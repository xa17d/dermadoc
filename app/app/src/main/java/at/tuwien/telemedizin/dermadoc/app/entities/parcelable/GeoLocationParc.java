package at.tuwien.telemedizin.dermadoc.app.entities.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import at.tuwien.telemedizin.dermadoc.app.general_entities.GeoLocation;

/**
 * Geographic location
 */
public class GeoLocationParc implements Parcelable {

    public GeoLocationParc(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public GeoLocationParc(GeoLocation location) {
        this(location.getName(), location.getLatitude(), location.getLatitude());
    }

    private GeoLocationParc() {
        // empty constructor for parcelable
    }

    private double latitude;
    public double getLatitude() { return latitude; }

    private double longitude;
    public double getLongitude() { return longitude; }

    private String name;
    public String getName() { return name; }

    /***
     * Calculates the distance between two GeoLocations in meters
     * Source: http://www.geodatasource.com/developers/java
     * @param a First GeoLocation
     * @param b Second GeoLocation
     * @return Distance between a and b in meters
     */
    public static double Distance(GeoLocationParc a, GeoLocationParc b) {
        double theta = a.getLongitude() - b.getLongitude();
        double dist = Math.sin(deg2rad(a.getLatitude())) * Math.sin(deg2rad(b.getLatitude())) + Math.cos(deg2rad(a.getLatitude())) * Math.cos(deg2rad(b.getLatitude())) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1.609344 * 1000;
        return dist;
    }

    /***
     * Converts decimal degrees to radians
     * @param deg degrees
     * @return radians
     */
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /***
     * This function converts radians to decimal degrees
     * @param rad radians
     * @return decimal degrees
     */
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    @Override
    public String toString() {
        return "GeoLocation{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", name='" + name + '\'' +
                '}';
    }



    // parcelable ######################################
    public GeoLocationParc(Parcel in) {
        this();
        String nameP = in.readString();
        double latitudeP = in.readDouble();
        double longitudeP = in.readDouble();

        this.name = nameP;
        this.latitude = latitudeP;
        this.longitude = longitudeP;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getName());
        dest.writeDouble(this.getLatitude());
        dest.writeDouble(this.getLongitude());
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<GeoLocationParc> CREATOR = new Parcelable.Creator<GeoLocationParc>() {
        public GeoLocationParc createFromParcel(Parcel in) {
            return new GeoLocationParc(in);
        }

        public GeoLocationParc[] newArray(int size) {
            return new GeoLocationParc[size];
        }
    };
}
