package at.tuwien.telemedizin.dermadoc.desktop.gui.login;

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
import javafx.scene.control.TextField;
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
            //TODO
        }
    }
}
