package at.tuwien.telemedizin.dermadoc.entities;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Patient
 */
@Entity
@Embeddable
@Table(name = "patient")
@PrimaryKeyJoinColumn(name = "person_id")
public class Patient extends User {

    @Column(nullable = false, unique = true)
    private String svnr;
    public String getSvnr() { return svnr; }
    public void setSvnr(String svnr) { this.svnr = svnr; }

    private Calendar birthTime;
    public Calendar getBirthTime() { return birthTime; }
    public void setBirthTime(Calendar birthTime) { this.birthTime = birthTime; }

    private Gender gender;
    public Gender getGender() { return gender != null ? gender : Gender.Undefined; }
    public void setGender(Gender gender) { this.gender = gender; }

    @Override
    public String toString() {
        return "[Patient] "+super.toString();
    }
}
