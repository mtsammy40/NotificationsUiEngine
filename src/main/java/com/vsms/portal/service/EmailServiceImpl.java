package com.vsms.portal.service;

import com.vsms.portal.controller.AppController;
import com.vsms.portal.data.model.Emails;
import com.vsms.portal.data.repositories.EmailRepository;
import com.vsms.portal.utils.models.Notification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

@Component
public class EmailServiceImpl implements EmailService {
    private final Logger LOG = LogManager.getLogger(AppController.class);

    @Autowired
    JavaMailSender mailer;

    @Autowired
    EmailRepository emailRepository;

    @Override
    public void send(Emails email, Boolean async) {
        try {
            sendEmail(email, async);
        } catch (Exception e) {
            email.setStatus(Notification.Status.FAILED.name());
            emailRepository.save(email);
        }
    }

    void sendEmail(Emails email, Boolean async) throws MessagingException, IOException {
        MimeMessage msg = mailer.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(email.getRecipient());
        helper.setSubject(email.getContext().getSubject());

        // true = text/html
        helper.setText(email.getEmailContent(), true);

        if (async) {
            ForkJoinPool.commonPool().submit(() -> {
                pushToNetwork(email, msg);
            });
        } else {
            pushToNetwork(email, msg);
        }

    }

    private void pushToNetwork(Emails email, MimeMessage message) {
        try {
            mailer.send(message);
            email.setStatus(Notification.Status.SENT.name());
        } catch (Exception e) {
            email.setStatus(Notification.Status.FAILED.name());
        }
        emailRepository.save(email);
    }

}
