package at.tuwien.telemedizin.dermadoc.desktop.gui.controls;

import at.tuwien.telemedizin.dermadoc.desktop.gui.MainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Created by Lucas on 12.11.2015.
 */
public class GCPatientList extends VBox {

    @FXML private VBox vbPatientList;

    private MainController controller;

    public GCPatientList(MainController controller) {

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
        //nothing to do
    }

    public void updateList() {
        //TODO set the list of patients
        vbPatientList.getChildren().add(new GCPatientListItem());
        vbPatientList.getChildren().add(new GCPatientListItem());
        vbPatientList.getChildren().add(new GCPatientListItem());
        vbPatientList.getChildren().add(new GCPatientListItem());
        vbPatientList.getChildren().add(new GCPatientListItem());
        vbPatientList.getChildren().add(new GCPatientListItem());
        vbPatientList.getChildren().add(new GCPatientListItem());
        vbPatientList.getChildren().add(new GCPatientListItem());
        vbPatientList.getChildren().add(new GCPatientListItem());
        vbPatientList.getChildren().add(new GCPatientListItem());
        vbPatientList.getChildren().add(new GCPatientListItem());
        vbPatientList.getChildren().add(new GCPatientListItem());
    }
}
