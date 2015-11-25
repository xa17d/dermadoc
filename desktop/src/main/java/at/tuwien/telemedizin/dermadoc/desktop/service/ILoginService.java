package at.tuwien.telemedizin.dermadoc.desktop.service;

import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;

/**
 * interface to access service layer
 * and login and logout the physician
 */
public interface ILoginService {

    /**
     * login a physician via email and password and get
     * the token for identification
     * @param loginData contains email and password
     * @return token for identification on backend
     */
    AuthenticationToken login(AuthenticationData loginData);

    /**
     * logout physician and destroy token
     * @param token token
     */
    void logout(AuthenticationToken token);

    Physician getPhysician(AuthenticationToken token);
}
