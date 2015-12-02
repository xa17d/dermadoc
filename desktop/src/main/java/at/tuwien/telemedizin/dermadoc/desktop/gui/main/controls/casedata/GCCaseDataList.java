package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata;

import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.edit.AGCCaseDataEdit;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.handler.CaseDataEventHandler;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

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
}
