package at.tuwien.telemedizin.dermadoc.entities;

/**
 * Medication
 */
public class Medication {

    public Medication() {  }

    public Medication(String name) {
        this.name = name;
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
