package at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.UserParc;
import at.tuwien.telemedizin.dermadoc.app.helper.ParcelableHelper;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.TextMessage;

/**
 * Unstructured text message.
 */
public class TextMessageParc extends CaseDataParc {

    public TextMessageParc(long id, Calendar created, UserParc author, String message) {
        super(id, created, author);

        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return super.toString() +
                "TextMessageParc{" +
                "message='" + message + '\'' +
                '}';
    }

    /**
     * mapping constructor
     *
     * @param textMessage
     */
    public TextMessageParc(TextMessage textMessage) {
        this(textMessage.getId(),
                textMessage.getCreated(),
                ParcelableHelper.mapUserToUserParc(textMessage.getAuthor()),
                textMessage.getMessage());
    }


// parcelable ################################

    public TextMessageParc(Parcel in) {

        super(in);

        this.message = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        super.writeToParcel(dest, flags);
        dest.writeString(message);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<TextMessageParc> CREATOR = new Parcelable.Creator<TextMessageParc>() {
        public TextMessageParc createFromParcel(Parcel in) {
            return new TextMessageParc(in);
        }

        public TextMessageParc[] newArray(int size) {
            return new TextMessageParc[size];
        }
    };

}
