package at.tuwien.telemedizin.dermadoc.desktop.service;

import javax.management.Notification;

/**
 * notification listener
 * notifies gui if service receives a new notification
 */
public interface DermadocNotificationListener {

    void onNewNotification(Notification notification);
}
