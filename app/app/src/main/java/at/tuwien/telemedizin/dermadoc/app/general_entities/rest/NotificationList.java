package at.tuwien.telemedizin.dermadoc.app.general_entities.rest;

import java.util.ArrayList;
import java.util.Collection;

import at.tuwien.telemedizin.dermadoc.app.general_entities.Notification;

/**
 * Created by daniel on 01.12.2015.
 */
public class NotificationList extends ArrayList<Notification> {

    public NotificationList() {}
    public NotificationList(Collection<Notification> original) {
        super(original);
    }

}