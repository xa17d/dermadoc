package at.tuwien.telemedizin.dermadoc.service.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UtilFormat {

    private UtilFormat() {   }

    private static SimpleDateFormat sdfLong = new SimpleDateFormat("dd.MM.yyyy");

    public static String formatDate(Calendar date) {
        return formatDate(date.getTime());
    }

    public static String formatDate(Date date) {
        return sdfLong.format(date);
    }
}
