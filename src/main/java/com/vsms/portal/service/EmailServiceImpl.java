package com.vsms.portal.service;

import com.vsms.portal.controller.AppController;
import com.vsms.portal.data.model.Emails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Component
public class EmailServiceImpl implements EmailService {
    private final Logger LOG = LogManager.getLogger(AppController.class);

    @Autowired
    JavaMailSender mailer;

    @Override
    public void send(Emails email) throws IOException, MessagingException {
        sendEmail(email);
    }

    void sendEmail(Emails email) throws MessagingException, IOException {
        MimeMessage msg = mailer.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(email.getRecipient());
        helper.setSubject(email.getContext().getSubject());

        // true = text/html
        helper.setText(email.getEmailContent(), true);
        mailer.send(msg);
    }

}
