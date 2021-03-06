package at.tuwien.telemedizin.dermadoc.app.general_entities.casedata;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



import at.tuwien.telemedizin.dermadoc.app.general_entities.User;

/**
 * Created by daniel on 24.11.2015.
 */
public class Anamnesis extends CaseData {

    public Anamnesis(Long id, Calendar created, User author, String message, List<AnamnesisQuestion> questions) {
        super(id, created, author);

        this.message = message;
        this.questions = questions;
    }

    public Anamnesis() {
        questions = new ArrayList<AnamnesisQuestion>();
    }

    private String message;
    public String getMessage() { return message; }

    private List<AnamnesisQuestion> questions;
    public List<AnamnesisQuestion> getQuestions() { return questions; }
}
