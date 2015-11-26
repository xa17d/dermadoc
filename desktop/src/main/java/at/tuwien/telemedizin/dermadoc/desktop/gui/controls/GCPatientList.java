package at.tuwien.telemedizin.dermadoc.desktop.gui.controls;

import at.tuwien.telemedizin.dermadoc.desktop.gui.Controller;
import at.tuwien.telemedizin.dermadoc.desktop.service.dto.PatientCaseMap;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Gender;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * custom list for patients
 */
public class GCPatientList extends VBox {

    @FXML private VBox vbPatientList;
    @FXML private TextField tfSearch;

    private Controller controller;
    private PatientCaseMap patientCaseMap;

    public GCPatientList(Controller controller, PatientCaseMap patientCaseMap) {

        this.controller = controller;
        this.patientCaseMap = patientCaseMap;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gc_patientlist.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        updateList();
    }

    /**
     * gets called on loading application
     * and at every keystroke in search field
     */
    @FXML
    private void updateList() {

        patientCaseMap = controller.searchPatientCaseMap(tfSearch.getText());
        ObservableList<Patient> patientList = FXCollections.observableArrayList(patientCaseMap.keySet());

        vbPatientList.getChildren().clear();
        for(Patient p : patientList) {
            vbPatientList.getChildren().add(new GCPatientListItem(controller, p, patientCaseMap.get(p)));
        }
    }
}
