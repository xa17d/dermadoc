package at.tuwien.telemedizin.dermadoc.app.persistence;

import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.CaseParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.GeoLocationParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.PatientParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.PhysicianParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AnamnesisParc;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Case;
import at.tuwien.telemedizin.dermadoc.app.general_entities.GeoLocation;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Patient;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Physician;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.Anamnesis;

/**
 * Created by FAUser on 02.12.2015.
 */
public interface ContentProvider {


    // #### get information #####
    public PatientParc getCurrentUser();

    public List<PhysicianParc> getNearbyPhysicians(GeoLocationParc geoLocation);

    public AnamnesisParc getAnamnesisForm();

    public List<CaseParc> getCurrentCasesOfUser();

    // #### send information #####

    /**
     * returns the ID of the case
     * @param caseItem
     * @return
     */
    public long saveNewCase(CaseParc caseItem);

    public boolean saveModifiedCase(CaseParc caseItem);

    // etc TODO
}
