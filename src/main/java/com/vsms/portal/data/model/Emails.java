package com.vsms.portal.data.model;

import com.vsms.portal.utils.models.Notification;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Emails {
    public static final String defaultRegistrationContent = "<h1>Great to have you</h1><p>kindly follow this link to access the portal {{link}} </p>";


    private int id;
    private String recipient;
    private String emailContext;
    private Timestamp timeSent;
    private String status;
    private String emailContent;
    private Client clientId;

    private Notification.Context context;

    public Emails() {
    }

    public Emails(String recipient, Client clientId, Notification.Context context) {
        this.recipient = recipient;
        this.clientId = clientId;
        this.context = context;
        this.emailContext = context.name();
        this.setEmailContent(Emails.defaultRegistrationContent);
        this.setStatus(Notification.Status.PENDING.name());
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "recipient", nullable = false, length = 200)
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    @Basic
    @Column(name = "email_context", nullable = false, length = 200)
    public String getEmailContext() {
        return emailContext;
    }

    public void setEmailContext(String emailContext) {
        this.emailContext = emailContext;
    }

    @Basic
    @Column(name = "time_sent", nullable = true)
    public Timestamp getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(Timestamp timeSent) {
        this.timeSent = timeSent;
    }

    @Basic
    @Column(name = "status", nullable = false, length = 200)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "email_content", nullable = false, length = -1)
    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    @ManyToOne()
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    public Client getClientId() {
        return clientId;
    }

    public void setClientId(Client clientId) {
        this.clientId = clientId;
    }

    @Transient
    public Notification.Context getContext() {
        return context;
    }

    public void setContext(Notification.Context context) {
        this.context = context;
        this.setEmailContext(context.name());
    }

    @Override
    public String toString() {
        return "Emails{" +
                "id=" + id +
                ", recipient='" + recipient + '\'' +
                ", emailContext='" + emailContext + '\'' +
                ", timeSent=" + timeSent +
                ", status='" + status + '\'' +
                ", emailContent='" + emailContent + '\'' +
                ", clientId=" + clientId +
                '}';
    }
}
