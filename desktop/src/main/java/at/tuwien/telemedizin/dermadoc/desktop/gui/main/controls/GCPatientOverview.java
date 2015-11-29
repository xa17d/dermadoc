package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls;

import at.tuwien.telemedizin.dermadoc.service.util.UtilFormat;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/**
 * Created by Lucas on 14.11.2015.
 */
public class GCPatientOverview extends GridPane {

    @FXML private Label name;
    @FXML private Label gender;
    @FXML private Label svnr;

    private Patient patient;

    public GCPatientOverview(Patient patient) {

        this.patient = patient;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gc_maintab_overview.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {

        name.setText(patient.getName());
        gender.setText(patient.getGender().toString());
        svnr.setText(patient.getSvnr() + " " + UtilFormat.formatDate(patient.getBirthTime().getTime()));
    }
}
