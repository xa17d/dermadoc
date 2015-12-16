package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls;

import at.tuwien.telemedizin.dermadoc.desktop.util.UtilBodyLocalization;
import at.tuwien.telemedizin.dermadoc.desktop.util.UtilPainIntensity;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseInfo;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucas on 14.11.2015.
 */
public class GCCaseInfo extends GridPane {

    List<CaseInfo> caseInfos;

    public GCCaseInfo() {

        caseInfos = new ArrayList<>();

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


    private void update(CaseInfo caseInfo) {

        //TEXT
        //TODO

        //LOCALIZATION
        ImageView main = new ImageView(utilBody.getMain());
        main.setPreserveRatio(true);
        main.setFitWidth(200);
        ImageView mask = new ImageView(utilBody.getMask(caseInfo.getLocalization()));
        mask.setPreserveRatio(true);
        mask.setFitWidth(200);

        mask.setBlendMode(BlendMode.ADD);

        Group blend = new Group(
                main,
                mask
        );

        apBodyLocalization.getChildren().add(blend);

        //PAIN
        ivPain.setImage(utilPain.getImage(caseInfo.getPain()));
    }

    public void addCaseInfo(CaseInfo caseInfo) {

        caseInfos.add(caseInfo);
        for(CaseInfo ci : caseInfos) {
            if (!ci.isObsolete()) {
                update(ci);
            }
        }


    }
}
