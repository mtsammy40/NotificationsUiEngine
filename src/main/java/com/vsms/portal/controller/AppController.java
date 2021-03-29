package com.vsms.portal.controller;

import com.vsms.portal.api.requests.PostMessageRequest;
import com.vsms.portal.api.responses.ApiResponse;
import com.vsms.portal.data.model.ChMessages;
import com.vsms.portal.data.model.ChurchTransactions;
import com.vsms.portal.data.model.TransactionsReportView;
import com.vsms.portal.exception.ApiOperationException;
import com.vsms.portal.service.AppService;
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
import java.sql.Timestamp;
import java.util.*;

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
    public ResponseEntity<Map<String, String>> upload(@RequestParam("file") MultipartFile file,
            @RequestParam("transactionType") String transactionType) throws IOException {
        Map<String, String> response = new HashMap<>();
        if (!file.isEmpty()) {
            String extension = FilenameUtils
                    .getExtension(Objects.requireNonNull(file.getOriginalFilename()).trim().toLowerCase());
            if (!("csv".equals(extension))) {
                LOGGER.error("File format not allowed: {}", extension);
                response.put("message", extension + " file format not allowed");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            String file_name = FilenameUtils.getName(file.getOriginalFilename().trim());

            System.out.println("file name " + file_name);
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            List<ChMessages> messages = appService.uploadMessages(reader, transactionType);

            // FileCopyUtils.copy(file.getBytes(),
            // new File(environment.getRequiredProperty("file.location.uploads") +
            // uploadFilename));

            LOGGER.info("Uploaded File to be processed: {}", file_name);

            response.put("message", "uploaded " + messages.size() + " messages");
            response.put("http_code", HttpStatus.CREATED.toString());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("message", "no file");
        response.put("http_code", HttpStatus.BAD_REQUEST.toString());

        System.out.println(String.format("Receive %s from %s service %s", file.getOriginalFilename(), transactionType));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/postMessage")
    public ResponseEntity<ApiResponse<?>> postSms(@RequestBody() PostMessageRequest request)
            throws ApiOperationException {
        List<ChMessages> processedMessages = appService.postMessages(request);
        return new ResponseEntity<ApiResponse<?>>(new ApiResponse<>(processedMessages), HttpStatus.OK);
    }

    @GetMapping("/messages")
    public ResponseEntity<Page<ChMessages>> getMessages(
            @RequestParam(name = "search", required = false) String search) throws Exception {
        Page<ChMessages> messagesPage = appService.getMessages(search);
        return new ResponseEntity<Page<ChMessages>>(messagesPage, HttpStatus.OK);

    }

    @GetMapping("/transactions")
    public ResponseEntity<Page<TransactionsReportView>> getTransactions(@RequestParam(name = "search", required = false) String search) throws Exception {
        Page<TransactionsReportView> transactions = appService.getTransactionReport(search);
        return new ResponseEntity<Page<TransactionsReportView>>(transactions, HttpStatus.OK);
    }

}
