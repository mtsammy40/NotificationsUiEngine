package com.vsms.portal.utils.models;

import java.util.List;

public class FileMessageRow {
    private String msisdn;
    private String message;

    public FileMessageRow() {
    }

    public FileMessageRow(String msisdn, String message) {
        this.msisdn = msisdn;
        this.message = message;
    }

    public FileMessageRow(List<String> record) {
        this.msisdn = record.get(0);
        this.message = record.get(1);
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
