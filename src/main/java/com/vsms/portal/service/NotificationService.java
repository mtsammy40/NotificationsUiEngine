package com.vsms.portal.service;

import com.vsms.portal.data.model.User;
import com.vsms.portal.utils.models.Notification;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
public interface NotificationService {
    void send(Notification notification, Boolean async) throws IOException, MessagingException;
    void sendSuccessfulRegistration(User user, String Password) throws IOException, MessagingException;
}
