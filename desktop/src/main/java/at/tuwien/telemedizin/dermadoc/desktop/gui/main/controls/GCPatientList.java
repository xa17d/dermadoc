package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls;

import at.tuwien.telemedizin.dermadoc.desktop.gui.main.Controller;
import at.tuwien.telemedizin.dermadoc.desktop.service.dto.PatientCaseMap;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

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

        /*
        this.patientCaseMap.addListListener(new ListChangeListener<Case>() {
            @Override
            public void onChanged(Change<? extends Case> c) {
                updateList();
            }
        });
        */
        this.patientCaseMap.addMapListener(new MapChangeListener<Patient, ObservableList<Case>>() {
            @Override
            public void onChanged(Change<? extends Patient, ? extends ObservableList<Case>> change) {
                updateList();
                change.getValueAdded().addListener(new ListChangeListener<Case>() {
                    @Override
                    public void onChanged(Change<? extends Case> c) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                updateList();
                            }
                        });
                    }
                });
            }
        });
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
    public void updateList() {

        patientCaseMap = controller.searchPatientCaseMap(tfSearch.getText());
        ObservableList<Patient> patientList = FXCollections.observableArrayList(patientCaseMap.keySet());

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbPatientList.getChildren().clear();
                for(Patient p : patientList) {
                    vbPatientList.getChildren().add(new GCPatientListItem(controller, p, patientCaseMap.get(p)));
                }
            }
        });
    }
}
