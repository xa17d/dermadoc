package at.tuwien.telemedizin.dermadoc.desktop.gui;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class Controller {

    @FXML private ImageView imageViewDoctor;

    @FXML
    public void initialize() {

        imageViewDoctor.setImage(new Image(Main.class.getResourceAsStream("drlucas.jpg")));
    }
}
