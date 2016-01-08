package at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseInfo;
import at.tuwien.telemedizin.dermadoc.entities.casedata.PhotoMessage;
import at.tuwien.telemedizin.dermadoc.entities.casedata.TextMessage;
import at.tuwien.telemedizin.dermadoc.server.Application;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

/*
*
 * Created by Lilly on 22.12.2015.*/


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class CaseDataRepositoryTest {

	@Autowired
	CaseDataRepository caseDataRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CaseRepository caseRepository;

	@Autowired
	MedicationRepository medicationRepository;


	CaseData savedCaseData;

	@Test
	public void testSaveCaseData() throws  Exception {

		Physician author1 = new Physician();
		author1.setMail("authorMail1");
		author1.setPassword("qweqwe");
		author1 = userRepository.save(author1);

		Case testCase = new Case();
		testCase.setPhysician(author1);
		testCase.setName("sldjflsdjf");
		testCase = caseRepository.save(testCase);

		CaseData testCaseData = new CaseInfo();

		testCaseData.setAuthor(author1);
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		testCaseData.setCreated(today);
		testCaseData.setPrivate(false);


		//	CaseInfo version = (CaseInfo) caseDataRepository.getById(1);
		//	testCaseData.setNextVersion(version);
		testCaseData.setCase(testCase);
		savedCaseData = caseDataRepository.save(testCaseData);

		CaseData c = caseDataRepository.getById(savedCaseData.getId());
		Assert.assertEquals(c.getCase(), savedCaseData.getCase());
	}


	@Test
	public void listCaseDataByUserAndCaseTest() throws Exception {
		Physician author = new Physician();
		author.setName("physicianTest");
		author.setMail("phuuu@hey");
		author.setPassword("123213");

		author = userRepository.save(author);

		Case c = new Case();
		c.setName("testCase3");
		c.setPhysician(author);
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		c.setCreated(today);
		c = caseRepository.save(c);
		CaseData testCaseData = new CaseInfo();

		testCaseData.setCase(c);
		testCaseData.setAuthor(author);

		testCaseData = caseDataRepository.save(testCaseData);

		long caseId = c.getId();
		long authorId = author.getId();
		Iterable<CaseData> caseList = caseDataRepository.listCaseDataByUserAndCase(caseId, authorId);

		Assert.assertNotNull(caseList);


	}


	@Test
	public void testGetByAuthor() throws Exception {
		Physician author = new Physician();
		author.setName("physicianTest");
		author.setMail("phuuu2@hey");
		author.setPassword("123213");

		author = userRepository.save(author);

		Case c = new Case();
		c.setName("testCase3");
		c.setPhysician(author);
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		c.setCreated(today);
		c = caseRepository.save(c);
		CaseData testCaseData = new CaseInfo();

		testCaseData.setCase(c);
		testCaseData.setAuthor(author);

		testCaseData = caseDataRepository.save(testCaseData);

		CaseData caseByAuthor = caseDataRepository.getByAuthor(author);
		Assert.assertEquals(testCaseData, caseByAuthor);
	}

	@Test
	public void testPhotoMessage() {

		Physician author = new Physician();
		author.setName("physicianTest");
		author.setMail("3@hey");
		author.setPassword("123213");

		author = userRepository.save(author);

		Case c = new Case();
		c.setName("testCase3");
		c.setPhysician(author);
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		c.setCreated(today);
		c = caseRepository.save(c);

		PhotoMessage pm = new PhotoMessage();
		pm.setAuthor(author);
		pm.setCase(c);
		pm.setCreated(today);
		pm.setMime("HI");

		pm = caseDataRepository.save(pm);

		Assert.assertNotNull(pm.getId());

	}

	@Test
	public void testTestMessage() {

		Physician author = new Physician();
		author.setName("physicianTest");
		author.setMail("35@hey");
		author.setPassword("123213");

		author = userRepository.save(author);

		Case c = new Case();
		c.setName("testCase3");
		c.setPhysician(author);
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		c.setCreated(today);
		c = caseRepository.save(c);

		TextMessage t = new TextMessage();
		t.setAuthor(author);
		t.setCase(c);
		t.setCreated(today);
		t.setMessage("Hello World");


		t = caseDataRepository.save(t);

		Assert.assertNotNull(t.getId());

	}

}
