package at.tuwien.telemedizin.dermadoc.entities;

import javafx.fxml.FXML;

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

    private long id;
    public long getId() { return id; }

    private Patient patient;
    public Patient getPatient() { return patient; }

    private Physician physician;
    public Physician getPhysician() { return physician; }
    public void setPhysician(Physician physician) { this.physician = physician; }

    private Calendar created;
    public Calendar getCreated() { return created; }

    private CaseStatus status;
    public CaseStatus getStatus() { return status; }
    public void setStatus(CaseStatus status) { this.status = status; }

    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
