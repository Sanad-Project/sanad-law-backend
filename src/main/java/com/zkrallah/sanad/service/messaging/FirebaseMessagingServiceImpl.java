package com.zkrallah.sanad.service.messaging;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.zkrallah.sanad.dtos.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirebaseMessagingServiceImpl implements FirebaseMessagingService{

    @Autowired
    private FirebaseMessaging firebaseMessaging;

    @Override
    public String sendNotificationByToken(String token, MessageDto msg) {
        Notification notification = Notification.builder()
                .setTitle("New Message")
                .setBody(msg.getContent())
                .build();

        Message message = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .putData("room", msg.getRoom())
                .putData("content", msg.getContent())
                .putData("senderId", msg.getSenderId().toString())
                .putData("receiverId", msg.getReceiverId().toString())
                .build();

        try {
            return firebaseMessaging.send(message);
        } catch (FirebaseMessagingException ex) {
            return "failure";
        }
    }
}
