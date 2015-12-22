package at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate;

import at.tuwien.telemedizin.dermadoc.entities.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDaoHibernate extends PagingAndSortingRepository<User, Long> {
	User getUserById(long userId);

	User getUserByMail(String mail);

	//List<Physician> listPhysicians();

}



