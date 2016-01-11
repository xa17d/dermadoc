package at.tuwien.telemedizin.dermadoc.app.general_entities.casedata;

import java.util.Calendar;


import at.tuwien.telemedizin.dermadoc.app.general_entities.User;

/**
 * Unstructured text message.
 */

public class TextMessage extends CaseData {

    public TextMessage(Long id, Calendar created, User author, String message) {
        super(id, created, author);

        this.message = message;
    }

    public TextMessage() { }

    private String message;
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
