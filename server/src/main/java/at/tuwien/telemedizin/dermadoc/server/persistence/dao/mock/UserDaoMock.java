package at.tuwien.telemedizin.dermadoc.server.persistence.dao.mock;

import at.tuwien.telemedizin.dermadoc.entities.*;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.EntityNotFoundException;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
