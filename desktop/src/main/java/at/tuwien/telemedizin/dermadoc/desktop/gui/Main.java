package at.tuwien.telemedizin.dermadoc.desktop.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PopupControl;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * main
 * launches desktop application
 */
public class Main extends Application {

    private Stage mainStage;

    @Override
    public void start(Stage stage) {

        this.mainStage = stage;

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
            Parent root = fxmlLoader.load();
            Controller controller = fxmlLoader.getController();

            mainStage.setTitle("DermaDoc");
            mainStage.getIcons().add(new Image(Main.class.getResourceAsStream("dermadoc_icon_c_96.png")));

            mainStage.setMinWidth(720);
            mainStage.setMinHeight(540);

            Scene scene = new Scene(root, 1400, 700);
            //scene.getStylesheets().add(getClass().getResource("dermadoc.css").toExternalForm());
            mainStage.setScene(scene);

            mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    mainStage.close();
                    controller.login(mainStage);
                }
            });
            controller.login(mainStage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
