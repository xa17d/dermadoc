package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls;

import at.tuwien.telemedizin.dermadoc.desktop.gui.main.Controller;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.GCCaseDataList;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.edit.AGCCaseDataEdit;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.edit.GCAdviceEdit;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.edit.GCTextMessageEdit;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.function.Predicate;

/**
 * Created by Lucas on 14.11.2015.
 */
public class GCCaseTab extends Tab {

    @FXML private VBox vbInput;
    @FXML private GridPane gpBottom;
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gc_casetab.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //set new tab as selected tab
        tabPane.getSelectionModel().select(this);
    }

    @FXML
    private void initialize() {

        //tab text
        this.setText(aCase.getPatient().getName() + " - " + aCase.getName());

        //patient overview
        GCCaseInfo gcCaseInfo = new GCCaseInfo();
        tpPatientOverview.setContent(gcCaseInfo);
        tpPatientOverview.setExpanded(false);

        //load case data list
        gcCaseDataList = new GCCaseDataList(controller.getCaseData(aCase), gcCaseInfo);

        //show case data list in scroll pane
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

    public Case getCase() { return aCase; }

    @FXML
    private void expand() {
        gcCaseDataList.expand(true);
        //TODO
        //scrollToBottom();
    }

    @FXML
    private void collapse() {
        gcCaseDataList.expand(false);
        //TODO
        //scrollToBottom();
    }

    @FXML
    private void newFreetext() {

        checkForOpenEditsAndRemove();
        gcCaseDataList.addEdit(new GCTextMessageEdit(controller, aCase));
    }

    @FXML
    private void newDiagnosis() {
        controller.showErrorMessage("ERROR - this button is not implemented yet!");
    }

    @FXML
    private void newAdvice() {

        checkForOpenEditsAndRemove();
        gcCaseDataList.addEdit(new GCAdviceEdit(controller, aCase));
    }

    private void checkForOpenEditsAndRemove() {

        //TODO maybe show a dialog to confirm the deleting of the actual edit?
        FilteredList<Node> filteredList = gcCaseDataList.getChildren().filtered(new Predicate<Node>() {
            @Override
            public boolean test(Node node) {
                return node instanceof AGCCaseDataEdit;
            }
        });
        gcCaseDataList.getChildren().removeAll(filteredList);
    }

    private void scrollToBottom() {

        //TODO fix this dirty hack!
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        spCaseData.setVvalue(spCaseData.getVmax());
                    }
                });
            }
        });
        t.start();
    }
}
