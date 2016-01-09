package at.tuwien.telemedizin.dermadoc.desktop.service;

import at.tuwien.telemedizin.dermadoc.desktop.gui.login.LoginController;
import at.tuwien.telemedizin.dermadoc.desktop.gui.login.LoginStage;
import at.tuwien.telemedizin.dermadoc.desktop.service.dto.AuthenticationListener;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import at.tuwien.telemedizin.dermadoc.entities.rest.Error;
import at.tuwien.telemedizin.dermadoc.service.rest.IRestLoginService;
import at.tuwien.telemedizin.dermadoc.service.rest.RestLoginService;
import at.tuwien.telemedizin.dermadoc.service.rest.listener.RestListener;

/**
 * Created by Lucas on 29.11.2015.
 */
public class LoginService implements ILoginService {

    private IRestLoginService rest = new RestLoginService();

    @Override
    public void login(AuthenticationListener listener, AuthenticationData loginData) {

        rest.postLogin(new RestListener<AuthenticationToken>() {
            @Override
            public void onRequestComplete(AuthenticationToken requestResult) {
                listener.onAuthenticationComplete(requestResult);
            }

            @Override
            public void onError(Error error) {
                listener.onAuthenticationError(error);
            }
        }, loginData);
    }

    @Override
    public void logout(AuthenticationListener listener, AuthenticationToken token) {
        //TODO
    }

    @Override
    public void getPhysician(AuthenticationToken token, RestListener<Physician> listener) {
        rest.getUser(listener, token);
    }
}
