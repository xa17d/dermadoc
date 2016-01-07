package at.tuwien.telemedizin.dermadoc.app.entities.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import at.tuwien.telemedizin.dermadoc.app.general_entities.User;

/**
 * Abstract User
 */
public abstract class UserParc implements Parcelable {
    private long id;
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    private String mail;
    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }

    private String password;
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    private GeoLocationParc location;
    public GeoLocationParc getLocation() { return location; }
    public void setLocation(GeoLocationParc location) { this.location = location; }

    public UserParc() {

    }

    @Override
    public String toString() {
        return "UserParc{" +
                "id=" + id +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", location=" + location +
                '}';
    }

    /**
     * map User to UserParc
     * @param user
     */
    public UserParc(UserParc user) {
        this(user.getId(), user.getMail(), user.getPassword(), user.getName(), user.getLocation());

    }

    private UserParc(long id, String mail, String password, String name, GeoLocationParc location) {
        setId(id);
        setMail(mail);
        setPassword(password);
        setName(name);
        setLocation(location);
    }

    // map
    public UserParc(User user) {
        this(user.getId(), user.getMail(), user.getPassword(), user.getName(),
                user.getLocation() != null ? new GeoLocationParc(user.getLocation()) : null);
    }



    // parcelable ################################


    public UserParc(Parcel in) {
        long id = in.readLong();
        String mail = in.readString();
        String password = in.readString();
        String name = in.readString();
        GeoLocationParc location = in.readParcelable(GeoLocationParc.class.getClassLoader());

        setId(id);
        setMail(mail);
        setPassword(password);
        setName(name);
        setLocation(location);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(getId());
        dest.writeString(getMail());
        dest.writeString(getPassword());
        dest.writeString(getName());
        dest.writeParcelable(location, flags);

    }

}
