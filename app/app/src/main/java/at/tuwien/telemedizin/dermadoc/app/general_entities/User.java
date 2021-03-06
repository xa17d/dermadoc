package at.tuwien.telemedizin.dermadoc.app.general_entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


/**
 * Abstract User
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Patient.class),
        @JsonSubTypes.Type(value = Physician.class)
})
public abstract class User {

    private Long id;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }


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

    @Override
    public int hashCode() {
        if (getId() == null) { return 0; }
        return this.getId().intValue();
    }

    @Override
    public boolean equals(Object o) {

        if(o == null)
            return false;

        if(this.hashCode() != o.hashCode())
            return false;

        if(this.getClass() != o.getClass())
            return false;

        User u = (User) o;
        return this.getId().equals(u.getId());
    }
}
