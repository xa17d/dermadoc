package at.tuwien.telemedizin.dermadoc.desktop.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * main
 * launches desktop application
 */
public class Main extends Application {

    private Parent root;

    @Override
    public void start(Stage primaryStage) {

        try {
            root = FXMLLoader.load(getClass().getResource("main.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        primaryStage.setTitle("DermaDoc");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("dermadoc_icon_c_96.png")));

        primaryStage.setMinWidth(720);
        primaryStage.setMinHeight(540);

        Scene scene = new Scene(root, 1200, 800);
        //scene.getStylesheets().add(getClass().getResource("dermadoc.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
