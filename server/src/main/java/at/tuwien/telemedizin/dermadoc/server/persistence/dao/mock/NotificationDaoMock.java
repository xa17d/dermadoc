/*
package at.tuwien.telemedizin.dermadoc.server.persistence.dao.mock;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.CaseStatus;
import at.tuwien.telemedizin.dermadoc.entities.Notification;
import at.tuwien.telemedizin.dermadoc.server.exceptions.EntityNotFoundException;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.NotificationDao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by daniel on 01.12.2015.
 *//*

@Repository
public class NotificationDaoMock implements NotificationDao {

    public NotificationDaoMock() {
        this.notifications = MockData.notifications;
    }

    private ArrayList<Notification> notifications;

    @Override
    public Notification getNotificationById(long notificationId) {
        for (Notification n : notifications) {
            if (n.getId() == notificationId) {
                return n;
            }
        }
        throw new EntityNotFoundException("id="+notificationId);
    }

    @Override
    public void insert(Notification notification) {
        // find highest id
        long id = 0;
        for (Notification n : notifications) {
            id = Math.max(n.getId(), id);
        }

        // generate new id
        id++;
        notification.setId(id);

        // add
        notifications.add(notification);
    }

    @Override
    public void delete(long notificationId) {
        notifications.removeIf(notification -> (notification.getId() == notificationId));
    }

    @Override
    public List<Notification> listNotificationsForUser(long userId) {
        ArrayList<Notification> result = new ArrayList<>();

        for (Notification n : notifications) {
            if (n.getUserId() == userId) {
                result.add(n);
            }
        }

        return result;
    }

}
*/
