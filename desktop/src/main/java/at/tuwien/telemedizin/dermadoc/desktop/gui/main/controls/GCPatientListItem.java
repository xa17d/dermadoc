package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls;

import at.tuwien.telemedizin.dermadoc.desktop.gui.main.Controller;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.buttons.GCButtonOpen;
import at.tuwien.telemedizin.dermadoc.service.util.UtilFormat;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import javafx.collections.ObservableList;
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

    private Controller controller;
    private Patient patient;
    private ObservableList<Case> cases;

    public GCPatientListItem(Controller controller, Patient patient, ObservableList<Case> cases) {

        this.controller = controller;
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

        this.setText(patient.getName());
        this.setExpanded(false);

        int i = 0;
        for(Case c : cases) {

            gpCaseList.add(new Label(c.getName()), 0, i);
            gpCaseList.add(new Label(UtilFormat.formatDate(c.getCreated())), 1, i);

            Button btOpen = new GCButtonOpen(c, controller.getOpenMainTabHandler());
            GridPane.setHalignment(btOpen, HPos.RIGHT);
            gpCaseList.add(btOpen, 2, i);

            i++;
        }
    }
}
