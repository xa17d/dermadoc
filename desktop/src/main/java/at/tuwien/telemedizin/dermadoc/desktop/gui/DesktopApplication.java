package at.tuwien.telemedizin.dermadoc.desktop.gui;

import at.tuwien.telemedizin.dermadoc.desktop.gui.login.LoginStage;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.MainStage;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * main class
 * starts desktop application
 */
public class DesktopApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        new LoginStage().show();
    }
}
