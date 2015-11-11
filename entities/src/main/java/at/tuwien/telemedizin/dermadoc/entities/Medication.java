package at.tuwien.telemedizin.dermadoc.entities;

/**
 * Medication
 */
public class Medication {
    public Medication(String name) {
        this.name = name;
    }

    private String name;
    public String getName() { return name; }
}
