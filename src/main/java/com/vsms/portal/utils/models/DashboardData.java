package com.vsms.portal.utils.models;

import java.math.BigDecimal;

public class DashboardData {
    BigDecimal smsBalance;
    Long failed;
    Long Success;
    Long sent;

    public DashboardData() {
    }

    public DashboardData(BigDecimal smsBalance) {
        this.smsBalance = smsBalance;
    }

    public BigDecimal getSmsBalance() {
        return smsBalance;
    }

    public void setSmsBalance(BigDecimal smsBalance) {
        this.smsBalance = smsBalance;
    }

    public Long getFailed() {
        return failed;
    }

    public void setFailed(Long failed) {
        this.failed = failed;
    }

    public Long getSuccess() {
        return Success;
    }

    public void setSuccess(Long success) {
        Success = success;
    }

    public Long getSent() {
        return sent;
    }

    public void setSent(Long sent) {
        this.sent = sent;
    }

    public void setSummary(Long success, Long failed, Long sent) {
        setSent(sent);
        setFailed(failed);
        setSuccess(success);
    }

    public void setSummary(int success, int failed, int sent) {
        setSent((long) sent);
        setFailed((long) failed);
        setSuccess((long) success);
    }
}
