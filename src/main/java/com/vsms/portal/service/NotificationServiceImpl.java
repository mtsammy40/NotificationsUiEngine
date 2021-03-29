package com.vsms.portal.service;

import com.vsms.portal.data.model.User;
import com.vsms.portal.utils.models.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;

@Component
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    EmailService emailService;
    @Override
    public void send(Notification notification) throws IOException, MessagingException {
        if(notification.getType().equals(Notification.Type.EMAIL)){
            emailService.send(notification.getEmail());
        }
    }

    @Override
    public void sendSuccessfulRegistration(User user) throws IOException, MessagingException {
        Notification notification = new Notification(user, Notification.Type.EMAIL, Notification.Context.REGISTRATION_SUCCESS);
        send(notification);
    }
}
