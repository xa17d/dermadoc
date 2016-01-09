package at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Icd10Diagnosis;
import at.tuwien.telemedizin.dermadoc.entities.Medication;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.casedata.*;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Lilly on 08.01.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class CaseDataDaoImplTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	CaseRepository caseRepository;

	@Autowired
	CaseDataDaoImpl caseDataDao;

	@Autowired
	CaseDataRepository caseDataRepository;


		@Test
		public void TestAdvice () {
			Physician author1 = new Physician();
			author1.setMail("authorMail989");
			author1.setPassword("qweqwe");
			author1 = userRepository.save(author1);


			Case testCase = new Case();
			testCase.setPhysician(author1);
			testCase.setName("sldjflsdjf");
			testCase = caseRepository.save(testCase);

			Advice a = new Advice();
			a.setCase(testCase);
			a.setAuthor(author1);
			a.setMessage("slfjlsdfjlsdjfl");
			Calendar today = Calendar.getInstance();
			today.set(Calendar.HOUR_OF_DAY, 0);
			a.setCreated(today);

			List<Medication> m = new ArrayList<>();

			for (int i= 0; i<5; i++) {
				Medication med = new Medication();
				med.setDosis("300mg");
				med.setName("med"+i);
				m.add(med);
			}
			a.setMedications(m);

			caseDataDao.insert(a);

			Advice c = (Advice) caseDataRepository.getByAuthor(author1);
			List<Medication> testMed = c.getMedications();

			Assert.assertNotNull(testMed);
			Assert.assertEquals(testMed.size(), 5);


		}

	@Test
	public void TestDiagonse() {
		Physician author1 = new Physician();
		author1.setMail("authorMail98998");
		author1.setPassword("qweqwe");
		author1 = userRepository.save(author1);


		Case testCase = new Case();
		testCase.setPhysician(author1);
		testCase.setName("sldjflsdjf");
		testCase = caseRepository.save(testCase);

		Diagnosis a = new Diagnosis();
		a.setCase(testCase);
		a.setAuthor(author1);
		a.setMessage("slfjlsdfjlsdjfl");
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		a.setCreated(today);

		List<Icd10Diagnosis> m = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			Icd10Diagnosis med = new Icd10Diagnosis();
			med.setIcd10Code("404");
			med.setIcd10Name("illness" + i);
			m.add(med);
		}
		a.setDiagnosisList(m);

		caseDataDao.insert(a);

		Diagnosis c = (Diagnosis) caseDataRepository.getByAuthor(author1);
		List<Icd10Diagnosis> testMed = c.getDiagnosisList();

		Assert.assertNotNull(testMed);
		Assert.assertEquals(testMed.size(), 5);


	}

	@Test
	public void TestAnamnesis() {
		Physician author1 = new Physician();
		author1.setMail("authorMail1228");
		author1.setPassword("qweqwe");
		author1 = userRepository.save(author1);


		Case testCase = new Case();
		testCase.setPhysician(author1);
		testCase.setName("sldjflsdjf");
		testCase = caseRepository.save(testCase);

		Anamnesis a = new Anamnesis();
		a.setCase(testCase);
		a.setAuthor(author1);
		a.setMessage("slfjlsdfjlsdjfl");
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		a.setCreated(today);

		List<AnamnesisQuestion> m = new ArrayList<>();

		for (int i= 0; i<5; i++) {
			AnamnesisQuestionText med = new AnamnesisQuestionText();
			AnamnesisQuestionBool med2 = new AnamnesisQuestionBool();
			med2.setQuestion(i + ": ????????????");
			med2.setAnswer(true);
			med.setQuestion(i + ": ????????????");
			med.setAnswer(i + ": !!!!!!!!!!!!!!!!!!!");
			m.add(med);
			m.add(med2);
		}
		a.setQuestions(m);

		caseDataDao.insert(a);

		Anamnesis c = (Anamnesis) caseDataRepository.getByAuthor(author1);
		List<AnamnesisQuestion> testMed = c.getQuestions();

		Assert.assertNotNull(testMed);
		Assert.assertEquals(testMed.size(), 10);



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

		testCaseData = caseDataDao.insert(testCaseData);

		long caseId = c.getId();
		long authorId = author.getId();
		Iterable<CaseData> caseList = caseDataDao.listCaseDataByUserAndCase(caseId, authorId);

		Assert.assertNotNull(caseList);

		int count = 0;
		for (CaseData d : caseList) {
			count++;

			Assert.assertTrue("Instance of Data",d instanceof CaseInfo);
		}

		Assert.assertEquals(1, count);


	}

	}
