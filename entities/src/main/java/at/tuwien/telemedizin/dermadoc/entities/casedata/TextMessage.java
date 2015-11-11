package at.tuwien.telemedizin.dermadoc.entities.casedata;

import at.tuwien.telemedizin.dermadoc.entities.User;

import java.util.Calendar;

/**
 * Unstructured text message.
 */
public class TextMessage extends CaseData {

    public TextMessage(long id, Calendar created, User author, String message) {
        super(id, created, author);

        this.message = message;
    }

    private String message;
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
