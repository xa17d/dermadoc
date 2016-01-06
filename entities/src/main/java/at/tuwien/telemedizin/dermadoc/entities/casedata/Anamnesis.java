package at.tuwien.telemedizin.dermadoc.entities.casedata;

import at.tuwien.telemedizin.dermadoc.entities.User;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by daniel on 24.11.2015.
 */
@Entity
public class Anamnesis extends CaseData {

    public Anamnesis(long id, Calendar created, User author, String message, List<AnamnesisQuestion> questions) {
        super(id, created, author);

        this.message = message;
        this.questions = questions;
    }

    public Anamnesis() {
        questions = new ArrayList<AnamnesisQuestion>();
    }

    private String message;
    public String getMessage() { return message; }

    @ElementCollection
    private List<AnamnesisQuestion> questions;
    public List<AnamnesisQuestion> getQuestions() { return questions; }
}
