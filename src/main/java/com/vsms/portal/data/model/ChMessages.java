package com.vsms.portal.data.model;

import com.vsms.portal.utils.models.FileMessageRow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "church_messages")
public class ChMessages {
    private static final Logger LOG = LogManager.getLogger(ChMessages.class);


    public static final Long STATUS_PENDING = 0L;
    public static final Long STATUS_SENT = 1L;
    public static final Long STATUS_CREATION_FAILED = 99L;

    private int id;
    private String msisdn;
    private Long status;
    private String message;
    private Timestamp dateCreated;
    private String transactionType;

    public ChMessages() {
    }

    public ChMessages(String msisdn, Long status, String message) {
        try {
            this.msisdn = msisdn;
            this.status = status;
            this.message = message;
        } catch (Exception e) {
            LOG.error("Error creating message for {} | {}", msisdn, e.getMessage());
            this.status = STATUS_CREATION_FAILED;
        }
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "destination", nullable = true, length = 20)
    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Basic
    @Column(name = "message", nullable = true, length = -1)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Basic
    @Column(name = "date_created", nullable = false)
    @CreationTimestamp
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Basic
    @Column(name = "transaction_type", nullable = false)
    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChMessages)) return false;
        ChMessages messages = (ChMessages) o;
        return getId() == messages.getId() &&
                Objects.equals(getMsisdn(), messages.getMsisdn()) &&
                Objects.equals(getStatus(), messages.getStatus()) &&
                Objects.equals(getMessage(), messages.getMessage()) &&
                Objects.equals(getDateCreated(), messages.getDateCreated()) &&
                Objects.equals(getTransactionType(), messages.getTransactionType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMsisdn(), getStatus(), getMessage(), getDateCreated(), getTransactionType());
    }

    @Override
    public String toString() {
        return "ChMessages{" +
                "id=" + id +
                ", msisdn='" + msisdn + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", dateCreated=" + dateCreated +
                ", transactionType='" + transactionType + '\'' +
                '}';
    }

    public static ChMessages from(FileMessageRow fileMessageRow) {
        return new ChMessages(fileMessageRow.getMsisdn(), STATUS_PENDING, fileMessageRow.getMessage());
    }
}
