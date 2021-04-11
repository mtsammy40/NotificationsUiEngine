package com.vsms.portal.utils.models;

import com.vsms.portal.data.model.Emails;
import com.vsms.portal.data.model.User;

import java.util.Map;

public class Notification {
    public enum Status {
        PENDING,SENT,FAILED
    }

    public enum Type {
        EMAIL,SMS
    }

    public enum Context {
        REGISTRATION_SUCCESS("Registration Successful");

        private final String subject;

        Context(String subject) {
            this.subject = subject;
        }

        public String getSubject() {
            return subject;
        }
    }

    public enum PlaceholderKeys {
        PASSWORD("password"),
        EMAIL("email");

        private final String key;

        PlaceholderKeys(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    User recipient;
    Emails email;
    Type type;
    Context context;
    Map<String, String> parameters;

    public Notification(User recipient, Type type, Context context, Map<String, String> parameters) {
        this.recipient = recipient;
        this.type = type;
        this.context = context;
        this.parameters = parameters;
        if(type != null && type.equals(Type.EMAIL)) {
            createEmail();
        }
    }

    public Emails getEmail() {
        return email;
    }

    public void setEmail(Emails email) {
        this.email = email;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    private void createEmail() {
        this.setEmail(new Emails(recipient.getEmail(), recipient.getClientId(), context, parameters));
    }
}
