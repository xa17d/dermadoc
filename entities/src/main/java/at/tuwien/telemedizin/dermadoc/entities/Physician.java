package at.tuwien.telemedizin.dermadoc.entities;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Physician
 */
@Entity
@Table(name = "patient")
@PrimaryKeyJoinColumn(name = "person_id")
public class Physician extends User {
    @Override
    public String toString() {
        return "[Physician] "+super.toString();
    }
}
