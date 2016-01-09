package at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate;

import at.tuwien.telemedizin.dermadoc.entities.casedata.AnamnesisQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Lilly on 08.01.2016.
 */
public interface AnamnesisQuestionRepository extends JpaRepository<AnamnesisQuestion, Long> {
	AnamnesisQuestion getQuestionById(long id);
}
