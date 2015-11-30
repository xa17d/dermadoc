package at.tuwien.telemedizin.dermadoc.server.persistence.dao.mock;

import at.tuwien.telemedizin.dermadoc.entities.*;
import at.tuwien.telemedizin.dermadoc.server.exceptions.EntityNotFoundException;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.UserDao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Created by daniel on 23.11.2015.
 */
@Repository
public class UserDaoMock implements UserDao {
    public UserDaoMock() {
        this.users = MockData.users;
    }

    private ArrayList<User> users;


    @Override
    public User getUserById(long id) throws EntityNotFoundException {
        for (User u:users) {
            if (u.getId() == id) {
                return  u;
            }
        }
        throw new EntityNotFoundException("id="+id);
    }

    @Override
    public User getUserByMail(String mail) throws EntityNotFoundException {
        for (User u:users) {
            if (u.getMail().toLowerCase().equals(mail.toLowerCase())) {
                return  u;
            }
        }
        throw new EntityNotFoundException("mail="+mail);
    }
}
