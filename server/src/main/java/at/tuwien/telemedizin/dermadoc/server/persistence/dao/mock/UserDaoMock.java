/*
package at.tuwien.telemedizin.dermadoc.server.persistence.dao.mock;

import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.User;
import at.tuwien.telemedizin.dermadoc.server.exceptions.EntityNotFoundException;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.UserDao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by daniel on 23.11.2015.
 *//*

@Repository
public class UserDaoMock implements UserDao {
    public UserDaoMock() {
        this.users = MockData.users;
    }

    private ArrayList<User> users;


    @Override
    public User getUserById(long userId) {
        for (User u : users) {
            if (u.getId() == userId) {
                return u;
            }
        }
        throw new EntityNotFoundException("id="+userId);
    }

    @Override
    public User getUserByMail(String mail) {
        for (User u : users) {
            if (u.getMail().toLowerCase().equals(mail.toLowerCase())) {
                return u;
            }
        }
        throw new EntityNotFoundException("mail="+mail);
    }

    @Override
    public List<Physician> listPhysicians() {
        ArrayList<Physician> result = new ArrayList<>();

        for (User u : users) {
            if (u instanceof Physician) {
                result.add((Physician)u);
            }
        }

        return result;
    }
}
*/
