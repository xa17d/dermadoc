package at.tuwien.telemedizin.dermadoc.entities.casedata;

import at.tuwien.telemedizin.dermadoc.entities.Icd10Diagnosis;
import at.tuwien.telemedizin.dermadoc.entities.User;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Diagnosis by a physician
 */
@Entity
@Table(name = "diagnosis")
public class Diagnosis extends CaseData {

    public Diagnosis(long id, Calendar created, User author, String message, List<Icd10Diagnosis> diagnosisList) {
        super(id, created, author);

        this.message = message;
        this.diagnosisList = diagnosisList;
    }

    public Diagnosis() { }


    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
    public String getMessage() { return message; }

    public void setDiagnosisList(List<Icd10Diagnosis> diagnosisList) {
        this.diagnosisList = diagnosisList;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Icd10Diagnosis> diagnosisList = new ArrayList<>();
    public List<Icd10Diagnosis> getDiagnosisList() { return diagnosisList; }
}