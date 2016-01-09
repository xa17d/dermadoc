package at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Icd10Diagnosis;
import at.tuwien.telemedizin.dermadoc.entities.Medication;
import at.tuwien.telemedizin.dermadoc.entities.casedata.*;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.CaseDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

		Iterable<CaseData> cases = caseDataRepository.findByCaseId(parentCase);
		ArrayList<CaseData> result = new ArrayList<CaseData>();

		for (CaseData c : cases) {
			if (!c.getPrivate() || c.getAuthor().getId() == userId) {
				result.add(c);
			}
		}

		return result;
	}

	@Override
	public CaseData insert(CaseData caseData) {

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

		return caseData;
	}
}
