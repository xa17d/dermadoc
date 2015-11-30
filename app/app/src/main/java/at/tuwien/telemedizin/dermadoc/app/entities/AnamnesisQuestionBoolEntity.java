package at.tuwien.telemedizin.dermadoc.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import at.tuwien.telemedizin.dermadoc.entities.casedata.AnamnesisQuestion;
import at.tuwien.telemedizin.dermadoc.entities.casedata.AnamnesisQuestionBool;

/**
 * Created by FAUser on 19.11.2015.
 *
 */
public class AnamnesisQuestionBoolEntity extends AnamnesisQuestionBool implements Parcelable {

    public AnamnesisQuestionBoolEntity() {
        super();
    }

    public AnamnesisQuestionBoolEntity(AnamnesisQuestionBool anamnesisQuestionObject) {
        super.setQuestion(anamnesisQuestionObject.getQuestion());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static AnamnesisQuestionBoolEntity createAnamnesisQuestionBoolEntity(Parcel in) {
        String question = in.readString();

        Boolean answer = in.readByte() != 0;


        AnamnesisQuestionBoolEntity anamnesisQuestionEntity = new AnamnesisQuestionBoolEntity();

        anamnesisQuestionEntity.setQuestion(question);
        anamnesisQuestionEntity.setAnswer(answer);
        return anamnesisQuestionEntity;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(super.getQuestion());

        dest.writeByte((byte) (super.getAnswer() ? 1 : 0) );
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Creator<AnamnesisQuestionBool> CREATOR = new Creator<AnamnesisQuestionBool>() {
        public AnamnesisQuestionBool createFromParcel(Parcel in) {
            return createAnamnesisQuestionBoolEntity(in);
        }

        public AnamnesisQuestionBool[] newArray(int size) {
            return new AnamnesisQuestionBool[size];
        }
    };


}
