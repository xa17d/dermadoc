package at.tuwien.telemedizin.dermadoc.server.security;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.CaseStatus;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.User;

/**
 * Created by daniel on 27.11.2015.
 */
public class Access {
    public static boolean canAccess(User user, Case forCase) {
        if (forCase.getPatient().getId() == user.getId()) { return true; }
        if (forCase.getPhysician() == null && forCase.getStatus() == CaseStatus.LookingForPhysician && user instanceof Physician) { return true; }
        if (forCase.getPhysician() != null && forCase.getPhysician().getId() == user.getId()) { return true; }
        return false;
    }
}
