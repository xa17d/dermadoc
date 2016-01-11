package at.tuwien.telemedizin.dermadoc.app.general_entities.casedata;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import at.tuwien.telemedizin.dermadoc.app.general_entities.Icd10Diagnosis;
import at.tuwien.telemedizin.dermadoc.app.general_entities.User;

/**
 * Diagnosis by a physician
 */

public class Diagnosis extends CaseData {

    public Diagnosis(Long id, Calendar created, User author, String message, List<Icd10Diagnosis> diagnosisList) {
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