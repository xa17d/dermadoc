package at.tuwien.telemedizin.dermadoc.entities.casedata;

import at.tuwien.telemedizin.dermadoc.entities.User;

import java.util.Calendar;
import java.util.List;

/**
 * Created by daniel on 24.11.2015.
 */
public class Anamnesis extends CaseData {

    public Anamnesis(long id, Calendar created, User author, String message, List<AnamnesisQuestion> medications) {
        super(id, created, author);

        this.message = message;
        this.questions = medications;
    }

    private String message;
    public String getMessage() { return message; }

    private List<AnamnesisQuestion> questions;
    public List<AnamnesisQuestion> getQuestions() { return questions; }
}
