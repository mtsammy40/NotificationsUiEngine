package com.vsms.portal.data.model;

import com.vsms.portal.utils.models.Notification;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Map;

@Entity
public class Emails {
    private final Logger LOG = LoggerFactory.getLogger(Emails.class); 
    public static final String defaultRegistrationContent = "<h1>Great to have you</h1><p>kindly follow this link to access the portal {{link}} </p>";

    public enum DefaultContent {
        REGISTRATION_SUCCESS(
                "<h1>Great to have you</h1><p>Kindly log in with email: {{email}} and password: {{password}} </p>");

        private final String body;

        DefaultContent(String body) {
            this.body = body;
        }

        public String getBody() {
            return body;
        }
    }

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

    public Emails(String recipient, Client clientId, Notification.Context context, Map<String, String> parameters) {
        this.recipient = recipient;
        this.clientId = clientId;
        this.context = context;
        this.emailContext = context.name();
        this.setEmailContent(getEnrichedContent(parameters));
        this.setStatus(Notification.Status.PENDING.name());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
        return "Emails{" + "id=" + id + ", recipient='" + recipient + '\'' + ", emailContext='" + emailContext + '\''
                + ", timeSent=" + timeSent + ", status='" + status + '\'' + ", emailContent='" + emailContent + '\''
                + ", clientId=" + clientId + '}';
    }

    private String getEnrichedContent(Map<String, String> parameters) {
        String content = getContentBody(this.context);
        return parameters.keySet().stream()
                // escape curly braces
                .filter((key) -> {
                    return StringUtils.isNotBlank(key) && StringUtils.isNotBlank(parameters.get(key));
                }).map((key) -> "\\{\\{" + key + "\\}\\}")
                // remove null values
                .peek((key) -> LOG.info("Email content: {} | key {}", content, key))
                .reduce(content, (editedContent, key) -> editedContent.replaceAll(key, parameters.get(clean(key))));
    }

    /**
     * Removes anything that is not alphanumeric from the string
     * @param {String} string
     * @return {String}
     */
    private String clean(String string) {
        return string.replaceAll("[^a-zA-Z0-9]", "");  
    }

    private String getContentBody(Notification.Context context) {
        return DefaultContent.valueOf(context.name()).getBody();
    }
}
