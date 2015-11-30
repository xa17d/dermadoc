package at.tuwien.telemedizin.dermadoc.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

import at.tuwien.telemedizin.dermadoc.app.helper.ParcelableHelper;
import at.tuwien.telemedizin.dermadoc.entities.User;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;

/**
 * Created by FAUser on 26.11.2015.
 *
 * TODO
 */
public abstract class CaseDataEntity extends CaseData implements Parcelable {


    public CaseDataEntity(long id, Calendar created, User author) {
        super(id, created, author);
    }

    public CaseDataEntity(CaseData caseData) {
        super(caseData.getId(), caseData.getCreated(), caseData.getAuthor());
    }

}
