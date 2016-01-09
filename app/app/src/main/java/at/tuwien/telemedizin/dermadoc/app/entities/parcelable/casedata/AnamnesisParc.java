package at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.UserParc;
import at.tuwien.telemedizin.dermadoc.app.helper.ParcelableHelper;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.Anamnesis;

/**
 * Created by daniel on 24.11.2015.
 */
public class AnamnesisParc extends CaseDataParc {

    public AnamnesisParc(Long id, Calendar created, UserParc author, String message, List<AnamnesisQuestionParc> questions) {
        super(id, created, author);

        this.message = message;
        this.questions = questions;
    }

    private String message;
    public String getMessage() { return message; }

    private List<AnamnesisQuestionParc> questions;
    public List<AnamnesisQuestionParc> getQuestions() { return questions; }


    @Override
    public String toString() {
        return "AnamnesisParc{" +
                "message='" + message + '\'' +
                ", questions=" + questions +
                '}';
    }

    /**
     * mapping constructor
     *
     * @param anamnesis
     */
    public AnamnesisParc(Anamnesis anamnesis) {
        this(anamnesis.getId(),
                anamnesis.getCreated(),
                ParcelableHelper.mapUserToUserParc(anamnesis.getAuthor()),
                anamnesis.getMessage(),
                ParcelableHelper.mapAnamnesisQuestionsToParc(anamnesis.getQuestions()));
    }


// parcelable ################################

    public AnamnesisParc(Parcel in) {

        super(in);

        this.message = in.readString();
        this.questions = new ArrayList<>();
        in.readList(this.questions, AnamnesisQuestionParc.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        super.writeToParcel(dest, flags);
        dest.writeString(message);

        dest.writeList(questions);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<AnamnesisParc> CREATOR = new Parcelable.Creator<AnamnesisParc>() {
        public AnamnesisParc createFromParcel(Parcel in) {
            return new AnamnesisParc(in);
        }

        public AnamnesisParc[] newArray(int size) {
            return new AnamnesisParc[size];
        }
    };
}
