package at.tuwien.telemedizin.dermadoc.entities;

/**
 * Abstract User
 */
public abstract class User {
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

    private GeoLocation location;
    public GeoLocation getLocation() { return location; }
    public void setLocation(GeoLocation location) { this.location = location; }

    @Override
    public String toString() {
        return getName();
    }
}
