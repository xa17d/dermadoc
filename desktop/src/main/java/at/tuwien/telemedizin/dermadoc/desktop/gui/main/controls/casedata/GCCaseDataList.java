package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata;

import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.edit.AGCCaseDataEdit;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.view.AGCCaseDataView;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.handler.CaseDataEventHandler;
import at.tuwien.telemedizin.dermadoc.entities.*;
import at.tuwien.telemedizin.dermadoc.entities.casedata.*;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
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

    public void expand(boolean expand) {

        for(Node n : this.getChildren()) {
            if(n instanceof AGCCaseDataView) {
                AGCCaseDataView cdv = (AGCCaseDataView) n;
                cdv.expand(expand);
            }
        }
    }
}
