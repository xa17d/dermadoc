package at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata;

import android.os.Parcel;
import android.os.Parcelable;

import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.AnamnesisQuestionBool;

/**
 * Created by daniel on 24.11.2015.
 */
public class AnamnesisQuestionBoolParc extends AnamnesisQuestionParc {
    private boolean answer;

    public AnamnesisQuestionBoolParc() {}

    public boolean getAnswer() { return answer; }
    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return super.toString() +
                "AnamnesisQuestionBoolParc{" +
                "answer=" + answer +
                '}';
    }

    /**
     * mapping constructor
     *
     * @param questionBool
     */
    public AnamnesisQuestionBoolParc(AnamnesisQuestionBool questionBool) {
        super(questionBool);
        this.answer = questionBool.getAnswer();
    }


// parcelable ################################

    public AnamnesisQuestionBoolParc(Parcel in) {

        super(in);

        int answerAsInt = in.readInt();
        this.answer = (answerAsInt == 1);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        super.writeToParcel(dest, flags);
        int answerAsInt = answer ? 1 : 0;
        dest.writeInt(answerAsInt);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<AnamnesisQuestionBoolParc> CREATOR = new Parcelable.Creator<AnamnesisQuestionBoolParc>() {
        public AnamnesisQuestionBoolParc createFromParcel(Parcel in) {
            return new AnamnesisQuestionBoolParc(in);
        }

        public AnamnesisQuestionBoolParc[] newArray(int size) {
            return new AnamnesisQuestionBoolParc[size];
        }
    };

}
