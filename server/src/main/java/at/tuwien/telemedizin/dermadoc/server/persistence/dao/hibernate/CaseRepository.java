package at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Lilly on 31.12.2015.
 */
public interface CaseRepository extends PagingAndSortingRepository<Case, Long> {

	Case getById(long id);
	Case getByName(String name);

}
