package at.tuwien.telemedizin.dermadoc.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Medication
 */
@Entity
@Table(name = "medication")
public class Medication {

    public Medication() {  }

    public Medication(String name) {
        this.name = name;
    }

    public Medication(String name, String dosis) {
        this.name = name;
        this.dosis = dosis;
    }


    @Id
    private String name;
    public String getName() { return name; }

    private String dosis;
    public String getDosis() { return dosis; }
    public void setDosis(String dosis) { this.dosis = dosis; }

    @Override
    public String toString() {
        return name + (dosis == null ? "" : " - " + dosis);
    }
}
