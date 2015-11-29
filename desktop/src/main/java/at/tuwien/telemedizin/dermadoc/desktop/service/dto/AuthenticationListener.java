package at.tuwien.telemedizin.dermadoc.desktop.service.dto;

import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import at.tuwien.telemedizin.dermadoc.entities.rest.Error;

/**
 * Created by Lucas on 29.11.2015.
 */
public interface AuthenticationListener {

    void onAuthenticationComplete(AuthenticationToken token);

    void onAuthenticationError(Error error);
}
