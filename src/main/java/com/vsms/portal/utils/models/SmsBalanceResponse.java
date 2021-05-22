package com.vsms.portal.utils.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class SmsBalanceResponse {
    private Long clientId;
    private BigDecimal runningBalance;
    private Date dateCreated;
    private Long status;

    public SmsBalanceResponse() {
    }

    public SmsBalanceResponse(Map<String, Object> map) {
        setClientId((long) (int) map.get("clientId"));
        setRunningBalance(BigDecimal.valueOf((double) map.get("runningBalance")));
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public BigDecimal getRunningBalance() {
        return runningBalance;
    }

    public void setRunningBalance(BigDecimal runningBalance) {
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
