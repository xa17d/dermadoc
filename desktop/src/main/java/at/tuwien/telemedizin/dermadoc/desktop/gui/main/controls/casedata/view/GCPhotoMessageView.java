package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.view;

import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.edit.GCTextMessageEdit;
import at.tuwien.telemedizin.dermadoc.desktop.gui.photo.PhotoStage;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.casedata.*;
import at.tuwien.telemedizin.dermadoc.service.exception.DermadocConversionException;
import at.tuwien.telemedizin.dermadoc.service.util.UtilImageConverter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * view of a diagnosis
 */
public class GCPhotoMessageView extends AGCCaseDataView {

    @FXML private VBox vbCaseData;
    @FXML private TitledPane tpPhotos;
    @FXML private ImageView ivPhoto;

    private PhotoMessage data;

    public GCPhotoMessageView(CaseData caseData) {

        if(caseData instanceof PhotoMessage) {
            this.data = (PhotoMessage) caseData;
        }
        else {
            throw new IllegalArgumentException("case data must be a photo message!");
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gc_photomessage_view.fxml"));
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

        try {
            ivPhoto.setImage(UtilImageConverter.byteToImage(data.getPhotoData(), data.getMime()));
            ivPhoto.setPreserveRatio(true);
            ivPhoto.fitWidthProperty().bind(tpPhotos.widthProperty());
        } catch (DermadocConversionException e) {
            //TODO
            e.printStackTrace();
        }

        expand(false);
    }

    @FXML
    private void openPhoto() {

        PhotoStage stage = new PhotoStage(data);
        stage.show();
    }

    @Override
    public boolean byPhysician() {
        return false;
    }

    @Override
    public boolean isObsolete() {
        return false;
    }

    @Override
    public void expand(boolean expand) {
        tpPhotos.setExpanded(expand);
    }

    @Override
    public CaseData getCaseData() {
        return data;
    }
}