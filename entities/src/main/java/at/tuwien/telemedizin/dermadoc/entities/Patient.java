package at.tuwien.telemedizin.dermadoc.entities;

import java.util.Calendar;

/**
 * Patient
 */
public class Patient extends User {
    private String svnr;
    public String getSvnr() { return svnr; }
    public void setSvnr(String svnr) { this.svnr = svnr; }

    private Calendar birthTime;
    public Calendar getBirthTime() { return birthTime; }
    public void setBirthTime(Calendar birthTime) { this.birthTime = birthTime; }

    private Gender gender;
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
}
