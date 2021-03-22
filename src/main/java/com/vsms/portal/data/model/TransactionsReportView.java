package com.vsms.portal.data.model;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Immutable
@Table(name = "transactions_report_view")
public class TransactionsReportView {
    private Long rowNum;
    private Integer status;
    private String amount;
    private String mpesaRefId;
    private Timestamp transactionDate;
    private String transactionType;
    private String fullName;

    @Id
    @Column(name = "row_num", nullable = true)
    public Long getRowNum() {
        return rowNum;
    }

    public void setRowNum(Long rowNum) {
        this.rowNum = rowNum;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
    @Column(name = "mpesa_ref_id", nullable = true, length = 255)
    public String getMpesaRefId() {
        return mpesaRefId;
    }

    public void setMpesaRefId(String mpesaRefId) {
        this.mpesaRefId = mpesaRefId;
    }

    @Basic
    @Column(name = "transaction_date", nullable = true)
    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
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
    @Column(name = "full_name", nullable = true, length = -1)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionsReportView that = (TransactionsReportView) o;
        return Objects.equals(status, that.status) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(mpesaRefId, that.mpesaRefId) &&
                Objects.equals(transactionDate, that.transactionDate) &&
                Objects.equals(transactionType, that.transactionType) &&
                Objects.equals(fullName, that.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, amount, mpesaRefId, transactionDate, transactionType, fullName);
    }
}
