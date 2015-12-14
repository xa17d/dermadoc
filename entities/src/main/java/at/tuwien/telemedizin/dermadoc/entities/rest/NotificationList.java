package at.tuwien.telemedizin.dermadoc.entities.rest;

import at.tuwien.telemedizin.dermadoc.entities.Notification;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by daniel on 01.12.2015.
 */
public class NotificationList extends ArrayList<Notification> {

    public NotificationList() {}
    public NotificationList(Collection<Notification> original) {
        super(original);
    }

}