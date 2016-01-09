package at.tuwien.telemedizin.dermadoc.app.comparators;

import java.util.Comparator;

import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.CaseListItem;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.CaseParc;

/**
 * Created by FAUser on 18.11.2015.
 *
 * Comparator for comparing Case-Objects - needed to sort the list
 */
public class CaseListItemComparator implements Comparator<CaseListItem> {


    // multiple Comparators, because multiple ways to sort case-items
    private CaseStatusComparator statusComparator = new CaseStatusComparator();
    private DateOfCreationComparator dateOfCreationComparator = new DateOfCreationComparator(true);
    private CaseListItemNotificationComparator notificationComparator = new CaseListItemNotificationComparator();

    private CaseSortCategory activeCategory = CaseSortCategory.STATUS; // default category


    @Override
    public int compare(CaseListItem lhsCLI, CaseListItem rhsCLI) {

        CaseParc lhs = lhsCLI.getCaseItem();
        CaseParc rhs = rhsCLI.getCaseItem();

        int compResult = 0;

        if (activeCategory == CaseSortCategory.NAME) {
            compResult = compareNames(lhs, rhs);
        } else if (activeCategory == CaseSortCategory.ID) {
            compResult = compareIDs(lhs, rhs);
        } else if (activeCategory == CaseSortCategory.STATUS) {
            compResult = compareStatus(lhs, rhs);
        } else if (activeCategory == CaseSortCategory.DATE_OF_CREATION) {
            compResult = compareDateOfCreation(lhs, rhs);
        } else if (activeCategory == CaseSortCategory.NOTIFICATIONS) {
            compResult = notificationComparator.compare(lhsCLI.getNotifications(), rhsCLI.getNotifications());
        }

        // if equal, take ID as secondary parameter
//        if (compResult == 0) {
//            return compareIDs(lhs, rhs);
//        } else {
//            return compResult;
//        }

        return compResult;
    }

    private int compareNames(CaseParc lhs, CaseParc rhs) {
        String lhsName = lhs.getName();
        String rhsName = rhs.getName();

        return lhsName.compareTo(rhsName);
    }


    private int compareIDs(CaseParc lhs, CaseParc rhs) {
        Long lhsID = lhs.getId();
        Long rhsID = rhs.getId();

        return lhsID.compareTo(rhsID);
    }

    private int compareStatus(CaseParc lhs, CaseParc rhs) {
        return statusComparator.compare(lhs.getStatus(), rhs.getStatus());
    }

    private int compareDateOfCreation(CaseParc lhs, CaseParc rhs) {
        return dateOfCreationComparator.compare(lhs.getCreated(), rhs.getCreated());
    }

    private int compareLastModified(CaseParc lhs, CaseParc rhs) {
        // TODO
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        return false;
    }

    public CaseSortCategory getActiveCategory() {
        return activeCategory;
    }

    public void setActiveCategory(CaseSortCategory category) {
        activeCategory = category;
    }
}
