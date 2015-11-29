package at.tuwien.telemedizin.dermadoc.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import at.tuwien.telemedizin.dermadoc.entities.casedata.AnamnesisQuestionText;

/**
 * Created by FAUser on 19.11.2015.
 *
 */
public class AnamnesisQuestionTextEntity extends AnamnesisQuestionText implements Parcelable {

    public AnamnesisQuestionTextEntity() {
        super();
    }

    public AnamnesisQuestionTextEntity(AnamnesisQuestionText anamnesisQuestionObject) {
        super.setQuestion(anamnesisQuestionObject.getQuestion());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static AnamnesisQuestionTextEntity createAnamnesisQuestionTextEntity(Parcel in) {
        String question = in.readString();

        String answer = in.readString();


        AnamnesisQuestionTextEntity anamnesisQuestionEntity = new AnamnesisQuestionTextEntity();

        anamnesisQuestionEntity.setQuestion(question);
        anamnesisQuestionEntity.setAnswer(answer);
        return anamnesisQuestionEntity;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(super.getQuestion());

        dest.writeString(super.getAnswer());
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Creator<AnamnesisQuestionText> CREATOR = new Creator<AnamnesisQuestionText>() {
        public AnamnesisQuestionText createFromParcel(Parcel in) {
            return createAnamnesisQuestionTextEntity(in);
        }

        public AnamnesisQuestionText[] newArray(int size) {
            return new AnamnesisQuestionText[size];
        }
    };


}
