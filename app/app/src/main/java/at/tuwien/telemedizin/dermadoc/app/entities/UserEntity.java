package at.tuwien.telemedizin.dermadoc.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import at.tuwien.telemedizin.dermadoc.entities.GeoLocation;
import at.tuwien.telemedizin.dermadoc.entities.User;

/**
 * Created by FAUser on 20.11.2015.
 */
public class UserEntity extends User implements Parcelable {

    public UserEntity(User user) {
        this(user.getId(), user.getMail(), user.getPassword(), user.getName(), user.getLocation());
    }

    public UserEntity(Parcel in) {
        long id = in.readLong();
        String mail = in.readString();
        String password = in.readString();
        String name = in.readString();
        GeoLocation location = in.readParcelable(GeoLocationEntity.class.getClassLoader());

        super.setId(id);
        super.setMail(mail);
        super.setPassword(password);
        super.setName(name);
        super.setLocation(location);
    }

    private UserEntity(long id, String mail, String password, String name, GeoLocation location) {
        super.setId(id);
        super.setMail(mail);
        super.setPassword(password);
        super.setName(name);
        super.setLocation(location);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(super.getId());
        dest.writeString(super.getMail());
        dest.writeString(super.getPassword());
        dest.writeString(super.getName());

        // location != null?
        GeoLocation location = super.getLocation();
        GeoLocationEntity locationEntity = null;
        if (location != null) {
            locationEntity = new GeoLocationEntity(super.getLocation());
        }
        dest.writeParcelable(locationEntity, flags);

    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new UserEntity(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
