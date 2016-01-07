package at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate;

import at.tuwien.telemedizin.dermadoc.entities.Patient;
import at.tuwien.telemedizin.dermadoc.entities.User;

/**
 * Created by Lilly on 07.01.2016.
 */
public interface PatientRepository extends PagingAndSortingRepository<User, Long{

	Patient findbyId(long id);
}
