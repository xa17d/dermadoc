package at.tuwien.telemedizin.dermadoc.entities.casedata;

import at.tuwien.telemedizin.dermadoc.entities.User;

import java.util.Calendar;

/**
 * Diagnose by a physician
 */
public class Diagnose extends CaseData {

    public Diagnose(long id, Calendar created, User author, String message) {
        super(id, created, author);

        this.message = message;
    }

    private String message;
    public String getMessage() { return message; }
}
