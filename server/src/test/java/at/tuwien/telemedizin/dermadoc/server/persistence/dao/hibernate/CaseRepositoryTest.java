package at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.User;
import at.tuwien.telemedizin.dermadoc.server.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Lilly on 31.12.2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class CaseRepositoryTest {

	@Autowired
	CaseRepository caseRepository;

	@Autowired
	UserRepository userRepository;

	@Test
	public void testSave() throws Exception {
		Case c = new Case();
		c.setName("test Case");
		c.setDescription("this is for testing");
		Patient patient = new Patient();
		Physician physician = new Physician();


		patient.setMail("testmail244");
		patient.setPassword("12345");
		patient.setSvnr("4016");
		patient = userRepository.save(patient);


		physician.setMail("testmail3344");
		physician.setPassword("12345");
		physician = userRepository.save(physician);

		c.setPatient(patient);
		c.setPhysician(physician);


		c = caseRepository.save(c);

		Case returnedCase = caseRepository.getCaseById(c.getId());
		Assert.assertEquals(returnedCase, c);

	}

	@Test
	public void findByPatient() throws Exception {
		User u = userRepository.getUserById(33);
		Iterable<Case> c = caseRepository.findByPatient(u);
		for (Case ca : c) {
			System.out.println(ca);
		}
	}

	@Test
	public void findOpenCases() throws Exception {
		Iterable<Case> c = caseRepository.findOpenCases();

	}

//	@Test
//	public void testGetById() throws Exception {
//
//	}
}