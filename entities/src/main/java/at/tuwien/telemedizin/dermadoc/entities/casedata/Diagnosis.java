package at.tuwien.telemedizin.dermadoc.entities.casedata;

import at.tuwien.telemedizin.dermadoc.entities.User;

import java.util.Calendar;

/**
 * Diagnosis by a physician
 */
public class Diagnosis extends CaseData {

    public Diagnosis(long id, Calendar created, User author, String message) {
        super(id, created, author);

        this.message = message;
    }

    public Diagnosis() { }

    private String message;
    public String getMessage() { return message; }
}
