package at.tuwien.telemedizin.dermadoc.entities;

import javax.persistence.*;

/**
 * Created by Lucas on 17.11.2015.
 */
@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    @Override
    public int hashCode() {
        if (getId() == null) { return 0; }
        return this.getId().intValue();
    }

    @Override
    public boolean equals(Object o) {

        if(o == null)
            return false;

        if(this.hashCode() != o.hashCode())
            return false;

        if(this.getClass() != o.getClass())
            return false;

        Notification n = (Notification) o;
        return this.getId().equals(n.getId());
    }
}
