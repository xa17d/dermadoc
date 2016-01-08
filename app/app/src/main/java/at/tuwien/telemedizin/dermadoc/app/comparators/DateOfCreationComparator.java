package at.tuwien.telemedizin.dermadoc.app.comparators;

import java.util.Calendar;
import java.util.Comparator;

/**
 * Created by FAUser on 18.11.2015.
 */
public class DateOfCreationComparator implements Comparator<Calendar> {

    boolean newestFirst = false;
    public DateOfCreationComparator(boolean newestFirst) {
        this.newestFirst = newestFirst;
    }


    @Override
    public int compare(Calendar lhs, Calendar rhs) {
        int compResult;
        // take null values into account
        if (lhs == null) {
            if (rhs == null) {
                compResult = 0;
            } else {
                compResult = 1;
            }
        } else {
            if (rhs == null) {
                compResult = -1;
            } else {
                compResult = lhs.compareTo(rhs);
                // if newest first switch
                if (newestFirst) {
                    compResult = compResult * (-1);
                }

            }
        }
        return compResult;
    }

}
