package at.tuwien.telemedizin.dermadoc.app.general_entities.rest;

import java.util.ArrayList;
import java.util.Collection;

import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.CaseData;

/**
 * Created by daniel on 30.11.2015.
 */
public class CaseDataList extends ArrayList<CaseData> {

    public CaseDataList() {}
    public CaseDataList(Collection<CaseData> original) {
        super(original);
    }

}