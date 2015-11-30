package at.tuwien.telemedizin.dermadoc.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import at.tuwien.telemedizin.dermadoc.entities.GeoLocation;

/**
 * Created by FAUser on 20.11.2015.
 */
public class GeoLocationEntity extends GeoLocation implements Parcelable {

    public GeoLocationEntity(String name, double latitude, double longitude) {
        super(name, latitude, longitude);
    }

    public GeoLocationEntity(GeoLocation location) {
        super(location.getName(), location.getLatitude(), location.getLatitude());
    }

    public static GeoLocationEntity createGeoLocationEntity(Parcel in) {
        String name = in.readString();
        double latitude = in.readDouble();
        double longitude = in.readDouble();

        return new GeoLocationEntity(name, latitude, longitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(super.getName());
        dest.writeDouble(super.getLatitude());
        dest.writeDouble(super.getLongitude());
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<GeoLocation> CREATOR = new Parcelable.Creator<GeoLocation>() {
        public GeoLocation createFromParcel(Parcel in) {
            return createGeoLocationEntity(in);
        }

        public GeoLocation[] newArray(int size) {
            return new GeoLocation[size];
        }
    };
}
