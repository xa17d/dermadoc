package at.tuwien.telemedizin.dermadoc.desktop.gui.controls;

import at.tuwien.telemedizin.dermadoc.desktop.gui.Controller;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.casedata.GCAdvice;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.casedata.GCCaseData;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Lucas on 14.11.2015.
 */
public class GCMainTab extends Tab {

    @FXML private VBox vbInput;
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

        //Chat window
        GridPane gpCaseDataList = new GridPane();
        gpCaseDataList.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(100);
        gpCaseDataList.getColumnConstraints().add(c1);

        //TODO get all case-data from backend
        //MOCK
        GCCaseData gcData = new GCAdvice(null);
        GridPane.setHalignment(gcData, gcData.getHPos());
        gpCaseDataList.addRow(0, gcData);
        //----

        ScrollPane spCaseData = new ScrollPane(gpCaseDataList);
        spCaseData.setFitToWidth(true);
        vbInput.getChildren().add(spCaseData);

        spCaseData.getStylesheets().add(getClass().getResource("chat.css").toExternalForm());
    }

    @FXML
    private void newFreetext() {
        //pnInput.getChildren().add(new Label("new freetext"));
    }
}
