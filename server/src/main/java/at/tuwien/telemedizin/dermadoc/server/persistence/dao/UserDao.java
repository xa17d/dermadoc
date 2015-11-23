package at.tuwien.telemedizin.dermadoc.server.persistence.dao;

import at.tuwien.telemedizin.dermadoc.entities.User;

/**
 * Created by daniel on 17.11.2015.
 */
public interface UserDao {

    public User getUserById(long id) throws EntityNotFoundException;

    public User getUserByMail(String mail) throws EntityNotFoundException;

}
