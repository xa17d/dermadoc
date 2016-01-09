package at.tuwien.telemedizin.dermadoc.entities.casedata;

import at.tuwien.telemedizin.dermadoc.entities.Medication;
import at.tuwien.telemedizin.dermadoc.entities.User;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Advice from a Physician
 */
@Entity
public class Advice extends CaseData {

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public Advice(long id, Calendar created, User author, String message, List<Medication> medications) {
        super(id, created, author);

        this.message = message;
        this.medications = medications;
    }

    public Advice() {
        medications = new ArrayList<Medication>();
    }

    private String message;
    public String getMessage() { return message; }

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Medication> medications = new ArrayList<>();
    public List<Medication> getMedications() { return medications; }
}
