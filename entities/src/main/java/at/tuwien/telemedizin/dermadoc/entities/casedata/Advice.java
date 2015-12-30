package at.tuwien.telemedizin.dermadoc.entities.casedata;

import at.tuwien.telemedizin.dermadoc.entities.Medication;
import at.tuwien.telemedizin.dermadoc.entities.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Advice from a Physician
 */

public class Advice extends CaseData {

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

    private List<Medication> medications = new ArrayList<>();
    public List<Medication> getMedications() { return medications; }
}
