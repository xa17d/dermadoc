package at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.User;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Lilly on 22.12.2015.
 */
public interface CaseDataRepository extends JpaRepository<CaseData, Long> {
	Iterable<CaseData> findByCaseId(Case parentCase);
	CaseData getById(long id);

	//Iterable<CaseData> listCaseDataByUserAndCase(long caseId, long authorId);

	CaseData getByAuthor(User author);
}
