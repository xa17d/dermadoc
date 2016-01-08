package at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate;

import at.tuwien.telemedizin.dermadoc.entities.Icd10Diagnosis;
import at.tuwien.telemedizin.dermadoc.entities.Medication;
import at.tuwien.telemedizin.dermadoc.entities.casedata.*;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.CaseDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public void insert(CaseData caseData) {

		if(caseData instanceof Advice) {
			List<Medication> m = ((Advice) caseData).getMedications();
			if(m.size()>0) {
				medicationRepository.save(m);
			}

			caseDataRepository.save(caseData);

		}
		else if (caseData instanceof Diagnosis) {

			List<Icd10Diagnosis> icd = ((Diagnosis) caseData).getDiagnosisList();
			if(icd.size()>0) {
				icd10Repository.save(icd);
			}

			caseDataRepository.save(caseData);



		} else if (caseData instanceof Anamnesis) {
			List<AnamnesisQuestion> aq = ((Anamnesis) caseData).getQuestions();
			if(aq.size()>0) {
				anamnesisQuestionRepository.save(aq);
			}

			caseDataRepository.save(caseData);

		} else {
			caseDataRepository.save(caseData);
		}

	}
}
