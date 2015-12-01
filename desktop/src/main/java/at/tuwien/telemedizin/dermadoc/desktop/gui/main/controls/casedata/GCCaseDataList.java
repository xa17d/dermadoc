package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata;

import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.edit.AGCCaseDataEdit;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.handler.CaseDataEventHandler;
import at.tuwien.telemedizin.dermadoc.entities.*;
import at.tuwien.telemedizin.dermadoc.entities.casedata.*;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Lucas on 20.11.2015.
 */
public class GCCaseDataList extends VBox {

    private ObservableList<CaseData> caseDataList;
    private GCCaseDataFactory gcFactory;

    public GCCaseDataList(ObservableList<CaseData> caseDataList) {

        gcFactory = new GCCaseDataFactory();
        GCCaseDataList cgList = this;

        this.caseDataList = caseDataList;

        for(CaseData cd : caseDataList) {
            this.getChildren().add(gcFactory.getGC(cd));
        }

        //MOCK
        List<AnamnesisQuestion> questions = new ArrayList<>();
        AnamnesisQuestionText q1 = new AnamnesisQuestionText();
        q1.setQuestion("question 1");
        q1.setAnswer("text answer 1");
        AnamnesisQuestionText q2 = new AnamnesisQuestionText();
        q2.setQuestion("question 2");
        q2.setAnswer("text answer 2");
        AnamnesisQuestionText q3 = new AnamnesisQuestionText();
        q3.setQuestion("question 3");
        q3.setAnswer("text answer 3");
        AnamnesisQuestionText q4 = new AnamnesisQuestionText();
        q4.setQuestion("question 4");
        q4.setAnswer("text answer 4");
        AnamnesisQuestionBool q5 = new AnamnesisQuestionBool();
        q5.setQuestion("question 5");
        q5.setAnswer(true);
        AnamnesisQuestionBool q6 = new AnamnesisQuestionBool();
        q6.setQuestion("question 6");
        q6.setAnswer(false);
        AnamnesisQuestionBool q7 = new AnamnesisQuestionBool();
        q7.setQuestion("question 7");
        q7.setAnswer(true);

        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        questions.add(q4);
        questions.add(q5);
        questions.add(q6);
        questions.add(q7);

        Anamnesis anamnesis = new Anamnesis(-2l, Calendar.getInstance(), new Patient(), "this is my anamnesis", questions);
        this.getChildren().add(gcFactory.getGC(anamnesis));

        List<Medication> medis = new ArrayList<>();
        medis.add(new Medication("Medi 1"));
        Medication med2 = new Medication("Medi 2");
        med2.setDosis("20mg");
        medis.add(med2);
        medis.add(new Medication("Medi 3"));

        medis.add(new Medication("Medi 1"));
        Medication med5 = new Medication("Medi 2");
        med5.setDosis("20mg");
        medis.add(med5);
        medis.add(new Medication("Medi 3"));
        Advice advice = new Advice(-1l, Calendar.getInstance(), new Physician(), "please take these medications", medis);
        this.getChildren().add(gcFactory.getGC(advice));

        List<Icd10Diagnosis> diagnosisList = new ArrayList<>();
        diagnosisList.add(new Icd10Diagnosis("I1.0", "Diagnosis 1"));
        diagnosisList.add(new Icd10Diagnosis("R2.0", "Diagnosis 2"));
        diagnosisList.add(new Icd10Diagnosis("F2.12", "Diagnosis 3"));
        diagnosisList.add(new Icd10Diagnosis("I21.2", "Diagnosis 4"));

        Diagnosis diagnosis = new Diagnosis(-3l, Calendar.getInstance(), new Physician(), "i think you are very sick", diagnosisList);
        this.getChildren().add(gcFactory.getGC(diagnosis));
        //----

        this.caseDataList.addListener(new ListChangeListener<CaseData>() {
            @Override
            public void onChanged(Change<? extends CaseData> c) {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        while(c.next()) {
                            for (CaseData cd : c.getAddedSubList()) {
                                AGCCaseData gcCaseData = gcFactory.getGC(cd);
                                cgList.getChildren().add(gcCaseData);
                            }

                            for (CaseData cd : c.getRemoved()) {
                                cgList.getChildren().remove(cd);
                            }
                        }
                    }
                });
            }
        });

        this.setFillWidth(true);
    }

    public void add(AGCCaseDataEdit editComponent) {

        GCCaseDataList gcList = this;
        editComponent.setSaveEventHandler(new CaseDataEventHandler() {
            @Override
            public void onEvent(CaseData caseData) {
                gcList.getChildren().remove(editComponent);
                caseDataList.add(caseData);
            }
        });
        this.getChildren().add(editComponent);
    }
}
