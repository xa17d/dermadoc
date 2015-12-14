package at.tuwien.telemedizin.dermadoc.desktop.service.dto;

import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;

/**
 * Created by Lucas on 29.11.2015.
 */
public class GlobalVariables {

    private GlobalVariables() {  }

    private static AuthenticationToken token;
    public static void setToken(AuthenticationToken token) { GlobalVariables.token = token; }
    public static AuthenticationToken getToken() { return token; }

    private static Physician physician;
    public static void setPhysician(Physician physician) { GlobalVariables.physician = physician; }
    public static Physician getPhysician() { return physician; }
}
