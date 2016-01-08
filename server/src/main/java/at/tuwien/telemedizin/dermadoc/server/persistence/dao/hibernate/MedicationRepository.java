package at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate;

import at.tuwien.telemedizin.dermadoc.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Lilly on 08.01.2016.
 */
public interface MedicationRepository extends JpaRepository<Medication, Long> {
	Medication getMedicationByMedicationId(long Id);
}
