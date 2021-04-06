package com.vsms.portal.controller;

import com.vsms.portal.api.requests.PostMessageRequest;
import com.vsms.portal.api.responses.ApiResponse;
import com.vsms.portal.data.model.ChMessages;
import com.vsms.portal.data.model.TransactionsReportView;
import com.vsms.portal.exception.ApiOperationException;
import com.vsms.portal.service.AppService;
import com.vsms.portal.utils.enums.ApiStatus;

import com.vsms.portal.utils.models.DashboardData;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin()
@RequestMapping("/app")
public class AppController {
    private final Logger LOGGER = LogManager.getLogger(AppController.class);

    private Environment environment;

    private AppService appService;

    public AppController(Environment environment, AppService appService) {
        this.environment = environment;
        this.appService = appService;
    }

    @PostMapping(value = "/uploadSmsFile")
    public ResponseEntity<ApiResponse<Object>> upload(@RequestParam("file") MultipartFile file,
            @RequestParam("transactionType") String transactionType, HttpServletRequest request) throws IOException, ApiOperationException {
        if (!file.isEmpty()) {
            String extension = FilenameUtils
                    .getExtension(Objects.requireNonNull(file.getOriginalFilename()).trim().toLowerCase());
            if (!("csv".equals(extension))) {
                LOGGER.error("File format not allowed: {}", extension);
                String errorMessage = extension + " file format not allowed";
                return ApiResponse.ofError(ApiStatus.BAD_REQUEST, errorMessage).build();
            }
            String file_name = FilenameUtils.getName(file.getOriginalFilename().trim());

            System.out.println("file name " + file_name);
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            List<ChMessages> messages = appService.uploadMessages(reader, transactionType, request);

            // FileCopyUtils.copy(file.getBytes(),
            // new File(environment.getRequiredProperty("file.location.uploads") +
            // uploadFilename));

            LOGGER.info("Uploaded File to be processed: {}", file_name);
            return new ApiResponse<Object>(ApiStatus.OK).build();
        }
        System.out.println(String.format("Receive %s from %s service %s", file.getOriginalFilename(), transactionType));

        return ApiResponse.ofError(ApiStatus.BAD_REQUEST, "No file").build();
    }

    @PostMapping(value = "/postMessage")
    public ResponseEntity<ApiResponse<?>> postSms(@RequestBody() PostMessageRequest request, HttpServletRequest httpServletRequest)
            throws ApiOperationException {
        List<ChMessages> processedMessages = appService.postMessages(request, httpServletRequest);
        return new ResponseEntity<ApiResponse<?>>(new ApiResponse<>(processedMessages), HttpStatus.OK);
    }

    @GetMapping("/messages")
    public ResponseEntity<ApiResponse<Page<ChMessages>>> getMessages(
            @RequestParam(name = "search", required = false) String search, HttpServletRequest request) throws Exception {
        Page<ChMessages> messagesPage = appService.searchData(search, request, ChMessages.class);
        return new ApiResponse<Page<ChMessages>>(ApiStatus.OK, messagesPage).build();

    }

    @GetMapping("/transactions")
    public ResponseEntity<ApiResponse<Page<TransactionsReportView>>> getTransactions(@RequestParam(name = "search", required = false) String search, HttpServletRequest request) throws Exception {
        Page<TransactionsReportView> transactions = appService.searchData(search, request, TransactionsReportView.class);
        return  new ApiResponse<Page<TransactionsReportView>>(ApiStatus.OK, transactions).build();
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<DashboardData>> getDashBoardData(HttpServletRequest request) throws Exception {
        DashboardData dashboardData = appService.getDashboardData(request);
        return  new ApiResponse<DashboardData>(ApiStatus.OK, dashboardData).build();
    }

}
