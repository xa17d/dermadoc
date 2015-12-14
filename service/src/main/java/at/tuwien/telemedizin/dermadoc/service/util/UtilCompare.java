package at.tuwien.telemedizin.dermadoc.service.util;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Patient;

/**
 * Created by Lucas on 26.11.2015.
 */
public class UtilCompare {

    private UtilCompare() {   }

    public static boolean contains(String s, Case c) {

        s = s.toLowerCase();
        return c.getName().toLowerCase().contains(s)
                || c.getPatient().getName().toLowerCase().contains(s)
                || UtilFormat.formatDate(c.getPatient().getBirthTime()).toLowerCase().contains(s)
                || c.getPatient().getSvnr().toLowerCase().contains(s);
    }
}
