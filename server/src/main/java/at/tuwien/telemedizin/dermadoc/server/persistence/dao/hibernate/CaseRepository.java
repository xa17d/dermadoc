package at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.CaseStatus;
import at.tuwien.telemedizin.dermadoc.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by Lilly on 31.12.2015.
 */
public interface CaseRepository extends JpaRepository<Case, Long> {

	Case getCaseById(long id);

	Case getCaseByName(String name);

	Iterable<Case> findByPatient(User p);

	//Case findByUser(User u);
	Iterable<Case> findByStatusInOrderByCreatedAsc(Collection<CaseStatus> status);

	Iterable<Case> findByPhysician(User p);

}
