package at.tuwien.telemedizin.dermadoc.server.persistence.dao.mock;

import at.tuwien.telemedizin.dermadoc.server.persistence.dao.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by daniel on 23.11.2015.
 */
@Configuration
public class MockConfiguation {
    @Bean
    public UserDao userDao() {
        return new UserDaoMock();
    }
}
