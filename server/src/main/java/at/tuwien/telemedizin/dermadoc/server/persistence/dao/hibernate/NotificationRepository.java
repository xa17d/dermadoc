package at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate;

import at.tuwien.telemedizin.dermadoc.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Lilly on 07.01.2016.
 */
public interface NotificationRepository extends JpaRepository<Notification, Long> {
	Notification getNotificationById(long notificationId);
	List<Notification> getNotificationsByUserId(long userId);
}
