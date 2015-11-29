package at.tuwien.telemedizin.dermadoc.desktop.gui.main;

import at.tuwien.telemedizin.dermadoc.desktop.gui.DesktopApplication;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * launches main application
 */
public class MainStage extends Stage {

    private static AuthenticationToken token;
    private static Physician physician;

    public MainStage() {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
            Parent root = fxmlLoader.load();

            this.setTitle("DermaDoc");
            this.getIcons().add(new Image(DesktopApplication.class.getResourceAsStream("dermadoc_icon_c_96.png")));

            this.setMinWidth(720);
            this.setMinHeight(540);

            Scene scene = new Scene(root, 1400, 700);
            //scene.getStylesheets().add(getClass().getResource("dermadoc.css").toExternalForm());
            this.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
