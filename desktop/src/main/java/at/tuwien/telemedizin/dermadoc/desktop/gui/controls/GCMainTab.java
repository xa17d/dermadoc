package at.tuwien.telemedizin.dermadoc.desktop.gui.controls;

import at.tuwien.telemedizin.dermadoc.desktop.gui.Controller;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.casedata.edit.GCTextMessageEdit;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.casedata.edit.GCCaseDataEdit;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.casedata.view.GCCaseDataView;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.casedata.view.GCTextMessageView;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.casedata.TextMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Lucas on 14.11.2015.
 */
public class GCMainTab extends Tab {

    @FXML private VBox vbInput;
    @FXML private TitledPane tpPatientOverview;

    private Controller controller;
    private Case aCase;

    private GridPane gpCaseDataList;

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
        gpCaseDataList = new GridPane();
        gpCaseDataList.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(100);
        gpCaseDataList.getColumnConstraints().add(c1);

        //TODO get all case-data from backend
        //MOCK
        TextMessage message1 = new TextMessage(-1l, Calendar.getInstance(), new Patient(), "This is a text-message from the patient! ");
        GCCaseDataView gcData1 = new GCTextMessageView(message1);
        gpCaseDataList.addRow(gpCaseDataList.getChildren().size(), gcData1);

        TextMessage message2 = new TextMessage(-1l, Calendar.getInstance(), new Physician(), "This is a text-message from the physician! ");
        GCCaseDataView gcData2 = new GCTextMessageView(message2);
        gpCaseDataList.addRow(gpCaseDataList.getChildren().size(), gcData2);

        TextMessage message3 = new TextMessage(-1l, Calendar.getInstance(), new Patient(), "This is a text-message from the patient! ");
        GCCaseDataView gcData3 = new GCTextMessageView(message3);
        gpCaseDataList.addRow(gpCaseDataList.getChildren().size(), gcData3);

        GCCaseDataEdit gcData4 = new GCTextMessageEdit(controller, gpCaseDataList);
        gpCaseDataList.addRow(gpCaseDataList.getChildren().size(), gcData4);
        //----

        ScrollPane spCaseData = new ScrollPane(gpCaseDataList);
        spCaseData.setFitToWidth(true);
        vbInput.getChildren().add(spCaseData);

        //TODO define style
        spCaseData.getStylesheets().add(getClass().getResource("chat.css").toExternalForm());
    }

    @FXML
    private void newFreetext() {

        GCCaseDataEdit newFreetext = new GCTextMessageEdit(controller, gpCaseDataList);
        gpCaseDataList.addRow(gpCaseDataList.getChildren().size(), newFreetext);
    }

    @FXML
    private void newDiagnosis() {
        controller.showErrorMessage("ERROR - this button is not implemented yet!");
    }
}
