package at.tuwien.telemedizin.dermadoc.desktop.gui.controls;

import at.tuwien.telemedizin.dermadoc.desktop.gui.MainController;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.buttons.GCButtonOpen;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.List;

/**
 * custom titled pane item for patients list
 */
public class GCPatientListItem extends TitledPane {

    @FXML private GridPane gpCaseList;

    private MainController controller;
    private Patient patient;
    private List<Case> cases;

    public GCPatientListItem(MainController controller, Patient patient, List<Case> cases) {

        this.patient = patient;
        this.cases = cases;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gc_patientlist_item.fxml"));
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

        //TODO just MOCKing - get data from patient object
        this.setText(patient.getName());
        this.setExpanded(false);

        Button btOpen = new GCButtonOpen();
        //TODO eventhandler on click
        GridPane.setHalignment(btOpen, HPos.RIGHT);

        //TODO write cases into gridpane

        //MOCK
        gpCaseList.add(new Label("Rash on Hand"), 0, 0);
        gpCaseList.add(new Label("12.11.2015"), 1, 0);
        gpCaseList.add(new GCButtonOpen(), 2, 0);

        gpCaseList.add(new Label("Rash on Hand"), 0, 1);
        gpCaseList.add(new Label("12.11.2015"), 1, 1);
        gpCaseList.add(new GCButtonOpen(), 2, 1);

        gpCaseList.add(new Label("Rash on Hand"), 0, 2);
        gpCaseList.add(new Label("12.11.2015"), 1, 2);
        gpCaseList.add(new GCButtonOpen(), 2, 2);
        //-----
    }
}
