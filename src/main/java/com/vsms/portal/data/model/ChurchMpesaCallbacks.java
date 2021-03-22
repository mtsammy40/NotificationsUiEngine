package com.vsms.portal.data.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "church_mpesa_callbacks")
public class ChurchMpesaCallbacks {
    private String accountBalance;
    private String amount;
    private String checkoutRequestid;
    private String merchantRequestid;
    private String mpesaReceiptNumber;
    private String resultDesc;
    private String transactionDate;
    private String msisdn;
    private Integer status;
    private int id;
    private String trxReference;
    private String shortCode;
    private String transactionType;
    private String firstName;
    private String lastName;
    private String middleName;

    @Basic
    @Column(name = "account_balance", nullable = true, length = 255)
    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    @Basic
    @Column(name = "amount", nullable = true, length = 255)
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "checkout_requestid", nullable = true, length = 255)
    public String getCheckoutRequestid() {
        return checkoutRequestid;
    }

    public void setCheckoutRequestid(String checkoutRequestid) {
        this.checkoutRequestid = checkoutRequestid;
    }

    @Basic
    @Column(name = "merchant_requestid", nullable = true, length = 255)
    public String getMerchantRequestid() {
        return merchantRequestid;
    }

    public void setMerchantRequestid(String merchantRequestid) {
        this.merchantRequestid = merchantRequestid;
    }

    @Basic
    @Column(name = "mpesa_receipt_number", nullable = true, length = 255)
    public String getMpesaReceiptNumber() {
        return mpesaReceiptNumber;
    }

    public void setMpesaReceiptNumber(String mpesaReceiptNumber) {
        this.mpesaReceiptNumber = mpesaReceiptNumber;
    }

    @Basic
    @Column(name = "result_desc", nullable = true, length = 255)
    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    @Basic
    @Column(name = "transaction_date", nullable = true, length = 255)
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

    @Basic
    @Column(name = "status", nullable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "trx_reference", nullable = true, length = 255)
    public String getTrxReference() {
        return trxReference;
    }

    public void setTrxReference(String trxReference) {
        this.trxReference = trxReference;
    }

    @Basic
    @Column(name = "short_code", nullable = true, length = 255)
    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    @Basic
    @Column(name = "transaction_type", nullable = true, length = 255)
    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Basic
    @Column(name = "first_name", nullable = true, length = 255)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = true, length = 255)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "middle_name", nullable = true, length = 255)
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChurchMpesaCallbacks that = (ChurchMpesaCallbacks) o;
        return id == that.id &&
                Objects.equals(accountBalance, that.accountBalance) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(checkoutRequestid, that.checkoutRequestid) &&
                Objects.equals(merchantRequestid, that.merchantRequestid) &&
                Objects.equals(mpesaReceiptNumber, that.mpesaReceiptNumber) &&
                Objects.equals(resultDesc, that.resultDesc) &&
                Objects.equals(transactionDate, that.transactionDate) &&
                Objects.equals(msisdn, that.msisdn) &&
                Objects.equals(status, that.status) &&
                Objects.equals(trxReference, that.trxReference) &&
                Objects.equals(shortCode, that.shortCode) &&
                Objects.equals(transactionType, that.transactionType) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(middleName, that.middleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountBalance, amount, checkoutRequestid, merchantRequestid, mpesaReceiptNumber, resultDesc, transactionDate, msisdn, status, id, trxReference, shortCode, transactionType, firstName, lastName, middleName);
    }
}
