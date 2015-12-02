package at.tuwien.telemedizin.dermadoc.entities;

/**
 * Created by Lucas on 17.11.2015.
 */
public class Notification {

    private long id;
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    private long userId;
    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    private String text;
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    private long caseId;
    public long getCaseId() {
        return caseId;
    }
    public void setCaseId(long caseId) {
        this.caseId = caseId;
    }

    @Override
    public String toString() {
        return text;
    }
}
