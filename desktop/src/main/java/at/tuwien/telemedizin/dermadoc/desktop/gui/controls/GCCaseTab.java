package at.tuwien.telemedizin.dermadoc.desktop.gui.controls;

import at.tuwien.telemedizin.dermadoc.desktop.gui.Controller;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.casedata.GCCaseDataList;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.casedata.edit.GCTextMessageEdit;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Created by Lucas on 14.11.2015.
 */
public class GCCaseTab extends Tab {

    @FXML private VBox vbInput;
    @FXML private TitledPane tpPatientOverview;

    private Controller controller;
    private TabPane tabPane;
    private Case aCase;

    private GCCaseDataList gcCaseDataList;
    private ScrollPane spCaseData;

    public GCCaseTab(Controller controller, TabPane tabPane, Case aCase) {

        this.controller = controller;
        this.tabPane = tabPane;
        this.aCase = aCase;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gc_maintab.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tabPane.getSelectionModel().select(this);
    }

    @FXML
    private void initialize() {

        //tab text
        this.setText(aCase.getPatient().getName());

        //patient overview
        tpPatientOverview.setContent(new GCPatientOverview(aCase.getPatient()));

        //case list
        //TODO get all case-data from backend
        ObservableList<CaseData> caseDataList = FXCollections.observableArrayList();
        gcCaseDataList = new GCCaseDataList(caseDataList);
        gcCaseDataList.mock();

        spCaseData = new ScrollPane(gcCaseDataList);
        spCaseData.setFitToWidth(true);
        vbInput.getChildren().add(spCaseData);

        //TODO define style
        spCaseData.getStylesheets().add(getClass().getResource("chat.css").toExternalForm());

        gcCaseDataList.getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(Change<? extends Node> c) {
                scrollToBottom();
            }
        });
    }

    public void scrollToBottom() {
        //TODO bugfix
        spCaseData.setVvalue(1);
    }

    @FXML
    private void newFreetext() {
        gcCaseDataList.add(new GCTextMessageEdit(controller, gcCaseDataList));
    }

    @FXML
    private void newDiagnosis() {
        controller.showErrorMessage("ERROR - this button is not implemented yet!");
    }
}
