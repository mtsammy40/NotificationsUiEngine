package com.vsms.portal.utils.models;

import com.vsms.portal.data.model.Emails;
import com.vsms.portal.data.model.User;

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


    User recipient;

    Emails email;
    Type type;
    Context context;

    public Notification(User recipient, Type type, Context context) {
        this.recipient = recipient;
        this.type = type;
        this.context = context;
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

    private void createEmail() {
        this.setEmail(new Emails(recipient.getEmail(), recipient.getClient(), context));
    }
}
