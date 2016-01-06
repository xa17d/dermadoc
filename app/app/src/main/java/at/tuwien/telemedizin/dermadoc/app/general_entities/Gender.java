package at.tuwien.telemedizin.dermadoc.app.general_entities;

/**
 * Gender
 */
public enum Gender {

    Undefined("-", "undef"),
    Male("M", "male"),
    Female("F", "female");

    private String shortName;
    private String longName;

    Gender(String shortName, String longName) {
        this.shortName = shortName;
        this.longName = longName;
    }

    public String getAbbreviation() {
        return shortName;
    }

    @Override
    public String toString() {
        return longName;
    }
}
