package at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case;

import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.entities.CaseValidationError;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.CaseParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.PhysicianParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AnamnesisParc;

/**
 * Created by FAUser on 02.12.2015.
 *
 * helps the fragments retrieve and update case-data from the activity
 */
public interface OnCaseDataRequestAndUpdateInterface {

    public AnamnesisParc getAnamnesisForm();

    public CaseParc getCase();

    public List<PhysicianParc> getNearbyPhysicians();

    public CaseParc finishEditing();

    public List<CaseValidationError> getCaseValidationErrors();




}
