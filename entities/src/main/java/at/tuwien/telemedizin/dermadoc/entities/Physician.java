package at.tuwien.telemedizin.dermadoc.entities;

import javax.persistence.*;

/**
 * Physician
 */
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Physician.listPhysicians",
                query = "select p.* from person p right join physician ph on p.person_id = ph.person_id",
                resultClass = Physician.class
        )
})
@Entity
@Embeddable
@Table(name = "physician")
@PrimaryKeyJoinColumn(name = "person_id")
public class Physician extends User {
    @Override
    public String toString() {
        return "[Physician] "+super.toString();
    }

    @Override
    public boolean equals(Object o) {

        if(o == null)
            return false;

        if(this.hashCode() != o.hashCode())
            return false;

        if(!(o instanceof Physician))
            return false;

        Physician p = (Physician) o;
        return this.getId().equals(p.getId());
    }
}
