package at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate;

import at.tuwien.telemedizin.dermadoc.entities.Icd10Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Lilly on 08.01.2016.
 */
public interface Icd10Repository extends JpaRepository<Icd10Diagnosis, Long>{
	Icd10Diagnosis getDiagonseById(long id);

}
