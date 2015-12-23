package at.tuwien.telemedizin.dermadoc.desktop.gui.photo;

import at.tuwien.telemedizin.dermadoc.desktop.gui.lock.LockStage;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.GCCaseTab;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.GCOpenCaseList;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.GCPatientList;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.GCPhysician;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.error.ErrorPane;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.handler.OpenMainTabEventHandler;
import at.tuwien.telemedizin.dermadoc.desktop.service.CaseService;
import at.tuwien.telemedizin.dermadoc.desktop.service.ICaseService;
import at.tuwien.telemedizin.dermadoc.desktop.service.dto.GlobalVariables;
import at.tuwien.telemedizin.dermadoc.desktop.service.dto.PatientCaseMap;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.entities.casedata.PhotoMessage;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import at.tuwien.telemedizin.dermadoc.service.exception.DermadocConversionException;
import at.tuwien.telemedizin.dermadoc.service.exception.DermadocException;
import at.tuwien.telemedizin.dermadoc.service.util.UtilImageConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.controlsfx.control.NotificationPane;

/**
 * photo controller
 * is the link between view and services
 */
public class PhotoController {

    @FXML private BorderPane bpPhoto;
    @FXML private ImageView ivPhoto;

    private PhotoMessage photoMessage;

    public PhotoController(PhotoMessage photoMessage) {

        this.photoMessage = photoMessage;
    }

    @FXML
    public void initialize() {
        //gets called after scene is loaded

        try {
            ivPhoto.setImage(UtilImageConverter.byteToImage(photoMessage.getPhotoData(), photoMessage.getMime()));
            ivPhoto.setPreserveRatio(true);
            ivPhoto.fitWidthProperty().bind(bpPhoto.widthProperty());
            ivPhoto.fitHeightProperty().bind(bpPhoto.heightProperty());

        } catch (DermadocConversionException e) {
            //TODO
            e.printStackTrace();
        }

        //TODO
    }
}