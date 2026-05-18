package com.ecommerce.demo.service;

import com.ecommerce.demo.entity.Notification;
import com.ecommerce.demo.entity.User;
import com.ecommerce.demo.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationService {
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    public List<Notification> getUserNotifications(User user) {
        return notificationRepository.findByUserOrderByCreatedAtDesc(user);
    }
    
    public long getUnreadCount(User user) {
        return notificationRepository.findByUserAndIsReadFalse(user).size();
    }
    
    public void markAsRead(Long notificationId) {
        notificationRepository.findById(notificationId).ifPresent(n -> {
            n.setRead(true);
            notificationRepository.save(n);
        });
    }
    
    public void markAllAsRead(User user) {
        List<Notification> unread = notificationRepository.findByUserAndIsReadFalse(user);
        unread.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(unread);
    }
    
    public void createNotification(String title, String message, String type, User user) {
        Notification notification = new Notification(title, message, type, user);
        notificationRepository.save(notification);
    }
}
