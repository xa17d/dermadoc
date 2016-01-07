package at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata;

import android.os.Parcel;
import android.os.Parcelable;

import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.AnamnesisQuestion;

/**
 * Created by daniel on 24.11.2015.
 */
public abstract class AnamnesisQuestionParc implements Parcelable {

    public AnamnesisQuestionParc() { }

    private String question;
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    @Override
    public String toString() {
        return "AnamnesisQuestionParc{" +
                "question='" + question + '\'' +
                '}';
    }

    /**
     * mapping constructor
     *
     * @param anamnesisQuestion
     */
    public AnamnesisQuestionParc(AnamnesisQuestion anamnesisQuestion) {
        this.question = anamnesisQuestion.getQuestion();
    }


// parcelable ################################

    public AnamnesisQuestionParc(Parcel in) {

        this.question = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
    }

}
