package at.tuwien.telemedizin.dermadoc.app.persistence;

import java.util.List;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.GeoLocation;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.casedata.Anamnesis;

/**
 * Created by FAUser on 02.12.2015.
 */
public interface ContentProvider {


    // #### get information #####
    public Patient getCurrentUser();

    public List<Physician> getNearbyPhysicians(GeoLocation geoLocation);

    public Anamnesis getAnamnesisForm();

    public List<Case> getCurrentCasesOfUser();

    // #### send information #####

    /**
     * returns the ID of the case
     * @param caseItem
     * @return
     */
    public long saveNewCase(Case caseItem);

    public boolean saveModifiedCase(Case caseItem);

    // etc TODO
}
