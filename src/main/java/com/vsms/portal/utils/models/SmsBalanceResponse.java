package com.vsms.portal.utils.models;

import java.util.Date;
import java.util.Map;

public class SmsBalanceResponse {
    private Long id;
    private Long clientId;
    private String runningBalance;
    private Date dateCreated;
    private Long status;

    public SmsBalanceResponse() {
    }

    public SmsBalanceResponse(Map<String, Object> map) {
        setClientId((long) (int) map.get("clientId"));
        setId(Long.valueOf(map.get("id").toString()));
        setRunningBalance((String) map.get("runningBalance"));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getRunningBalance() {
        return runningBalance;
    }

    public void setRunningBalance(String runningBalance) {
        this.runningBalance = runningBalance;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
}
