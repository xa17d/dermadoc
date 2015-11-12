package at.tuwien.telemedizin.dermadoc.desktop.gui.controls;

import at.tuwien.telemedizin.dermadoc.entities.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Lucas on 12.11.2015.
 */
public class GCNewCasesList {

    private ObservableList<Patient> patients = FXCollections.observableArrayList();

    public GCNewCasesList() {

        Patient p1 = new Patient();
        p1.setName("abc");

        patients.add(p1);
        patients.add(p1);
        patients.add(p1);
    }

    public ObservableList<Patient> getPatients() {
        return patients;
    }
}
