package at.tuwien.telemedizin.dermadoc.entities.casedata;

import at.tuwien.telemedizin.dermadoc.entities.User;

import java.util.Calendar;

/**
 * Abstract CaseData.
 * A case can have multiple CaseData elements.
 */
public abstract class CaseData {

    public CaseData(long id, Calendar created, User author) {
        this.id = id;
        this.created = created;
        this.author = author;
    }

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

    private CaseData nextVersion;
    public CaseData getNextVersion() { return nextVersion; }
    public void setNextVersion(CaseData nextVersion) { this.nextVersion = nextVersion; }

    private boolean isObsolete() { return (nextVersion != null); }
}
