package at.tuwien.telemedizin.dermadoc.desktop.service.mock;

import at.tuwien.telemedizin.dermadoc.desktop.service.ILoginService;
import at.tuwien.telemedizin.dermadoc.desktop.service.dto.AuthenticationListener;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import at.tuwien.telemedizin.dermadoc.service.rest.listener.RestListener;

/**
 * Created by Lucas on 25.11.2015.
 */
@Deprecated
public class LoginServiceMock implements ILoginService {

    @Override
    public void login(AuthenticationListener listener, AuthenticationData loginData) {

    }

    @Override
    public void logout(AuthenticationListener listener, AuthenticationToken token) {

    }

    @Override
    public void getPhysician(AuthenticationToken token, RestListener<Physician> listener) {

    }
}
