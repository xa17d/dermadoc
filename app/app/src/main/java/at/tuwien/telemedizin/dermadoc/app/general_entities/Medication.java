package at.tuwien.telemedizin.dermadoc.app.general_entities;



/**
 * Medication
 */

public class Medication {

    public Medication() {  }

    public Medication(String name) {
        this.name = name;
    }

    public Medication(String name, String dosis) {
        this.name = name;
        this.dosis = dosis;
    }

    private Long medicationId;
    public Long getMedicationId() {
        return medicationId;
    }
    public void setMedicationId(Long medicationId) {
        this.medicationId = medicationId;
    }


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
