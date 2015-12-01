package at.tuwien.telemedizin.dermadoc.server.persistence.dao;

import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.User;
import at.tuwien.telemedizin.dermadoc.server.exceptions.EntityNotFoundException;

import java.util.List;

/**
 * Created by daniel on 17.11.2015.
 */
public interface UserDao {

    User getUserById(long userId);
    User getUserByMail(String mail);

    List<Physician> listPhysicians();
}
