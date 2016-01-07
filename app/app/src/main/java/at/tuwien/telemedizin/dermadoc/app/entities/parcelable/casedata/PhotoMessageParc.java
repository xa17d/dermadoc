package at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.UserParc;
import at.tuwien.telemedizin.dermadoc.app.helper.ParcelableHelper;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.PhotoMessage;

/**
 * Created by daniel on 11.11.2015.
 */
public class PhotoMessageParc extends CaseDataParc {

    public PhotoMessageParc(long id, Calendar created, UserParc author, String mime, byte[] photoData) {
        super(id, created, author);

        this.mime = mime;
        this.photoData = photoData;
    }

    private String mime;
    public String getMime() { return mime; }

    private byte[] photoData;
    public byte[] getPhotoData() { return photoData; }

    @Override
    public String toString() {
        return  super.toString() +
                "PhotoMessageParc{" +
                "mime='" + mime + '\'' +
                ", photoData != null" + (photoData != null) +
                '}';
    }

    /**
     * mapping constructor
     *
     * @param photoMessage
     */
    public PhotoMessageParc(PhotoMessage photoMessage) {
        this(photoMessage.getId(),
                photoMessage.getCreated(),
                ParcelableHelper.mapUserToUserParc(photoMessage.getAuthor()),
                photoMessage.getMime(),
                photoMessage.getPhotoData());
    }


// parcelable ################################

    public PhotoMessageParc(Parcel in) {

        super(in);

        this.mime = in.readString();
        int byteArrayLength = in.readInt();
        this.photoData = new byte[byteArrayLength];
        if (byteArrayLength > 0) {
            in.readByteArray(this.photoData);
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        super.writeToParcel(dest, flags);
        dest.writeString(mime);
        dest.writeInt(photoData != null ? photoData.length : 0);
        dest.writeByteArray(photoData);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<PhotoMessageParc> CREATOR = new Parcelable.Creator<PhotoMessageParc>() {
        public PhotoMessageParc createFromParcel(Parcel in) {
            return new PhotoMessageParc(in);
        }

        public PhotoMessageParc[] newArray(int size) {
            return new PhotoMessageParc[size];
        }
    };
 }
