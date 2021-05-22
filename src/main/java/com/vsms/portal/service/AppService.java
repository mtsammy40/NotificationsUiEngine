package com.vsms.portal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.vsms.portal.api.requests.PostMessageRequest;
import com.vsms.portal.controller.AppController;
import com.vsms.portal.data.exceptions.ValidationException;
import com.vsms.portal.data.model.ChMessages;
import com.vsms.portal.data.model.Client;
import com.vsms.portal.data.model.TransactionsReportView;
import com.vsms.portal.data.model.User;
import com.vsms.portal.data.model.User.Action;
import com.vsms.portal.data.repositories.ChMessagesRepository;
import com.vsms.portal.data.repositories.ChTransactionsRepository;
import com.vsms.portal.data.repositories.ClientRepository;
import com.vsms.portal.data.repositories.TransactionViewRepository;
import com.vsms.portal.data.repositories.UserRepository;
import com.vsms.portal.data.specifications.DataSpecificationBuilder;
import com.vsms.portal.exception.ApiOperationException;
import com.vsms.portal.exception.RestCallException;
import com.vsms.portal.utils.enums.ApiStatus;
import com.vsms.portal.utils.enums.CoreRequestTypes;
import com.vsms.portal.utils.helpers.AsyncRest;
import com.vsms.portal.utils.helpers.CommonFunctions;
import com.vsms.portal.utils.helpers.Rest;
import com.vsms.portal.utils.models.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

@Service
public class AppService {
    private final Logger LOG = LogManager.getLogger(AppController.class);

    private UserService userService;

    private ChMessagesRepository messagesRepository;
    private ChTransactionsRepository transactionsRepository;
    private TransactionViewRepository transactionViewRepository;
    private UserRepository userRepository;
    private ClientRepository clientRepository;
    private Rest rest;

    public AppService(ChMessagesRepository messagesRepository, ChTransactionsRepository transactionsRepository,
                      TransactionViewRepository transactionViewRepository, UserRepository userRepository, ClientRepository clientRepository, UserService userService, Rest rest) {
        this.messagesRepository = messagesRepository;
        this.transactionsRepository = transactionsRepository;
        this.transactionViewRepository = transactionViewRepository;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.userService = userService;
        this.rest = rest;
    }

