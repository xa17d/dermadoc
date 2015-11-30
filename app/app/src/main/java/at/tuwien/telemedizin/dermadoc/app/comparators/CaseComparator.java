package at.tuwien.telemedizin.dermadoc.app.comparators;

import java.util.Comparator;

import at.tuwien.telemedizin.dermadoc.entities.Case;

/**
 * Created by FAUser on 18.11.2015.
 *
 * Comparator for comparing Case-Objects - needed to sort the list
 */
public class CaseComparator implements Comparator<Case> {


    // multiple Comparators, because multiple ways to sort case-items
    private CaseStatusComparator statusComparator = new CaseStatusComparator();
    private CaseDateOfCreationComparator dateOfCreationComparator = new CaseDateOfCreationComparator();

    private CaseSortCategory activeCategory = CaseSortCategory.STATUS; // default category


    @Override
    public int compare(Case lhs, Case rhs) {

        int compResult = 0;

        if (activeCategory == CaseSortCategory.NAME) {
            compResult = compareNames(lhs, rhs);
        } else if (activeCategory == CaseSortCategory.ID) {
            compResult = compareIDs(lhs, rhs);
        } else if (activeCategory == CaseSortCategory.STATUS) {
            compResult = compareStatus(lhs, rhs);
        } else if (activeCategory == CaseSortCategory.DATE_OF_CREATION) {
            compResult = compareDateOfCreation(lhs, rhs);
        } else if (activeCategory == CaseSortCategory.LAST_MODIFIED) {
            compResult = compareLastModified(lhs, rhs);
        }

        // if equal, take ID as secondary parameter
//        if (compResult == 0) {
//            return compareIDs(lhs, rhs);
//        } else {
//            return compResult;
//        }

        return compResult;
    }

    private int compareNames(Case lhs, Case rhs) {
        String lhsName = "TODO"; // TODO
        String rhsName = "TODO"; // TODO

        return lhsName.compareTo(rhsName);
    }


    private int compareIDs(Case lhs, Case rhs) {
        Long lhsID = lhs.getId();
        Long rhsID = rhs.getId();

        return lhsID.compareTo(rhsID);
    }

    private int compareStatus(Case lhs, Case rhs) {
        return statusComparator.compare(lhs.getStatus(), rhs.getStatus());
    }

    private int compareDateOfCreation(Case lhs, Case rhs) {
        return dateOfCreationComparator.compare(lhs.getCreated(), rhs.getCreated());
    }

    private int compareLastModified(Case lhs, Case rhs) {
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
