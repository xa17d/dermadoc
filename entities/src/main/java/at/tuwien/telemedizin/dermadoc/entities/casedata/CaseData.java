package at.tuwien.telemedizin.dermadoc.entities.casedata;

import at.tuwien.telemedizin.dermadoc.entities.Patient;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.User;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Calendar;

/**
 * Abstract CaseData.
 * A case can have multiple CaseData elements.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Advice.class),
        @JsonSubTypes.Type(value = Anamnesis.class),
        @JsonSubTypes.Type(value = CaseInfo.class),
        @JsonSubTypes.Type(value = Diagnosis.class),
        @JsonSubTypes.Type(value = PhotoMessage.class),
        @JsonSubTypes.Type(value = TextMessage.class)
})
public abstract class CaseData {

    public CaseData(long id, Calendar created, User author) {
        this.id = id;
        this.created = created;
        this.author = author;
    }

    public CaseData() { }

    private long id;
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    private Calendar created;
    public Calendar getCreated() { return created; }
    public void setCreated(Calendar created) { this.created = created; }

    private User author;
    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }

    private boolean isPrivate;
    public boolean getPrivate() { return isPrivate; }
    public void setPrivate(boolean isPrivate) { this.isPrivate = isPrivate; }

    private boolean obsolete;
    public boolean isObsolete() { return obsolete; }
    public void setObsolete(boolean obsolete) { this.obsolete = obsolete; }
}
