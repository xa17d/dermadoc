package at.tuwien.telemedizin.dermadoc.server.persistence.dao;

import at.tuwien.telemedizin.dermadoc.entities.User;
import org.springframework.stereotype.Repository;

/**
 * Created by daniel on 17.11.2015.
 */
@Repository
public interface UserDao {

    public User getUserById(long id);

}
