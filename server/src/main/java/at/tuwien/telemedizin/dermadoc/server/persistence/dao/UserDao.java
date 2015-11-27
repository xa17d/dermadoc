package at.tuwien.telemedizin.dermadoc.server.persistence.dao;

import at.tuwien.telemedizin.dermadoc.entities.User;

/**
 * Created by daniel on 17.11.2015.
 */
public interface UserDao {

    User getUserById(long id) throws EntityNotFoundException;

    User getUserByMail(String mail) throws EntityNotFoundException;

}
