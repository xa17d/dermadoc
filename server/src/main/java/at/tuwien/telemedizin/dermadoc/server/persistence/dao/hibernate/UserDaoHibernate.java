package at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate;

import at.tuwien.telemedizin.dermadoc.entities.User;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.UserDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by daniel on 17.11.2015.
 */
@Repository
public class UserDaoHibernate implements UserDao {

    private SessionFactory sessionFactory;
    //public void setSessionFactory(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    @Autowired
    public void SomeService(EntityManagerFactory factory) {
        if(factory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.sessionFactory = factory.unwrap(SessionFactory.class);
    }

    @Override
    public User getUserById(long id) {
        Session session = sessionFactory.openSession(); // TODO: currentSession() ?

        List<User> users;
        users = session
                .createQuery("from physician where id=?")
                .setParameter(0, id).list();

        session.close();

        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }
}
