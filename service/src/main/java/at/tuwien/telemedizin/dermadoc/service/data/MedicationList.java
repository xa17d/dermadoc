package at.tuwien.telemedizin.dermadoc.service.data;

import at.tuwien.telemedizin.dermadoc.entities.Medication;

import java.util.ArrayList;
import java.util.List;

/**
 * collection of available medications
 */
public class MedicationList {

    private MedicationList() {  }

    private static List<Medication> medicationList;

    static {
        medicationList = new ArrayList<>();
        medicationList.add(new Medication("cortisone ointment"));
        medicationList.add(new Medication("ibuprofen", "200mg"));
        medicationList.add(new Medication("ibuprofen", "400mg"));
        medicationList.add(new Medication("skin cream"));
        medicationList.add(new Medication("FeniHydrocort", "0.5%"));
        medicationList.add(new Medication("FeniHydrocort", "2%"));
        medicationList.add(new Medication("Amoxicillin", "125mg"));
        medicationList.add(new Medication("Amoxicillin", "500mg"));
    }

    public static List<Medication> getAll() {
        return medicationList;
    }
}
