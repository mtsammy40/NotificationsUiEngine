package com.vsms.portal.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.vsms.portal.api.requests.PostMessageRequest;
import com.vsms.portal.controller.AppController;
import com.vsms.portal.data.model.ChMessages;
import com.vsms.portal.data.model.ChurchTransactions;
import com.vsms.portal.data.model.TransactionsReportView;
import com.vsms.portal.data.repositories.ChMessagesRepository;
import com.vsms.portal.data.repositories.ChTransactionsRepository;
import com.vsms.portal.data.repositories.TransactionViewRepository;
import com.vsms.portal.exception.ApiOperationException;
import com.vsms.portal.utils.models.FileMessageRow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service containing the main app logic
 */
@Service
public class AppService {
    private final Logger LOG = LogManager.getLogger(AppController.class);

    private ChMessagesRepository messagesRepository;
    private ChTransactionsRepository transactionsRepository;
    private TransactionViewRepository transactionViewRepository;

    public AppService(ChMessagesRepository messagesRepository, ChTransactionsRepository transactionsRepository, TransactionViewRepository transactionViewRepository) {
        this.messagesRepository = messagesRepository;
        this.transactionsRepository = transactionsRepository;
        this.transactionViewRepository = transactionViewRepository;
    }

    public List<ChMessages> getMessages() {
        return messagesRepository.findAll();
    }

    public List<ChurchTransactions> getTransactions() {
        return transactionsRepository.findAll();
    }

    public List<TransactionsReportView> getTransactionReport() {
        return transactionViewRepository.findAll();
    }

    public List<ChMessages> uploadMessages(Reader reader, String transactionType) {
        List<List<String>> records = new ArrayList<List<String>>();
        try (CSVReader csvReader = new CSVReader(reader);) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
            List<FileMessageRow> messages = records.stream()
                    .map(FileMessageRow::new)
                    // .peek((fileMessageRow) -> LOG.info("Me = " + fileMessageRow.getMsisdn()))
                    .collect(Collectors.toList());
            return messages.parallelStream().map(ChMessages::from)
                    .peek((message) -> message.setTransactionType("DEPOSIT"))
                    .peek((message) -> messagesRepository.save(message))
                    .collect(Collectors.toList());
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ChMessages> postMessages(PostMessageRequest request) throws ApiOperationException {
        request.validate();

        LOG.info("Processing request ...");

        return request.getPhoneNumberList().parallelStream()
                .peek((number) -> LOG.info("Creating message record... | {}", number))
                .map((phoneNumber) -> new ChMessages(phoneNumber, ChMessages.STATUS_PENDING, request.getMessage()))
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
}
