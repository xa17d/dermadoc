package at.tuwien.telemedizin.dermadoc.app.helper;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by FAUser on 20.11.2015.
 */
public class FormatHelper {

    public static String calendarToDateFormatString(Calendar calendar, Context context) {
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
        if (calendar != null) {
            String date = dateFormat.format(calendar.getTime());
            return date;
        } else {
            return null;
        }
    }

    public static String calendarToTimeFormatString(Calendar calendar, Context context) {
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getTimeFormat(context);
        if (calendar != null) {
            String date = dateFormat.format(calendar.getTime());
            return date;
        } else {
            return null;
        }
    }

    public static String calendarToDateFormatString(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if (calendar != null) {
            String date = dateFormat.format(calendar.getTime());
            return date;
        } else {
            return null;
        }
    }
}
