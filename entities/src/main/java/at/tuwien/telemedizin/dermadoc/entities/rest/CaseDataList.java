package at.tuwien.telemedizin.dermadoc.entities.rest;

import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by daniel on 30.11.2015.
 */
public class CaseDataList extends ArrayList<CaseData> {

    public CaseDataList() {}
    public CaseDataList(Collection<CaseData> original) {
        super(original);
    }

    public CaseDataList(Iterable<CaseData> original) {
        for (CaseData item : original) {
            add(item);
        }
    }

}