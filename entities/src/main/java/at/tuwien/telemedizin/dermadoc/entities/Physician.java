package at.tuwien.telemedizin.dermadoc.entities;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Physician
 */
@Entity
@Embeddable
@Table(name = "physician")
@PrimaryKeyJoinColumn(name = "person_id")
public class Physician extends User {
    @Override
    public String toString() {
        return "[Physician] "+super.toString();
    }
}
