package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.view;

import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.edit.GCAdviceEdit;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.edit.GCDiagnosisEdit;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.edit.GCTextMessageEdit;
import at.tuwien.telemedizin.dermadoc.entities.Icd10Diagnosis;
import at.tuwien.telemedizin.dermadoc.entities.Medication;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.casedata.Advice;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
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
 * view of a diagnosis
 */
public class GCDiagnosisView extends AGCCaseDataView {

    private static final int ROW_HEIGHT = 24;

    @FXML private VBox vbCaseData;
    @FXML private Label lbMessage;
    @FXML private TitledPane tpDiagnosis;
    @FXML private ListView<Icd10Diagnosis> lvDiagnosis;

    private Diagnosis data;

    public GCDiagnosisView(CaseData caseData) {

        if(caseData instanceof Diagnosis) {
            this.data = (Diagnosis) caseData;
        }
        else {
            throw new IllegalArgumentException("case data must be a diagnosis!");
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gc_diagnosis_view.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GCDiagnosisView(GCDiagnosisEdit gcDiagnosis) {

        this.data = gcDiagnosis.getDiagnosis();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gc_diagnosis_view.fxml"));
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
        lvDiagnosis.setItems(FXCollections.observableArrayList(data.getDiagnosisList()));
        lvDiagnosis.setPrefHeight((data.getDiagnosisList().size()+1) * ROW_HEIGHT + 4);
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
        tpDiagnosis.setExpanded(expand);
    }

    @Override
    public CaseData getCaseData() {
        return data;
    }
}