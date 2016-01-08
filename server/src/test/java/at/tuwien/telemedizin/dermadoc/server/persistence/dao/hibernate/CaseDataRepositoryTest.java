package at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseInfo;
import at.tuwien.telemedizin.dermadoc.server.Application;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Calendar;

/*
*
 * Created by Lilly on 22.12.2015.*/


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
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

//	@Test
//	public void listCaseDataByUserAndCaseTest() throws Exception {
//		Physician author = new Physician();
//		author = (Physician) userRepository.getUserByMail("authorMail2");
//
//		long caseId = 2;
//		long authorId = author.getId();
//		Iterable<CaseData> c = caseDataRepository.listCaseDataByUserAndCase(caseId, authorId);
//		Assert.assertNotNull(c);
//	}

//
//@Test
//public void testGetByAuthor() throws Exception {
//	User author = userRepository.getUserByMail("authorMail1");
//
//	CaseData caseByAuthor = caseDataRepository.getByAuthor(author);
//	Assert.assertNotNull(caseByAuthor);
//}


}
