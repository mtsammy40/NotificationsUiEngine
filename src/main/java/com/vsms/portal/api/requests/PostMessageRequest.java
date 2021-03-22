package com.vsms.portal.api.requests;

import com.vsms.portal.controller.UserController;
import com.vsms.portal.exception.ApiOperationException;
import com.vsms.portal.utils.enums.ApiStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class PostMessageRequest {
    private static final Logger LOGGER = LogManager.getLogger(PostMessageRequest.class);

    private List<String> phoneNumberList;
    private String message;
    private Date scheduleTime;

    public PostMessageRequest() {
    }

    public PostMessageRequest(List<String> phoneNumberList, String message, Date scheduleTime) {
        this.phoneNumberList = phoneNumberList;
        this.message = message;
        this.scheduleTime = scheduleTime;
    }

    public List<String> getPhoneNumberList() {
        return phoneNumberList;
    }

    public void setPhoneNumberList(List<String> phoneNumberList) {
        this.phoneNumberList = phoneNumberList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(Date scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public void validate() throws ApiOperationException {
        LOGGER.info("Validating {} ...", this.getClass().getSimpleName());
        if(StringUtils.isBlank(this.message)){
            throw new ApiOperationException("Field [ Message ] is required!", ApiStatus.BAD_REQUEST);
        }
        if(this.phoneNumberList.size() == 0) {
            throw new ApiOperationException("Field [ phoneNumberList ] is required!", ApiStatus.BAD_REQUEST);
        }
    }
}
