package at.tuwien.telemedizin.dermadoc.entities;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by daniel on 11.11.2015.
 */
@Entity
@Table(name = "issue")
public class Case {
    public Case(long id, Patient patient, Calendar created) {
        this.id = id;
        this.patient = patient;
        this.created = created;
    }

    public Case() { }

    @Id
    //@GeneratedValue
    @Column(name = "case_id")
    private long id;
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    //@Column(name="patient_id")
    // @MapsId
    @OneToOne
    //@JoinColumn(name = "patient_id")
    private Patient patient;
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    //@Column(name="physician_id")
    //@MapsId
    @OneToOne
    // @JoinColumn(name = "physician_id")
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

    private String description;
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }


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
