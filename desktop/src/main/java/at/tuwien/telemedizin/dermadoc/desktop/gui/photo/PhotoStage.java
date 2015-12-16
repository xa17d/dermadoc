package at.tuwien.telemedizin.dermadoc.desktop.gui.photo;

import at.tuwien.telemedizin.dermadoc.desktop.gui.DesktopApplication;
import at.tuwien.telemedizin.dermadoc.entities.casedata.PhotoMessage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * launches photo edit scene
 * called in {@link at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.view.GCPhotoMessageView} (line 71)
 */
public class PhotoStage extends Stage {

    public PhotoStage(PhotoMessage photoMessage) {

        PhotoController photoController = new PhotoController(photoMessage);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("photo.fxml"));
            fxmlLoader.setController(photoController);
            Parent root = fxmlLoader.load();

            this.setTitle("Photo " + photoMessage.getId());
            this.getIcons().add(new Image(DesktopApplication.class.getResourceAsStream("dermadoc_icon_c_96.png")));

            this.setMinWidth(400);
            this.setMinHeight(400);

            Scene scene = new Scene(root, 700, 700);
            scene.getStylesheets().add(DesktopApplication.class.getResource("style.css").toExternalForm());
            this.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
