package at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate;

import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Lilly on 22.12.2015.
 */
public interface CaseDataRepository extends PagingAndSortingRepository<CaseData, Long> {
	CaseData getByCaseId(long caseId);
	CaseData getById(long id);
	CaseData getByAuthor(long authorId);
}
