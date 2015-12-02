package at.tuwien.telemedizin.dermadoc.entities.rest;

import at.tuwien.telemedizin.dermadoc.entities.Case;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by daniel on 27.11.2015.
 */
public class CaseList extends ArrayList<Case> {

    public CaseList() {}
    public CaseList(Collection<Case> original) {
        super(original);
    }

}
