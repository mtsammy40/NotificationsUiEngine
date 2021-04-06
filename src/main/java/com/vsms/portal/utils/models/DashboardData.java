package com.vsms.portal.utils.models;

public class DashboardData {
    Long smsBalance;

    public DashboardData() {
    }

    public DashboardData(Long smsBalance) {
        this.smsBalance = smsBalance;
    }

    public Long getSmsBalance() {
        return smsBalance;
    }

    public void setSmsBalance(Long smsBalance) {
        this.smsBalance = smsBalance;
    }
}
