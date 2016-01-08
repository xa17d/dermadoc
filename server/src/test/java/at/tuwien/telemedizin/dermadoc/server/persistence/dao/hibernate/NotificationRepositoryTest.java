package at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Notification;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
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

	@Autowired
	UserRepository userRepository;

	@Autowired
	CaseRepository caseRepository;
	@Test
	public void testGetNotificationById() throws Exception {

		Patient p = new Patient();
		p.setMail("testmail5@gmx.at");
		p.setPassword("12345");
		p.setSvnr("28233");
		p = userRepository.save(p);

		Case c = new Case();
		c.setPatient(p);
		c.setName("test notification");
		c = caseRepository.save(c);



		Notification n = new Notification();

		n.setText("BLABLABLA");
		n.setCaseId(c.getId());
		n.setUserId(p.getId());

		Notification newN = notificationRepository.save(n);
		Assert.assertEquals(newN.getText(), n.getText());

	}

	@Test
	public void testListNotificationsForUser() throws Exception {

		Iterable<Notification> n = notificationRepository.getNotificationsByUserId(26);


	}
}