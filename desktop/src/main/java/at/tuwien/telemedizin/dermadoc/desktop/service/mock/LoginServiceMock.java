package at.tuwien.telemedizin.dermadoc.desktop.service.mock;

import at.tuwien.telemedizin.dermadoc.desktop.service.ILoginService;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;

/**
 * Created by Lucas on 25.11.2015.
 */
public class LoginServiceMock implements ILoginService {


    @Override
    public AuthenticationToken login(AuthenticationData loginData) {
        return new AuthenticationToken();
    }

    @Override
    public void logout(AuthenticationToken token) {
    }

    @Override
    public Physician getPhysician(AuthenticationToken token) {

        Physician physician = new Physician();
        physician.setName("Lucas Dobler");
        physician.setMail("lucas@ldob.eu");

        return physician;
    }
}
