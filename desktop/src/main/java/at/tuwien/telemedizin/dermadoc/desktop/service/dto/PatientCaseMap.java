package at.tuwien.telemedizin.dermadoc.desktop.service.dto;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import javafx.collections.*;

import java.util.List;
import java.util.Set;

/**
 * Created by Lucas on 26.11.2015.
 */
public class PatientCaseMap {

    private ObservableMap<Patient, ObservableList<Case>> map = FXCollections.observableHashMap();

    public void put(Case aCase) {

        //patient already in map - add case
        if(map.containsKey(aCase.getPatient())) {
            ObservableList<Case> patientCaseList = map.get(aCase.getPatient());
            patientCaseList.add(aCase);
        }
        //create map entry for patient
        else {
            ObservableList<Case> patientCaseList = FXCollections.observableArrayList();
            map.put(aCase.getPatient(), patientCaseList);
            patientCaseList.add(aCase);
        }
    }

    //TODO necessary??
    public void putAll(List<Case> cases) {

        for(Case c : cases) {
            this.put(c);
        }
    }

    public ObservableList<Case> get(Patient patient) {
        return map.get(patient);
    }

    public Set<Patient> keySet() {
        return map.keySet();
    }

    public void addMapListener(MapChangeListener<Patient, ObservableList<Case>> listener) {
        map.addListener(listener);
    }

    public void addListListener(ListChangeListener<Case> listener) {
        for(Patient p : map.keySet()) {
            map.get(p).addListener(listener);
        }
    }


    public void sort() {
        //TODO
    }
}
