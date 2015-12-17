package at.tuwien.telemedizin.dermadoc.desktop.util;

import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;

import java.util.Comparator;

/**
 * compares
 */
public class CaseDataComparator implements Comparator<CaseData> {

    @Override
    public int compare(CaseData cd1, CaseData cd2) {
        return cd1.getCreated().compareTo(cd2.getCreated());
    }
}