    public List<ChMessages> uploadMessages(Reader reader, String transactionType, HttpServletRequest httpServletRequest)
            throws ApiOperationException {
        List<List<String>> records = new ArrayList<List<String>>();
        User user = CommonFunctions.extractUser(httpServletRequest);
        try (CSVReader csvReader = new CSVReader(reader);) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
            List<FileMessageRow> messages = records.stream().map(FileMessageRow::new)
                    // .peek((fileMessageRow) -> LOG.info("Me = " + fileMessageRow.getMsisdn()))
                    .collect(Collectors.toList());
            return messages.parallelStream().map(ChMessages::from)
                    .peek((message) -> message.setClientId(user.getClientId()))
                    .peek((message) -> message.setTransactionType("DEPOSIT"))
                    .peek((message) -> messagesRepository.save(message)).collect(Collectors.toList());
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User postUser(User user, HttpServletRequest request) throws Exception {
        // Check if user record exists to knoe whether its a creation on an update
        Optional<User> userResult = Optional.empty();
        if (user.getId() != null) {
            userResult = userRepository.findById(user.getId());
        }
        if (userResult.isPresent()) {
            try {
                user.validate(userRepository, Action.UPDATE);
            } catch (ValidationException e) {
                throw new ApiOperationException("Validation failed: " + e.getMessage(), ApiStatus.BAD_REQUEST);
            }
            userRepository.save(user);
        } else {
            user = userService.signUp(user);
        }
        return user;
    }

    public List<ChMessages> postMessages(PostMessageRequest request, HttpServletRequest httpServletRequest)
            throws ApiOperationException {
        request.validate();

        LOG.info("Processing request ...");

        User user = CommonFunctions.extractUser(httpServletRequest);

        return request.getPhoneNumberList().parallelStream()
                .peek((number) -> LOG.info("Creating message record... | {}", number))
                .map((phoneNumber) -> new ChMessages(phoneNumber, ChMessages.STATUS_PENDING, request.getMessage(),
                        user.getClientId()))
                .peek((message) -> {
                    try {
                        message.setTransactionType("DEPOSIT");
                        if (!message.getStatus().equals(ChMessages.STATUS_CREATION_FAILED)) {
                            LOG.info("Saving message record... | {}", message);
                            messagesRepository.save(message);
                        }
                    } catch (Exception e) {
                        LOG.error("Could not save record for msisdn {} | {}", message.getMsisdn(), e.getMessage());
                        e.printStackTrace();
                        message.setStatus(ChMessages.STATUS_CREATION_FAILED);
                    }
                }).collect(Collectors.toList());
    }

    public DashboardData getDashboardData(HttpServletRequest request) throws ApiOperationException {
        DashboardData dashboardData = new DashboardData();
        User user = CommonFunctions.extractUser(request);
        // Get sms balance
        List<Future<Object>> results = null;
        try {
            List<Callable<Object>> callables = new ArrayList<>();
            callables.add(new AsyncRest(rest, new AsyncCoreRequest(user.getClientId().getId(), CoreRequestTypes.DASH_SUMMARY)));
            callables.add(new AsyncRest(rest, new AsyncCoreRequest(user.getClientId().getId(), CoreRequestTypes.SMS_BALANCE)));

            results = ForkJoinPool.commonPool().invokeAll(callables);

            CoreResponse smsResponse = (CoreResponse) results.get(1).get();
            if (smsResponse.getResponseBody() != null) {
                SmsBalanceResponse responseBody = new SmsBalanceResponse((Map) smsResponse.getResponseBody());
                dashboardData.setSmsBalance(responseBody.getRunningBalance());
            } else {
                dashboardData.setSmsBalance(BigDecimal.ZERO);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            dashboardData.setSmsBalance(BigDecimal.ZERO);
        }

        try {
            // get summary
            Map<String, Integer> summaryResponse = (Map) results.get(0).get();
            if (summaryResponse != null) {
                dashboardData.setSummary(summaryResponse.getOrDefault("Success", 0),
                        summaryResponse.getOrDefault("Failed", 0),
                        summaryResponse.getOrDefault("Sent", 0));
            } else {
                dashboardData.setSummary(0L, 0L, 0L);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            dashboardData.setSummary(0L, 0L, 0L);
        }
        return dashboardData;
    }

    public <T> Page<T> searchData(Map<String, String> queryParams, HttpServletRequest request, Class<T> clazz) throws Exception {
        String search = queryParams.getOrDefault("search", "");
        search = injectClientId(search, request);
        // get PageRequest Data
        PageRequest pageRequest = CommonFunctions.getPageData(queryParams);
        Specification<T> specification = buildSpecification(search, clazz);
        try {
            return fetch(specification, clazz, pageRequest);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new ApiOperationException("Invalid field in search parameters", ApiStatus.INVALID_SEARCH_FIELD, e);
        }
    }

    private <T> Specification<T> buildSpecification(String search, Class<T> clazz) throws Exception {
        DataSpecificationBuilder<T> builder = new DataSpecificationBuilder<T>();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        LOG.info("SearchString... {}", search);
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3), clazz);
        }
        return builder.build();
    }

    private <T> Page<T> fetch(Specification<T> specification, Class<T> clazz, PageRequest pageRequest) throws Exception {
        Page page = null;
        String className = clazz.getName();
        if (className.equalsIgnoreCase(ChMessages.class.getName())) {
            page = messagesRepository.findAll((Specification<ChMessages>) specification, pageRequest);
        } else if (className.equalsIgnoreCase(TransactionsReportView.class.getName())) {
            page = transactionViewRepository.findAll((Specification<TransactionsReportView>) specification, pageRequest);
        } else if (className.equalsIgnoreCase(User.class.getName())) {
            page = userRepository.findAll((Specification<User>) specification, pageRequest);
        } else if (className.equalsIgnoreCase(Client.class.getName())) {
            page = clientRepository.findAll((Specification<Client>) specification, pageRequest);
        } else {
            throw new ApiOperationException("Search not implemented for this entity [ " + clazz.getSimpleName() + " ]",
                    ApiStatus.UNSEARCHABLE_ENTITY);
        }
        return page;
    }

    /**
     * Injects client id to search string to enforce retrieval of records in that
     * client if the client does not have the admin role. If the user has an admin
     * role, the seach string remains unaltered.
     *
     * @param searchString
     * @param request
     * @throws ApiOperationException
     */
    public String injectClientId(String searchString, HttpServletRequest request) throws ApiOperationException {
        searchString = StringUtils.isBlank(searchString) ? "" : searchString;
        User user = CommonFunctions.extractUser(request);
        if (!user.hasAdminRole()) {
            LOG.info("User is not admin, attaching client id {} to search string...", user.getClientId().getId());
            StringBuilder searchStringbuilder = new StringBuilder(searchString);
            searchStringbuilder.insert(0, "clientId:" + user.getClientId().getId().toString() + ",");
            LOG.info("BUILDER >> {}", searchStringbuilder);
            searchString = searchStringbuilder.toString();
        } else {
            LOG.debug("User is admin, will not append clientId in searchString... | {}", searchString);
        }
        return searchString;
    }
}
