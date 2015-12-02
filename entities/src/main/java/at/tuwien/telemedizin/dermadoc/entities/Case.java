package at.tuwien.telemedizin.dermadoc.entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by daniel on 11.11.2015.
 */
public class Case {
    public Case(long id, Patient patient, Calendar created) {
        this.id = id;
        this.patient = patient;
        this.created = created;
    }

    public Case() { }

    private long id;
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    private Patient patient;
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    private Physician physician;
    public Physician getPhysician() { return physician; }
    public void setPhysician(Physician physician) { this.physician = physician; }

    private Calendar created;
    public Calendar getCreated() { return created; }
    public void setCreated(Calendar created) { this.created = created; }

    private CaseStatus status;
    public CaseStatus getStatus() { return status; }
    public void setStatus(CaseStatus status) { this.status = status; }

    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }


    @Override
    public int hashCode() {
        return (int)this.getId();
    }

    @Override
    public boolean equals(Object o) {

        if(o == null)
            return false;

        if(this.hashCode() != o.hashCode())
            return false;

        if(!(o instanceof Case))
            return false;

        Case c = (Case) o;
        return this.getId() == c.getId();
    }
}
