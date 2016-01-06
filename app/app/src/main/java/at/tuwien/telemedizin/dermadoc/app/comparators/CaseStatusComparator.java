package at.tuwien.telemedizin.dermadoc.app.comparators;

import java.util.Comparator;

import at.tuwien.telemedizin.dermadoc.app.general_entities.CaseStatus;

/**
 * Created by FAUser on 18.11.2015.
 */
public class CaseStatusComparator implements Comparator<CaseStatus> {


    @Override
    public int compare(CaseStatus lhs, CaseStatus rhs) {
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

        // ATTENTION: "-" inverses the result -> therefore "active" is first, "searching for..." last
        return -compResult;
    }

}
