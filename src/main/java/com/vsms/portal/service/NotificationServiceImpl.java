package com.vsms.portal.service;

import com.vsms.portal.data.model.User;
import com.vsms.portal.utils.models.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    EmailService emailService;
    @Override
    public void send(Notification notification, Boolean async) throws IOException, MessagingException {
        if(notification.getType().equals(Notification.Type.EMAIL)){
            emailService.send(notification.getEmail(), async);
        }
    }

    @Override
    public void sendSuccessfulRegistration(User user, String password) throws IOException, MessagingException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(Notification.PlaceholderKeys.PASSWORD.getKey(), password);
        parameters.put(Notification.PlaceholderKeys.EMAIL.getKey(), user.getEmail());
        Notification notification = new Notification(user, Notification.Type.EMAIL, Notification.Context.REGISTRATION_SUCCESS, parameters);
        send(notification, true);
    }

}
