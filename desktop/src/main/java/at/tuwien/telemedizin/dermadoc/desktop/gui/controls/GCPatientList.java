package at.tuwien.telemedizin.dermadoc.desktop.gui.controls;

import at.tuwien.telemedizin.dermadoc.desktop.gui.Controller;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Gender;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    private Controller controller;

    public GCPatientList(Controller controller) {

        this.controller = controller;

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

        //TODO load patients from backend
        //MOCK
        Patient p1 = new Patient();
        p1.setName("Daniel Gehrer");
        p1.setGender(Gender.Male);

        List<Patient> patients = new ArrayList<Patient>();
        patients.add(p1);
        //----

        for(Patient p : patients) {

            //TODO get cases from controller!
            //MOCK
            List<Case> cases = new ArrayList<Case>();
            cases.add(new Case(-1l, p, Calendar.getInstance()));
            //-----

            vbPatientList.getChildren().add(new GCPatientListItem(controller, p, cases));
        }
    }
}
