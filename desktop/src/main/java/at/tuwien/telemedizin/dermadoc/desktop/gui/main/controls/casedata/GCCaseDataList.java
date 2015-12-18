package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata;

import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.GCCaseInfo;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.edit.AGCCaseDataEdit;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.view.AGCCaseDataView;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.handler.CaseDataEventHandler;
import at.tuwien.telemedizin.dermadoc.entities.*;
import at.tuwien.telemedizin.dermadoc.entities.casedata.*;
import at.tuwien.telemedizin.dermadoc.service.exception.DermadocConversionException;
import at.tuwien.telemedizin.dermadoc.service.util.UtilImageConverter;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Lucas on 20.11.2015.
 */
public class GCCaseDataList extends VBox {

    private ObservableList<CaseData> caseDataList;
    private GCCaseInfo gcCaseInfo;

    private GCCaseDataFactory gcFactory;

    public GCCaseDataList(ObservableList<CaseData> caseDataList, GCCaseInfo gcCaseInfo) {

        this.caseDataList = caseDataList;
        this.gcCaseInfo = gcCaseInfo;

        gcFactory = new GCCaseDataFactory();

        for(CaseData cd : caseDataList) {
            addView(cd);
        }

        this.caseDataList.addListener(new ListChangeListener<CaseData>() {
            @Override
            public void onChanged(Change<? extends CaseData> c) {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        while(c.next()) {
                            for (CaseData cd : c.getAddedSubList()) {
                                addView(cd);
                            }

                            for (CaseData cd : c.getRemoved()) {
                                removeView(cd);
                            }
                        }
                    }
                });
            }
        });

        //------------------
        //MOCK
        try {
            URL url = getClass().getResource("testimage.jpg");
            byte[] imageByte = new byte[0];
            imageByte = UtilImageConverter.imageToByte(new File(url.getPath()), "jpg");
            PhotoMessage pm = new PhotoMessage(-1l, Calendar.getInstance(), new Patient(), "jpg", imageByte );
            this.getChildren().add(gcFactory.getGC(pm));
        } catch (DermadocConversionException e) {
            e.printStackTrace();
        }

        Patient p = new Patient();
        p.setName("John Doe");
        p.setGender(Gender.Male);
        p.setSvnr("3023 15.02.1991");
        Calendar birthTime = Calendar.getInstance();
        birthTime.set(1991,2,15);
        p.setBirthTime(birthTime);

        List<BodyLocalization> localizations = new ArrayList<>();
        localizations.add(BodyLocalization.ABDOMEN);
        localizations.add(BodyLocalization.HAND_LEFT);
        localizations.add(BodyLocalization.HEAD);
        localizations.add(BodyLocalization.LOWER_BACK);

        CaseInfo ci1 = new CaseInfo(-1l, Calendar.getInstance(), p, localizations, PainIntensity.Severe, 5);
        gcCaseInfo.addCaseInfo(ci1);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        localizations.remove(BodyLocalization.LOWER_BACK);
        CaseInfo ci2 = new CaseInfo(-2l, Calendar.getInstance(), p, localizations, PainIntensity.Moderate, 5);
        gcCaseInfo.addCaseInfo(ci2);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        localizations.remove(BodyLocalization.HEAD);
        CaseInfo ci3 = new CaseInfo(-3l, Calendar.getInstance(), p, localizations, PainIntensity.Mild, 5);
        gcCaseInfo.addCaseInfo(ci3);
        //-----------

        this.setFillWidth(true);
    }

    private void addView(CaseData cd) {

        //special treatment for case info
        if(cd instanceof CaseInfo) {
            gcCaseInfo.addCaseInfo((CaseInfo) cd);
        }
        else {
            this.getChildren().add(gcFactory.getGC(cd));
        }
    }

    private void removeView(CaseData cd) {
        this.getChildren().remove(cd);
    }

    public void addEdit(AGCCaseDataEdit editComponent) {

        GCCaseDataList gcList = this;
        editComponent.setSaveEventHandler(new CaseDataEventHandler() {
            @Override
            public void onEvent(CaseData caseData) {
                gcList.getChildren().remove(editComponent);
                caseDataList.add(caseData);

                //set old case data of same type obsolete
                //TODO dirty
                if(caseData instanceof Advice || caseData instanceof Diagnosis) {
                    for(AGCCaseDataView cdv : getAllCaseDataViews()) {
                        if(caseData.getClass().equals(cdv.getCaseData().getClass())) {
                            cdv.setObsolete();
                        }
                    }
                }
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

    private List<AGCCaseDataView> getAllCaseDataViews() {

        List<AGCCaseDataView> result = new ArrayList<>();
        for(Node n : this.getChildren()) {
            if(n instanceof AGCCaseDataView) {
                result.add((AGCCaseDataView) n);
            }
        }
        return result;
    }
}
