package at.tuwien.telemedizin.dermadoc.entities;

import javax.persistence.*;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long medicationId;

    public Long getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(Long medicationId) {
        this.medicationId = medicationId;
    }




    private String name;
    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    private String dosis;
    public String getDosis() { return dosis; }
    public void setDosis(String dosis) { this.dosis = dosis; }

    @Override
    public String toString() {
        return name + (dosis == null ? "" : " - " + dosis);
    }
}
