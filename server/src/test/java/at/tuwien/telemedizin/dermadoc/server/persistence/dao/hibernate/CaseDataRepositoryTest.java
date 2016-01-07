package at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.User;
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

/**
 * Created by Lilly on 22.12.2015.
 */
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

	@Test
	public void testSaveCaseData() throws  Exception {
		CaseData testCaseData = new CaseInfo();
		testCaseData.setId(1);
		User author =  userRepository.getUserById(26);
		testCaseData.setAuthor(author);
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		testCaseData.setCreated(today);
		testCaseData.setPrivate(false);
		Case testCase = caseRepository.getById(3);
		testCaseData.setCase(testCase);
	//	CaseInfo version = (CaseInfo) caseDataRepository.getById(1);
	//	testCaseData.setNextVersion(version);

		caseDataRepository.save(testCaseData);

		CaseData c = caseDataRepository.getById(1);
		Assert.assertEquals(c.getCase(), testCaseData.getCase());
	}


//	@Test
//	public void testGetByCaseId() throws Exception {
//
//	}
//
//	@Test
//	public void testGetById() throws Exception {
//
//	}
//
//	@Test
//	public void testGetByAuthor() throws Exception {
//
//	}
}