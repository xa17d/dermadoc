package at.tuwien.telemedizin.dermadoc.desktop.gui.controls.casedata;

import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.casedata.edit.AGCCaseDataEdit;
import at.tuwien.telemedizin.dermadoc.desktop.service.CaseDataEventHandler;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.entities.casedata.TextMessage;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import java.util.Calendar;

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
        this.caseDataList.addListener(new ListChangeListener<CaseData>() {
            @Override
            public void onChanged(Change<? extends CaseData> c) {

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

    //TODO remove
    public void mock() {

        //MOCK
        TextMessage message1 = new TextMessage(-1l, Calendar.getInstance(), new Patient(), "This is a text-message from the patient! ");
        TextMessage message2 = new TextMessage(-1l, Calendar.getInstance(), new Physician(), "This is a text-message from the physician! ");
        TextMessage message3 = new TextMessage(-1l, Calendar.getInstance(), new Patient(), "This is a text-message from the patient! ");

        caseDataList.add(message1);
        caseDataList.add(message2);
        caseDataList.add(message3);
    }
}
