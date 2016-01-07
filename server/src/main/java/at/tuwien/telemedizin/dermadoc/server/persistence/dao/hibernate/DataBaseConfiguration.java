package at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Created by Lilly on 16.12.2015.
 */

@Configuration
@EnableJpaAuditing
@EntityScan
public class DataBaseConfiguration {


}
