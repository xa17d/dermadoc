package at.tuwien.telemedizin.dermadoc.app.general_entities.rest;

import java.util.ArrayList;
import java.util.Collection;

import at.tuwien.telemedizin.dermadoc.app.general_entities.Case;

/**
 * Created by daniel on 27.11.2015.
 */
public class CaseList extends ArrayList<Case> {

    public CaseList() {}
    public CaseList(Collection<Case> original) {
        super(original);
    }

    public CaseList(Iterable<Case> original) {
        for (Case item : original) {
            add(item);
        }
    }

}
