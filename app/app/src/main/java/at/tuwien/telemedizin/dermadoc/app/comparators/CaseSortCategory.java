package at.tuwien.telemedizin.dermadoc.app.comparators;

/**
 * Created by FAUser on 18.11.2015.
 */
public enum CaseSortCategory {

    NAME,
    ID,
    STATUS,
    DATE_OF_CREATION,
    NOTIFICATIONS;

    public static CaseSortCategory getCategory(int index) {
        switch (index){
            case 1: return NAME;
            case 2: return ID;
            case 3: return STATUS;
            case 4: return DATE_OF_CREATION;
            case 5: return NOTIFICATIONS;
        }
        return NAME; // DEFAULT
    }

}
