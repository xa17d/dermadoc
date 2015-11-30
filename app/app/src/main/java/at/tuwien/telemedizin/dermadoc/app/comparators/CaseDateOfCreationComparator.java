package at.tuwien.telemedizin.dermadoc.app.comparators;

import java.util.Calendar;
import java.util.Comparator;

import at.tuwien.telemedizin.dermadoc.entities.CaseStatus;

/**
 * Created by FAUser on 18.11.2015.
 */
public class CaseDateOfCreationComparator implements Comparator<Calendar> {


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
            }
        }
        return compResult;
    }

}
