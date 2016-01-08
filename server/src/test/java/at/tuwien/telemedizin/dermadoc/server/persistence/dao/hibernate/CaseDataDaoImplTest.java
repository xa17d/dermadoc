package at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Icd10Diagnosis;
import at.tuwien.telemedizin.dermadoc.entities.Medication;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.casedata.Advice;
import at.tuwien.telemedizin.dermadoc.entities.casedata.Diagnosis;
import at.tuwien.telemedizin.dermadoc.server.Application;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Lilly on 08.01.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
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



		}

	@Test
	public void TestIcd10 () {
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

		for (int i= 0; i<5; i++) {
			Icd10Diagnosis med = new Icd10Diagnosis();
			med.getIcd10Code("404");
			med.getIcd10Name("illness"+i)
			m.add(med);
		}
		a.setDiagnosisList(m);

		caseDataDao.insert(a);

		Advice c = (Advice) caseDataRepository.getByAuthor(author1);
		List<Medication> testMed = c.getMedications();

		Assert.assertNotNull(testMed);



	}
	}
