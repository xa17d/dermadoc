package at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Icd10Diagnosis;
import at.tuwien.telemedizin.dermadoc.entities.Medication;
import at.tuwien.telemedizin.dermadoc.entities.casedata.*;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.CaseDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Lilly on 08.01.2016.
 */
@Service
public class CaseDataDaoImpl implements CaseDataDao {


	@Autowired
	MedicationRepository medicationRepository;

	@Autowired
	CaseDataRepository caseDataRepository;

	@Autowired
	Icd10Repository icd10Repository;

	@Autowired
	AnamnesisQuestionRepository anamnesisQuestionRepository;

	@Override
	public Iterable<CaseData> listCaseDataByUserAndCase(long caseId, long userId) {
		Case parentCase = new Case();
		parentCase.setId(caseId);

		Iterable<CaseData> caseData = caseDataRepository.findByCaseId(parentCase);
		ArrayList<CaseData> result = new ArrayList<CaseData>();

		for (CaseData c : caseData) {
			if (!c.getPrivate() || c.getAuthor().getId() == userId) {
				result.add(c);
			}
		}

		Collections.sort(result, new Comparator<CaseData>() {
			@Override
			public int compare(CaseData o1, CaseData o2) {
				return o1.getCreated().compareTo(o2.getCreated());
			}
		});

		return result;
	}

	@Override
	public CaseData insert(CaseData caseData) {

		// Insert Subitems and CaseData
		if(caseData instanceof Advice) {
			List<Medication> m = ((Advice) caseData).getMedications();
			if(m.size()>0) {
				medicationRepository.save(m);
			}

			caseData = caseDataRepository.save(caseData);

		}
		else if (caseData instanceof Diagnosis) {

			List<Icd10Diagnosis> icd = ((Diagnosis) caseData).getDiagnosisList();
			if(icd.size()>0) {
				icd10Repository.save(icd);
			}

			caseData = caseDataRepository.save(caseData);



		} else if (caseData instanceof Anamnesis) {
			List<AnamnesisQuestion> aq = ((Anamnesis) caseData).getQuestions();
			if(aq.size()>0) {
				anamnesisQuestionRepository.save(aq);
			}

			caseData = caseDataRepository.save(caseData);

		} else {
			caseData = caseDataRepository.save(caseData);
		}

		// Set previous CaseData of Diagnosis / Anamnesis obsolete
		Iterable<CaseData> caseDataOfCase = caseDataRepository.findByCaseId(caseData.getCase());

		for (CaseData c : caseDataOfCase) {
			if (caseData.getClass().equals(c.getClass())) {
				// make sure it is a different object, and not yet obsolete
				if (c.getId() != caseData.getId() && !c.isObsolete())
					// if creation time of c was before caseData
					if (c.getCreated().compareTo(caseData.getCreated()) < 0) {
						// obsolete
						c.setObsolete(true);
						// update db
						caseDataRepository.save(c);
					}
			}
		}


		return caseData;
	}
}
