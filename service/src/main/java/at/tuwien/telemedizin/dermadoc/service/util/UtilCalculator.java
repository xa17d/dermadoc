package at.tuwien.telemedizin.dermadoc.service.util;

import java.time.OffsetDateTime;
import java.util.Calendar;

/**
 * Created by Lucas on 17.12.2015.
 */
public class UtilCalculator {

    public static int age(Calendar then) {

        if(then == null) {
            return 0;
        }

        Calendar now = Calendar.getInstance();

        int age = now.get(Calendar.YEAR) - then.get(Calendar.YEAR);
        if (now.get(Calendar.MONTH) < then.get(Calendar.MONTH)) {
            age--;
        } else if (now.get(Calendar.MONTH) == then.get(Calendar.MONTH) && now.get(Calendar.DAY_OF_MONTH) < then.get(Calendar.DAY_OF_MONTH)) {
            age--;
        }

        return age;
    }
}
