package at.tuwien.telemedizin.dermadoc.desktop.service.dto;

import at.tuwien.telemedizin.dermadoc.entities.Notification;

import java.util.List;

/**
 * notification listener
 * notifies gui if service receives a new notification
 */
public interface DermadocNotificationHandler {

    void onNewNotifications(List<Notification> notifications);
}
