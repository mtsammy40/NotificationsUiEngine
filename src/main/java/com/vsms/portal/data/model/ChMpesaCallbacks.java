package com.vsms.portal.data.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ch_mpesa_callbacks")
public class ChMpesaCallbacks {
    private int id;
    private String merchantRequestId;
    private String checkoutRequestId;
    private String resultDesc;
    private String amount;
    private String mpesaReceiptNumber;
    private String accountBalance;
    private String transactionDate;
    private String msisdn;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "MerchantRequestID", nullable = true, length = 255)
    public String getMerchantRequestId() {
        return merchantRequestId;
    }

    public void setMerchantRequestId(String merchantRequestId) {
        this.merchantRequestId = merchantRequestId;
    }

    @Basic
    @Column(name = "CheckoutRequestID", nullable = true, length = 255)
    public String getCheckoutRequestId() {
        return checkoutRequestId;
    }

    public void setCheckoutRequestId(String checkoutRequestId) {
        this.checkoutRequestId = checkoutRequestId;
    }

    @Basic
    @Column(name = "ResultDesc", nullable = true, length = -1)
    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    @Basic
    @Column(name = "Amount", nullable = true, length = 7)
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "MpesaReceiptNumber", nullable = true, length = 255)
    public String getMpesaReceiptNumber() {
        return mpesaReceiptNumber;
    }

    public void setMpesaReceiptNumber(String mpesaReceiptNumber) {
        this.mpesaReceiptNumber = mpesaReceiptNumber;
    }

    @Basic
    @Column(name = "AccountBalance", nullable = true, length = 200)
    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    @Basic
    @Column(name = "TransactionDate", nullable = true, length = 255)
    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Basic
    @Column(name = "msisdn", nullable = true, length = 255)
    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChMpesaCallbacks that = (ChMpesaCallbacks) o;
        return id == that.id &&
                Objects.equals(merchantRequestId, that.merchantRequestId) &&
                Objects.equals(checkoutRequestId, that.checkoutRequestId) &&
                Objects.equals(resultDesc, that.resultDesc) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(mpesaReceiptNumber, that.mpesaReceiptNumber) &&
                Objects.equals(accountBalance, that.accountBalance) &&
                Objects.equals(transactionDate, that.transactionDate) &&
                Objects.equals(msisdn, that.msisdn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, merchantRequestId, checkoutRequestId, resultDesc, amount, mpesaReceiptNumber, accountBalance, transactionDate, msisdn);
    }
}
