package at.tuwien.telemedizin.dermadoc.desktop.gui.login;

import at.tuwien.telemedizin.dermadoc.desktop.gui.DesktopApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * application to login physician
 * with username and password
 */
public class LoginStage extends Stage {

    public LoginStage() {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = fxmlLoader.load();

            this.setTitle("DermaDoc - Login");
            this.getIcons().add(new Image(DesktopApplication.class.getResourceAsStream("dermadoc_icon_c_96.png")));

            this.setMinWidth(500);
            this.setMinHeight(300);
            this.setMaxWidth(500);
            this.setMaxHeight(300);

            Scene scene = new Scene(root, 500, 300);
            scene.getStylesheets().add(DesktopApplication.class.getResource("style.css").toExternalForm());
            this.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
