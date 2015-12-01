package at.tuwien.telemedizin.dermadoc.server.controllers;

import at.tuwien.telemedizin.dermadoc.entities.Notification;
import at.tuwien.telemedizin.dermadoc.entities.User;
import at.tuwien.telemedizin.dermadoc.entities.rest.NotificationList;
import at.tuwien.telemedizin.dermadoc.server.exceptions.EntityNotFoundException;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.NotificationDao;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.UserDao;
import at.tuwien.telemedizin.dermadoc.server.security.Access;
import at.tuwien.telemedizin.dermadoc.server.security.AccessUser;
import at.tuwien.telemedizin.dermadoc.server.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by daniel on 01.12.2015.
 */
@RestController
public class NotificationController {
    @Autowired
    private NotificationDao notificationDao;

    @RequestMapping(value = "/notifications", method = RequestMethod.GET)
    @AccessUser
    public NotificationList listNotifications(@CurrentUser User user) {
        return new NotificationList(notificationDao.listNotificationsForUser(user.getId()));
    }

    @RequestMapping(value = "/notifications/{notificationId}", method = RequestMethod.DELETE)
    @AccessUser
    public void deleteNotification(@CurrentUser User user, @PathVariable long notificationId) {
        Notification notification = notificationDao.getNotificationById(notificationId);

        if (Access.canAccess(user, notification)) {
            notificationDao.delete(notificationId);
        }
        else {
            throw new EntityNotFoundException("user has no access");
        }
    }


}
