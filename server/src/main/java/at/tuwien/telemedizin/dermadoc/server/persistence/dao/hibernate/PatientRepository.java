package at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate;

import at.tuwien.telemedizin.dermadoc.entities.Patient;
import at.tuwien.telemedizin.dermadoc.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Lilly on 07.01.2016.
 */
public interface PatientRepository extends JpaRepository<User, Long> {

	Patient findById(long id);
	//Patient findByEmail(String email);

}
