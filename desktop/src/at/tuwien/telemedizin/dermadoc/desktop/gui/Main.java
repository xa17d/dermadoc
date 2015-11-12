package at.tuwien.telemedizin.dermadoc.desktop.gui;

import com.aquafx_project.AquaFx;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("draft1.fxml"));
        primaryStage.setTitle("DermaDoc");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("icon.jpg")));
        primaryStage.setScene(new Scene(root, 1200, 800));
        AquaFx.style();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
