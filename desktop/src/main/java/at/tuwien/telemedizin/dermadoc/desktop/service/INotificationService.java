package at.tuwien.telemedizin.dermadoc.desktop.service;

import at.tuwien.telemedizin.dermadoc.desktop.exception.DermadocException;
import at.tuwien.telemedizin.dermadoc.entities.Notification;
import javafx.collections.ObservableList;

/**
 * Created by Lucas on 03.12.2015.
 */
public interface INotificationService {

    /**
     * get an observable list of notifications
     * notification is added to list, as soon as it gest registered
     *
     * @return notification list
     * @throws DermadocException
     */
    ObservableList<Notification> getNotificationList() throws DermadocException;
}
