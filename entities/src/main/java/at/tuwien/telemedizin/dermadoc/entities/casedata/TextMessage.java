package at.tuwien.telemedizin.dermadoc.entities.casedata;

import at.tuwien.telemedizin.dermadoc.entities.User;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Calendar;

/**
 * Unstructured text message.
 */
@Entity
@Table(name = "text_message")
public class TextMessage extends CaseData {

    public TextMessage(long id, Calendar created, User author, String message) {
        super(id, created, author);

        this.message = message;
    }

    public TextMessage() { }

    private String message;
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
