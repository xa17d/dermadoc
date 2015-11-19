package at.tuwien.telemedizin.dermadoc.entities;

/**
 * Created by Lucas on 17.11.2015.
 */
public class Notification {

    private String text;
    private long caseId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

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
