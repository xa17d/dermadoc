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

    private Long id;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AnamnesisQuestionParc{" +
                "question='" + question + '\'' +
                ", id=" + id +
                '}';
    }

    /**
     * mapping constructor
     *
     * @param anamnesisQuestion
     */
    public AnamnesisQuestionParc(AnamnesisQuestion anamnesisQuestion) {
        this.id = anamnesisQuestion.getId();
        this.question = anamnesisQuestion.getQuestion();
    }


// parcelable ################################

    public AnamnesisQuestionParc(Parcel in) {
        this.id = in.readLong();
        this.question = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(question);
    }

}
