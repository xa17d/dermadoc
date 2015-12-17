package at.tuwien.telemedizin.dermadoc.entities.casedata;

import at.tuwien.telemedizin.dermadoc.entities.Icd10Diagnosis;
import at.tuwien.telemedizin.dermadoc.entities.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Diagnosis by a physician
 */
public class Diagnosis extends CaseData {

    public Diagnosis(long id, Calendar created, User author, String message, List<Icd10Diagnosis> diagnosisList) {
        super(id, created, author);

        this.message = message;
        this.diagnosisList = diagnosisList;
    }

    public Diagnosis() { }

    private String message;
    public String getMessage() { return message; }

    private List<Icd10Diagnosis> diagnosisList = new ArrayList<>();
    public List<Icd10Diagnosis> getDiagnosisList() { return diagnosisList; }
}