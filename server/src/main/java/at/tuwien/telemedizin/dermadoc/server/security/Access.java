package at.tuwien.telemedizin.dermadoc.server.security;

import at.tuwien.telemedizin.dermadoc.entities.*;

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

    public static boolean canAccess(User user, Notification notification) {
        return (notification.getUserId() == user.getId());
    }
}
