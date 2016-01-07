package at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata;

import android.os.Parcel;
import android.os.Parcelable;

import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.AnamnesisQuestionText;

/**
 * Created by daniel on 24.11.2015.
 */
public class AnamnesisQuestionTextParc extends AnamnesisQuestionParc {
    private String answer;

    public AnamnesisQuestionTextParc() {

    }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return super.toString() +
                "AnamnesisQuestionTextParc{" +
                "answer='" + answer + '\'' +
                '}';
    }

    /**
     * mapping constructor
     *
     * @param questionText
     */
    public AnamnesisQuestionTextParc(AnamnesisQuestionText questionText) {
        super(questionText);
        this.answer = questionText.getAnswer();
    }


// parcelable ################################

    public AnamnesisQuestionTextParc(Parcel in) {

        super(in);

        this.answer = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        super.writeToParcel(dest, flags);
        dest.writeString(answer);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<AnamnesisQuestionTextParc> CREATOR = new Parcelable.Creator<AnamnesisQuestionTextParc>() {
        public AnamnesisQuestionTextParc createFromParcel(Parcel in) {
            return new AnamnesisQuestionTextParc(in);
        }

        public AnamnesisQuestionTextParc[] newArray(int size) {
            return new AnamnesisQuestionTextParc[size];
        }
    };
}
