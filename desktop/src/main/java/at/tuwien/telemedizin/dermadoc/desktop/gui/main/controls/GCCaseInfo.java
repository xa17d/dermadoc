package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls;

import at.tuwien.telemedizin.dermadoc.desktop.util.CaseDataComparator;
import at.tuwien.telemedizin.dermadoc.desktop.util.UtilBodyLocalization;
import at.tuwien.telemedizin.dermadoc.desktop.util.UtilPainIntensity;
import at.tuwien.telemedizin.dermadoc.desktop.util.UtilSet;
import at.tuwien.telemedizin.dermadoc.entities.BodyLocalization;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseInfo;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import at.tuwien.telemedizin.dermadoc.service.util.UtilCalculator;
import at.tuwien.telemedizin.dermadoc.service.util.UtilFormat;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.*;

/**
 * Created by Lucas on 14.11.2015.
 */
public class GCCaseInfo extends GridPane {

    private Case aCase;

    private Set<CaseInfo> caseInfos;

    private CaseInfo actualVersion;

    public GCCaseInfo(Case aCase) {

        this.aCase = aCase;

        caseInfos = new TreeSet<>(new CaseDataComparator());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gc_caseinfo_new.fxml"));
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
        //do nothing...
        //initalize on first update
    }

    private UtilBodyLocalization utilBody = new UtilBodyLocalization();
    private UtilPainIntensity utilPain = new UtilPainIntensity();

    @FXML AnchorPane apBodyLocalization;
    @FXML ImageView ivPain;
    @FXML Label lbName;
    @FXML Label lbGender;
    @FXML Label lbSvnr;
    @FXML Label lbAge;
    @FXML Label lbCreated;
    @FXML Label lbCaseName;
    @FXML Label lbCaseDescription;

    private void update(CaseInfo caseInfo) {

        actualVersion = caseInfo;

        //TEXT
        Patient p = (Patient) caseInfo.getAuthor();
        lbName.setText(p.getName());
        lbGender.setText(p.getGender().getAbbreviation());
        lbSvnr.setText(p.getSvnr());
        lbAge.setText(UtilCalculator.age(p.getBirthTime()) + " Y");
        lbCreated.setText(UtilFormat.formatDate(caseInfo.getCreated()));
        lbCaseName.setText(aCase.getName());
        lbCaseDescription.setText(aCase.getDescription());

        //LOCALIZATION

        //all masks + main
        ImageView[] mask = new ImageView[caseInfo.getLocalizations().size()+1];

        mask[0] = new ImageView(utilBody.getMain());
        mask[0].setPreserveRatio(true);
        mask[0].setFitWidth(200);

        int i = 1;
        for(BodyLocalization bl : caseInfo.getLocalizations()) {
            mask[i] = new ImageView(utilBody.getMask(bl));
            mask[i].setPreserveRatio(true);
            mask[i].setFitWidth(200);
            mask[i].setBlendMode(BlendMode.MULTIPLY);
            i++;
        }

        apBodyLocalization.getChildren().add(new Group(mask));

        //PAIN
        ivPain.setImage(utilPain.getImage(caseInfo.getPain()));

        //BUTTONS
        if(utilSet.getPreviousElement(caseInfos, actualVersion) == null) {
            btBack.setVisible(false);
        }
        else {
            btBack.setVisible(true);
        }

        if(utilSet.getNextElement(caseInfos, actualVersion) == null) {
            btForward.setVisible(false);
        }
        else {
            btForward.setVisible(true);
        }
    }

    UtilSet<CaseInfo> utilSet = new UtilSet<>();

    public void addCaseInfo(CaseInfo caseInfo) {
        caseInfos.add(caseInfo);
        update(utilSet.getLastElement(caseInfos));
    }

    @FXML Button btBack;
    @FXML Button btForward;

    @FXML
    private void goBack() {
        update(utilSet.getPreviousElement(caseInfos, actualVersion));
    }

    @FXML
    private void goForward() {
        update(utilSet.getNextElement(caseInfos, actualVersion));
    }
}
