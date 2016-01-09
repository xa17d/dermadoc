package at.tuwien.telemedizin.dermadoc.desktop.gui.login;

import at.tuwien.telemedizin.dermadoc.desktop.gui.DesktopApplication;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.MainStage;
import at.tuwien.telemedizin.dermadoc.desktop.service.ILoginService;
import at.tuwien.telemedizin.dermadoc.desktop.service.LoginService;
import at.tuwien.telemedizin.dermadoc.desktop.service.dto.AuthenticationListener;
import at.tuwien.telemedizin.dermadoc.desktop.service.dto.GlobalVariables;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import at.tuwien.telemedizin.dermadoc.entities.rest.Error;
import at.tuwien.telemedizin.dermadoc.service.rest.listener.RestListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Created by Lucas on 29.11.2015.
 */
public class LoginController {

    @FXML private TextField tfMail;
    @FXML private TextField tfPassword;

    private ILoginService loginService = new LoginService();
    private Stage loginStage;

    @FXML
    private void login() {

        AuthenticationData data = new AuthenticationData(tfMail.getText(), tfPassword.getText());

        loginStage = (Stage) tfMail.getScene().getWindow();
        loginService.login(new LoginListener(), data);
    }

    private class LoginListener implements AuthenticationListener {

        @Override
        public void onAuthenticationComplete(AuthenticationToken token) {
            loginService.getPhysician(token, new RestListener<Physician>() {
                @Override
                public void onRequestComplete(Physician physician) {

                    GlobalVariables.setToken(token);
                    GlobalVariables.setPhysician(physician);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            loginStage.close();
                            new MainStage().show();
                        }
                    });
                }

                @Override
                public void onError(Error error) {
                    //TODO
                }
            });
        }

        @Override
        public void onAuthenticationError(Error error) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("OOOPS");
                    alert.setHeaderText("Login Credentials Are Incorrect");
                    alert.setContentText("please check your email address and password and try again...");
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().add(DesktopApplication.class.getResource("main.css").toExternalForm());
                    ((Stage) dialogPane.getScene().getWindow()).getIcons().add(new Image(DesktopApplication.class.getResourceAsStream("dermadoc_icon_c_96.png")));
                    alert.showAndWait();
                }
            });
        }
    }
}
