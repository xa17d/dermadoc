package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.view;

import at.tuwien.telemedizin.dermadoc.entities.Medication;
import at.tuwien.telemedizin.dermadoc.entities.casedata.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * view of an anamnesis
 */
public class GCAnamnesisView  extends AGCCaseDataView {

    private static final int ROW_HEIGHT = 24;

    @FXML private VBox vbCaseData;
    @FXML private Label lbMessage;
    @FXML private TitledPane tpAnamnesis;
    @FXML private ListView<AnamnesisQuestion> lvAnamnesis;

    private Anamnesis data;

    public GCAnamnesisView(CaseData caseData) {

        if(caseData instanceof Anamnesis) {
            this.data = (Anamnesis) caseData;
        }
        else {
            throw new IllegalArgumentException("case data must be an anamnesis!");
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gc_anamnesis_view.fxml"));
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
        lvAnamnesis.setItems(FXCollections.observableArrayList(data.getQuestions()));
        lvAnamnesis.setPrefHeight((data.getQuestions().size()+1) * ROW_HEIGHT + 2);
    }

    @Override
    public boolean byPhysician() {
        return false;
    }

    @Override
    public boolean isObsolete() {
        return data.isObsolete();
    }

    @Override
    public void expand(boolean expand) {
        tpAnamnesis.setExpanded(expand);
    }

    @Override
    public CaseData getCaseData() {
        return data;
    }
}