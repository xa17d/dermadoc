package at.tuwien.telemedizin.dermadoc.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;

/**
 * Abstract User
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Patient.class),
        @JsonSubTypes.Type(value = Physician.class)
})
@MappedSuperclass
@Table(name = "person")// indexes = @Index(name="user_mail_constraint",columnList = "mail", unique = true))
public abstract class User {
    @Id
    private long id;
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    @Column(nullable = false)
    private String mail;
    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }

    @Column(nullable = false)
    private String password;
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Column(name = "location")
    @Embedded
    private GeoLocation location;
    public GeoLocation getLocation() { return location; }
    public void setLocation(GeoLocation location) { this.location = location; }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int hashCode() {
        return (int)this.getId();
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
        return this.getId() == u.getId();
    }
}
