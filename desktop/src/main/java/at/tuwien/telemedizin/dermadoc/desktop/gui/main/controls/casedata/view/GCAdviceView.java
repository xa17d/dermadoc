package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.view;

import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.edit.GCAdviceEdit;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.edit.GCTextMessageEdit;
import at.tuwien.telemedizin.dermadoc.entities.Medication;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.casedata.Advice;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseInfo;
import at.tuwien.telemedizin.dermadoc.entities.casedata.Diagnosis;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * view of an advice
 */
public class GCAdviceView extends AGCCaseDataView {

    private static final int ROW_HEIGHT = 24;

    @FXML private VBox vbCaseData;
    @FXML private Label lbMessage;
    @FXML private TitledPane tpMedication;
    @FXML private ListView<Medication> lvMedication;

    private Advice data;

    public GCAdviceView(CaseData caseData) {

        if(caseData instanceof Advice) {
            this.data = (Advice) caseData;
        }
        else {
            throw new IllegalArgumentException("case data must be an advice!");
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gc_advice_view.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GCAdviceView(GCAdviceEdit gcAdvice) {

        this.data = gcAdvice.getAdvice();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gc_advice_view.fxml"));
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

        this.initStyle(vbCaseData);

        lbMessage.setText(data.getMessage());
        lvMedication.setItems(FXCollections.observableArrayList(data.getMedications()));
        lvMedication.setPrefHeight((data.getMedications().size()+1) * ROW_HEIGHT + 2);
    }

    @Override
    public boolean byPhysician() {
        return true;
    }

    @Override
    public boolean isObsolete() {

        return data.isObsolete();
    }

    @Override
    public void expand(boolean expand) {
        tpMedication.setExpanded(expand);
    }

    @Override
    public CaseData getCaseData() {
        return data;
    }
}