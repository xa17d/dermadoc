package at.tuwien.telemedizin.dermadoc.app.comparators;

import java.util.Comparator;

import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.CaseDataParc;

/**
 * Created by FAUser on 18.11.2015.
 */
public class CaseDataDateOfCreationComparator implements Comparator<CaseDataParc> {

    private DateOfCreationComparator dateOfCreationComparator = new DateOfCreationComparator(false);

    @Override
    public int compare(CaseDataParc lhs, CaseDataParc rhs) {
        return compareDateOfCreation(lhs, rhs);
    }


    private int compareDateOfCreation(CaseDataParc lhs, CaseDataParc rhs) {
        return dateOfCreationComparator.compare(lhs.getCreated(), rhs.getCreated());
    }



}
