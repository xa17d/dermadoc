package at.tuwien.telemedizin.dermadoc.desktop.gui.controls;

import at.tuwien.telemedizin.dermadoc.entities.Patient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Lucas on 14.11.2015.
 */
public class GCMainTabOverview extends GridPane {

    @FXML private Label name;
    @FXML private Label gender;
    @FXML private Label svnr;

    private Patient patient;
    private DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    public GCMainTabOverview(Patient patient) {

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
        svnr.setText(patient.getSvnr() + " " + sdf.format(patient.getBirthTime().getTime()));
    }
}
