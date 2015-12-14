package at.tuwien.telemedizin.dermadoc.desktop.service;

import at.tuwien.telemedizin.dermadoc.desktop.service.dto.AuthenticationListener;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import at.tuwien.telemedizin.dermadoc.service.rest.listener.RestListener;

/**
 * interface to access service layer
 * to login and logout the physician
 */
public interface ILoginService {

    /**
     * login a physician via email and password and get
     * the token for identification
     * @param listener listner
     * @param loginData data
     */
    void login(AuthenticationListener listener, AuthenticationData loginData);

    /**
     * logout physician and destroy token
     * @param listener listner
     * @param token token
     */
    void logout(AuthenticationListener listener, AuthenticationToken token);

    /**
     * load the logged in physician from backend
     * @param listener listner
     */
    void getPhysician(AuthenticationToken token, RestListener<Physician> listener);
}
