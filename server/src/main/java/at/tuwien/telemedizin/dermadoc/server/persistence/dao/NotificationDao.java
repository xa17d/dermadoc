package at.tuwien.telemedizin.dermadoc.server.persistence.dao;

import at.tuwien.telemedizin.dermadoc.entities.Notification;

import java.util.List;

/**
 * Created by daniel on 01.12.2015.
 */
public interface NotificationDao {
    Notification getNotificationById(long notificationId);
    void insert(Notification notification);
    void delete(long notificationId);
    List<Notification> listNotificationsForUser(long userId);
}
