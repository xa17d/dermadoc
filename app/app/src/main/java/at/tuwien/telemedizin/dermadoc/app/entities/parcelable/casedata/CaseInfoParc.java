package at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.UserParc;
import at.tuwien.telemedizin.dermadoc.app.helper.ParcelableHelper;
import at.tuwien.telemedizin.dermadoc.app.general_entities.BodyLocalization;
import at.tuwien.telemedizin.dermadoc.app.general_entities.CaseStatus;
import at.tuwien.telemedizin.dermadoc.app.general_entities.PainIntensity;
import at.tuwien.telemedizin.dermadoc.app.general_entities.User;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.CaseInfo;

/**
 * General case information.
 */
public class CaseInfoParc extends CaseDataParc {

    public CaseInfoParc(long id, Calendar created, UserParc author, List<BodyLocalization> localizations, PainIntensity pain, double size, String symptromDescription) {
        super(id, created, author);

        this.localizations = localizations;
        this.pain = pain;
        this.size = size;
        this.symptomDescription = symptromDescription;
    }

    private List<BodyLocalization> localizations;
    public List<BodyLocalization> getLocalizations() { return localizations; }

    private PainIntensity pain;
    public PainIntensity getPain() { return pain; }

    private double size;
    public double getSize() { return size; }

    private String symptomDescription;
    public String getSymptomDescription() { return this.symptomDescription; }

    @Override
    public String toString() {
        return super.toString() +
                "CaseInfoParc{" +
                "localizations=" + localizations.size() +
                ", pain=" + pain +
                ", size=" + size +
                ", symptomDescription=" + symptomDescription +
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

                // TODO change in entities to list
                ParcelableHelper.mapLocalizationToList(caseInfo.getLocalization()),
                caseInfo.getPain(),
                caseInfo.getSize(),
                null); // TODO add in entities
    }


// parcelable ################################

    public CaseInfoParc(Parcel in) {

        super(in);

        List<String> bodyLocalizationsStr = new ArrayList<>();
        in.readStringList(bodyLocalizationsStr);
        this.localizations = new ArrayList<>();
        for (String s : bodyLocalizationsStr) {
            this.localizations.add(BodyLocalization.valueOf(s));
        }

        String painIntensityStr = in.readString();
        if (painIntensityStr != null) {
            this.pain = PainIntensity.valueOf(painIntensityStr);
        }

        this.size = in.readDouble();

        this.symptomDescription = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        super.writeToParcel(dest, flags);

        List<String> bodyLocalizationsStr = new ArrayList<>();
        if (localizations != null) {
            for (BodyLocalization bl : localizations) {
                bodyLocalizationsStr.add(bl.name());
            }
        }
        dest.writeStringList(bodyLocalizationsStr);

        String painIntensityStr = pain != null ? pain.name() : null;
        dest.writeString(painIntensityStr);

        dest.writeDouble(size);

        dest.writeString(symptomDescription);
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
