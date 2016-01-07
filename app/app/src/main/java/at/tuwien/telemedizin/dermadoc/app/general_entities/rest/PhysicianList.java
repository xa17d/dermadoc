package at.tuwien.telemedizin.dermadoc.app.general_entities.rest;

import java.util.ArrayList;
import java.util.Collection;

import at.tuwien.telemedizin.dermadoc.app.general_entities.Physician;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.CaseData;

/**
 * Created by fabian
 */
public class PhysicianList extends ArrayList<Physician> {

    public PhysicianList() {}
    public PhysicianList(Collection<Physician> original) {
        super(original);
    }

}