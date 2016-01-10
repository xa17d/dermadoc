package at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Icd10Diagnosis;
import at.tuwien.telemedizin.dermadoc.entities.Medication;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.casedata.*;
import at.tuwien.telemedizin.dermadoc.entities.rest.CaseDataList;
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
import java.util.GregorianCalendar;
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

	@Test
	public void TestAdviceObsolete () {
		Physician author1 = new Physician();
		author1.setMail("authorMail989");
		author1.setPassword("qweqwe");
		author1 = userRepository.save(author1);

		Case testCase = new Case();
		testCase.setPhysician(author1);
		testCase.setName("Case TestAdviceObsolete");
		testCase = caseRepository.save(testCase);

		Advice a1 = new Advice();
		a1.setCase(testCase);
		a1.setAuthor(author1);
		a1.setObsolete(false);
		a1.setMessage("FIRST ADVICE");
		Calendar date1 = GregorianCalendar.getInstance();
		date1.add(GregorianCalendar.SECOND, -30);
		a1.setCreated(date1);

		Diagnosis d1 = new Diagnosis();
		d1.setCase(testCase);
		d1.setAuthor(author1);
		d1.setObsolete(false);
		d1.setMessage("FIRST DIAGNOSIS");
		Calendar date2 = GregorianCalendar.getInstance();
		date2.add(GregorianCalendar.SECOND, -20);
		d1.setCreated(date2);

		Advice a2 = new Advice();
		a2.setCase(testCase);
		a2.setAuthor(author1);
		a2.setObsolete(false);
		a2.setMessage("SECOND ADVICE");
		Calendar date3 = GregorianCalendar.getInstance();
		date3.add(GregorianCalendar.SECOND, -10);
		a2.setCreated(date3);

		Diagnosis d2 = new Diagnosis();
		d2.setCase(testCase);
		d2.setAuthor(author1);
		d2.setObsolete(false);
		d2.setMessage("SECOND DIAGNOSIS");
		Calendar date4 = GregorianCalendar.getInstance();
		date4.add(GregorianCalendar.SECOND, 0);
		d2.setCreated(date4);


		CaseDataList caseData;

		// INSERT ADVICE 1
		caseDataDao.insert(a1);
		caseData = new CaseDataList(caseDataDao.listCaseDataByUserAndCase(testCase.getId(), author1.getId()));
		Assert.assertEquals("Count after 1st insert", 1, caseData.size());

		Assert.assertEquals("item 0 type", true, caseData.get(0) instanceof Advice);
		Assert.assertEquals("item 0 obsolete", false, caseData.get(0).isObsolete());

		sleep();

		// INSERT DIAGNOSIS 1
		caseDataDao.insert(d1);
		caseData = new CaseDataList(caseDataDao.listCaseDataByUserAndCase(testCase.getId(), author1.getId()));
		Assert.assertEquals("Count after 2st insert", 2, caseData.size());

		Assert.assertEquals("item 0 type", true, caseData.get(0) instanceof Advice);
		Assert.assertEquals("item 0 obsolete", false, caseData.get(0).isObsolete());

		Assert.assertEquals("item 1 type", true, caseData.get(1) instanceof Diagnosis);
		Assert.assertEquals("item 1 obsolete", false, caseData.get(1).isObsolete());

		sleep();

		// INSERT ADVICE 2
		caseDataDao.insert(a2);
		caseData = new CaseDataList(caseDataDao.listCaseDataByUserAndCase(testCase.getId(), author1.getId()));
		Assert.assertEquals("Count after 3rd insert", 3, caseData.size());

		Assert.assertEquals("item 0 type", true, caseData.get(0) instanceof Advice);
		Assert.assertEquals("item 0 obsolete", true, caseData.get(0).isObsolete());

		Assert.assertEquals("item 1 type", true, caseData.get(1) instanceof Diagnosis);
		Assert.assertEquals("item 1 obsolete", false, caseData.get(1).isObsolete());

		Assert.assertEquals("item 2 type", true, caseData.get(2) instanceof Advice);
		Assert.assertEquals("item 2 obsolete", false, caseData.get(2).isObsolete());

		sleep();

		// INSERT DIAGNOSIS 2
		caseDataDao.insert(d2);
		caseData = new CaseDataList(caseDataDao.listCaseDataByUserAndCase(testCase.getId(), author1.getId()));
		Assert.assertEquals("Count after 4th insert", 4, caseData.size());

		Assert.assertEquals("item 0 type", true, caseData.get(0) instanceof Advice);
		Assert.assertEquals("item 0 obsolete", true, caseData.get(0).isObsolete());

		Assert.assertEquals("item 1 type", true, caseData.get(1) instanceof Diagnosis);
		Assert.assertEquals("item 1 obsolete", true, caseData.get(1).isObsolete());

		Assert.assertEquals("item 2 type", true, caseData.get(2) instanceof Advice);
		Assert.assertEquals("item 2 obsolete", false, caseData.get(2).isObsolete());

		Assert.assertEquals("item 3 type", true, caseData.get(3) instanceof Diagnosis);
		Assert.assertEquals("item 3 obsolete", false, caseData.get(3).isObsolete());
	}

	private void sleep() {
		Calendar c = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		while (now.equals(c)) {
			now = Calendar.getInstance();
		}
	}

}
