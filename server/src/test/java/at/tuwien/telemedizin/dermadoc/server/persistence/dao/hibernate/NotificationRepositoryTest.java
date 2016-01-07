package at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate;

import at.tuwien.telemedizin.dermadoc.entities.Notification;
import at.tuwien.telemedizin.dermadoc.server.Application;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Lilly on 07.01.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class NotificationRepositoryTest {

	@Autowired
	NotificationRepository notificationRepository;
	@Test
	public void testGetNotificationById() throws Exception {
		Notification n = new Notification();
		n.setId(1);
		n.setText("BLABLABLA");
		n.setCaseId(3);
		n.setUserId(26);

		Notification newN = notificationRepository.save(n);
		Assert.assertEquals(newN.getText(), n.getText());

	}

	@Test
	public void testListNotificationsForUser() throws Exception {

		Iterable<Notification> n = notificationRepository.getNotificationsByUserId(26);


	}
}