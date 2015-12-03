package at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.casedata.Anamnesis;

/**
 * Created by FAUser on 02.12.2015.
 *
 * helps the fragments retrieve and update case-data from the activity
 */
public interface OnCaseDataRequestAndUpdateInterface {

    public Anamnesis getAnamnesisForm();

    public void updateAnamnesis(Anamnesis anamnesis);

    public Case getCase();




}
