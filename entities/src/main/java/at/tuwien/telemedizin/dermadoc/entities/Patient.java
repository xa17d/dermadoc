package at.tuwien.telemedizin.dermadoc.entities;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Calendar;
import java.util.Objects;

/**
 * Patient
 */
public class Patient extends User {
    private StringProperty svnr = new SimpleStringProperty();
    public String getSvnr() { return svnr.get(); }
    public void setSvnr(String svnr) { this.svnr.set(svnr); }
    public StringProperty svnrProperty() { return svnr; }

    private ObjectProperty<Calendar> birthTime = new SimpleObjectProperty<Calendar>();
    public Calendar getBirthTime() { return birthTime.get(); }
    public void setBirthTime(Calendar birthTime) { this.birthTime.set(birthTime); }
    public ObjectProperty<Calendar> birthTimeProperty() { return birthTime; }

    private ObjectProperty<Gender> gender = new SimpleObjectProperty<Gender>();
    public Gender getGender() { return gender.get(); }
    public void setGender(Gender gender) { this.gender.set(gender); }
    public ObjectProperty<Gender> genderProperty() { return gender; }
}
