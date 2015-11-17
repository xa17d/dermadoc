package at.tuwien.telemedizin.dermadoc.desktop.gui.controls;

import at.tuwien.telemedizin.dermadoc.desktop.gui.Controller;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Created by Lucas on 14.11.2015.
 */
public class GCMainTab extends Tab {

    @FXML private Pane pnInput;
    @FXML private TitledPane tpPatientOverview;

    private Controller controller;
    private Case aCase;

    public GCMainTab(Controller controller, Case aCase) {

        this.controller = controller;
        this.aCase = aCase;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gc_maintab.fxml"));
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

        this.setText(aCase.getPatient().getName());
        tpPatientOverview.setContent(new GCMainTabOverview(aCase.getPatient()));
    }

    @FXML
    private void newFreetext() {
        pnInput.getChildren().add(new Label("new freetext"));
    }
}
