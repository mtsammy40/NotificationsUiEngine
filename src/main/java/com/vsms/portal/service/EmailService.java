package com.vsms.portal.service;

import com.vsms.portal.data.model.Emails;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailService {
    void send(Emails email) throws IOException, MessagingException;
}
