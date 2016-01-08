package at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate;

import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.User;
import at.tuwien.telemedizin.dermadoc.server.Application;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Lilly on 16.12.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Test
	public void saveTest() throws Exception {
		Assert.assertNotNull(userRepository);
		User testUser = new Physician();
		testUser.setName("test");
		testUser.setPassword("test");
		testUser.setMail("test@mail4.com");
			userRepository.save(testUser);

		//User mailUser = userRepository.getUserByMail("test@mail4.com");
		//User idUser = userRepository.getUserById(mailUser.getId());

		//Assert.assertEquals(mailUser, idUser);


	}

	@Test
	public void savePhysicianTest() {
		Physician p = new Physician();
		p.setPassword("12345");
		p.setMail("testPhysicianMail");
		p.setName("dr.test");

		userRepository.save(p);

		User returnedPhysician = userRepository.getUserByMail("testPhysicianMail");
		Assert.assertEquals(p, returnedPhysician);



	}


	@Test
	public void testListUsers() throws Exception {

		Iterable<User> userList = userRepository.findAll();
		Assert.assertNotNull(userList);
		int i = 0;
		for (User u : userList ) {
			i++;

		}
		Assert.assertEquals(userRepository.count(), i);

	}

	@Test
	public void testListPhysicians() throws Exception {

		Iterable<Physician> physiciansList = userRepository.listPhysicians();
		int i = 0;
		for (Physician ph : physiciansList) {
			i++;
		}

		Assert.assertTrue(i > 0);

	}
}