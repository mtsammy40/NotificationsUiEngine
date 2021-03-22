package com.vsms.portal.data.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "church_transactions")
public class ChurchTransactions {
    private int id;
    private String amount;
    private Integer churchMemberId;
    private Integer status;
    private Timestamp transactionDate;
    private String transactionType;
    private String mpesaRefId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "church_member_id", nullable = true)
    public Integer getChurchMemberId() {
        return churchMemberId;
    }

    public void setChurchMemberId(Integer churchMemberId) {
        this.churchMemberId = churchMemberId;
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
    @Column(name = "mpesa_ref_id", nullable = true, length = 255)
    public String getMpesaRefId() {
        return mpesaRefId;
    }

    public void setMpesaRefId(String mpesaRefId) {
        this.mpesaRefId = mpesaRefId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChurchTransactions that = (ChurchTransactions) o;
        return id == that.id &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(churchMemberId, that.churchMemberId) &&
                Objects.equals(status, that.status) &&
                Objects.equals(transactionDate, that.transactionDate) &&
                Objects.equals(transactionType, that.transactionType) &&
                Objects.equals(mpesaRefId, that.mpesaRefId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, churchMemberId, status, transactionDate, transactionType, mpesaRefId);
    }
}
