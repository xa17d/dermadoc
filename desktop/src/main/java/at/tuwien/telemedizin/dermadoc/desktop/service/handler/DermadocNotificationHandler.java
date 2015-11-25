package at.tuwien.telemedizin.dermadoc.desktop.service.handler;

import javax.management.Notification;

/**
 * notification listener
 * notifies gui if service receives a new notification
 */
public interface DermadocNotificationHandler {

    void onNewNotification(Notification notification);
}
