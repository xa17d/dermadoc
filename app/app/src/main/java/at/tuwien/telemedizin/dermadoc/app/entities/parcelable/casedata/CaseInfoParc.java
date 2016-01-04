package at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.UserParc;
import at.tuwien.telemedizin.dermadoc.app.helper.ParcelableHelper;
import at.tuwien.telemedizin.dermadoc.entities.BodyLocalization;
import at.tuwien.telemedizin.dermadoc.entities.CaseStatus;
import at.tuwien.telemedizin.dermadoc.entities.PainIntensity;
import at.tuwien.telemedizin.dermadoc.entities.User;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseInfo;

/**
 * General case information.
 */
public class CaseInfoParc extends CaseDataParc {

    public CaseInfoParc(long id, Calendar created, UserParc author, BodyLocalization localization, PainIntensity pain, double size) {
        super(id, created, author);

        this.localization = localization;
        this.pain = pain;
        this.size = size;
    }

    private BodyLocalization localization;
    public BodyLocalization getLocalization() { return localization; }

    private PainIntensity pain;
    public PainIntensity getPain() { return pain; }

    private double size;
    public double getSize() { return size; }

    @Override
    public String toString() {
        return super.toString() +
                "CaseInfoParc{" +
                "localization=" + localization +
                ", pain=" + pain +
                ", size=" + size +
                '}';
    }

    /**
     * mapping constructor
     *
     * @param caseInfo
     */
    public CaseInfoParc(CaseInfo caseInfo) {
        this(caseInfo.getId(),
                caseInfo.getCreated(),
                ParcelableHelper.mapUserToUserParc(caseInfo.getAuthor()),
                caseInfo.getLocalization(),
                caseInfo.getPain(),
                caseInfo.getSize());
    }


// parcelable ################################

    public CaseInfoParc(Parcel in) {

        super(in);

        String bodyLocalizationStr = in.readString();
        if (bodyLocalizationStr != null) {
            this.localization = BodyLocalization.valueOf(bodyLocalizationStr);
        }

        String painIntensityStr = in.readString();
        if (painIntensityStr != null) {
            this.pain = PainIntensity.valueOf(painIntensityStr);
        }

        this.size = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        super.writeToParcel(dest, flags);

        String bodyLocalizationStr = localization != null ? localization.name() : null;
        dest.writeString(bodyLocalizationStr);

        String painIntensityStr = pain != null ? pain.name() : null;
        dest.writeString(painIntensityStr);

        dest.writeDouble(size);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<CaseInfoParc> CREATOR = new Parcelable.Creator<CaseInfoParc>() {
        public CaseInfoParc createFromParcel(Parcel in) {
            return new CaseInfoParc(in);
        }

        public CaseInfoParc[] newArray(int size) {
            return new CaseInfoParc[size];
        }
    };
}
