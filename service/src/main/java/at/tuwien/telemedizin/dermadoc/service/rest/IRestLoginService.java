package at.tuwien.telemedizin.dermadoc.service.rest;

import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import at.tuwien.telemedizin.dermadoc.service.rest.listener.RestListener;

/**
 * Created by Lucas on 26.11.2015.
 */
public interface IRestLoginService {

    void postLogin(RestListener<AuthenticationToken> listener, AuthenticationData data);

    void postLogout(RestListener<Void> listener, AuthenticationToken token);
}
