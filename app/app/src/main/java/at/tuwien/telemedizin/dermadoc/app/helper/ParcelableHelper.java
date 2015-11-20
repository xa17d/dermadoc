package at.tuwien.telemedizin.dermadoc.app.helper;

import java.util.Calendar;

/**
 * Created by FAUser on 20.11.2015.
 */
public class ParcelableHelper {

    public static Calendar longToCalendar(long timeInMillis) {
        if (timeInMillis != -1) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(timeInMillis);
            return cal;
        } else {
            return null;
        }
    }
}
