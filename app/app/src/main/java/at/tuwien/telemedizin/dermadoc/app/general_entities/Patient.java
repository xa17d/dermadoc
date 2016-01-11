package at.tuwien.telemedizin.dermadoc.app.general_entities;

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
    public Gender getGender() { return gender != null ? gender : Gender.Undefined; }
    public void setGender(Gender gender) { this.gender = gender; }

    @Override
    public String toString() {
        return "[Patient] "+super.toString();
    }
}
