package at.tuwien.telemedizin.dermadoc.service.data;

import at.tuwien.telemedizin.dermadoc.entities.Icd10Diagnosis;
import at.tuwien.telemedizin.dermadoc.entities.Medication;
import at.tuwien.telemedizin.dermadoc.entities.casedata.Diagnosis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucas on 17.12.2015.
 */
public class Icd10List {

    private Icd10List() {  }

    /*
    //TODO use tree node
    //https://en.wikipedia.org/wiki/ICD-10_Chapter_XII:_Diseases_of_the_skin_and_subcutaneous_tissue
    private static TreeNode<Icd10Diagnosis> root;

    static {
        root = new TreeNode<Icd10Diagnosis>(new Icd10Diagnosis("", "root"));
        {
            TreeNode<Icd10Diagnosis> node0 = root.addChild(new Icd10Diagnosis("", "node0"));
            TreeNode<Icd10Diagnosis> node1 = root.addChild(new Icd10Diagnosis("", "node1"));
            TreeNode<Icd10Diagnosis> node2 = root.addChild(new Icd10Diagnosis("", "node2"));
            {
                TreeNode<Icd10Diagnosis> node20 = node2.addChild(null);
                TreeNode<Icd10Diagnosis> node21 = node2.addChild(new Icd10Diagnosis("", "node21"));
                {
                    TreeNode<Icd10Diagnosis> node210 = node20.addChild(new Icd10Diagnosis("", "node210"));
                }
            }
        }
    }
    */

    private static List<Icd10Diagnosis> diagnosisList;

    static {
        diagnosisList = new ArrayList<>();
        diagnosisList.add(new Icd10Diagnosis("L00–L99","Diseases of the skin and subcutaneous tissue"));
        diagnosisList.add(new Icd10Diagnosis("L00–L08", "Infections of the skin and subcutaneous tissue"));
        diagnosisList.add(new Icd10Diagnosis("L00","Staphylococcal scalded skin syndrome"));
        diagnosisList.add(new Icd10Diagnosis("L01","Impetigo"));
        diagnosisList.add(new Icd10Diagnosis("L01.0","Impetigo"));
        diagnosisList.add(new Icd10Diagnosis("L01.1","Impetiginization of other dermatoses"));
        diagnosisList.add(new Icd10Diagnosis("L03","Cellulitis"));
        diagnosisList.add(new Icd10Diagnosis("L03.0","Cellulitis of finger and toe"));
        diagnosisList.add(new Icd10Diagnosis("L03.1","Cellulitis of other parts of limb"));
        diagnosisList.add(new Icd10Diagnosis("L03.2","Cellulitis of face"));
        diagnosisList.add(new Icd10Diagnosis("L03.3","Cellulitis of trunk"));
        diagnosisList.add(new Icd10Diagnosis("L03.8","Cellulitis of other sites"));
        diagnosisList.add(new Icd10Diagnosis("L03.9","Cellulitis, unspecified"));
        diagnosisList.add(new Icd10Diagnosis("L10–L14","Bullous disorders"));
        diagnosisList.add(new Icd10Diagnosis("L10","Pemphigus"));
        diagnosisList.add(new Icd10Diagnosis("L10.1","Pemphigus vulgaris"));
        diagnosisList.add(new Icd10Diagnosis("L10.2","Pemphigus vegetans"));
        diagnosisList.add(new Icd10Diagnosis("L10.3","Pemphigus foliaceus"));
        diagnosisList.add(new Icd10Diagnosis("L10.4","Brazilian pemphigus"));
        diagnosisList.add(new Icd10Diagnosis("L13","Other bullous disorders"));
        diagnosisList.add(new Icd10Diagnosis("L13.0","Dermatitis herpetiformis"));
        diagnosisList.add(new Icd10Diagnosis("L13.1","Subcorneal pustular dermatitis"));
        diagnosisList.add(new Icd10Diagnosis("L14","Bullous disorders in diseases classified elsewhere"));
        diagnosisList.add(new Icd10Diagnosis("L40–L45","Papulosquamous disorders"));
        diagnosisList.add(new Icd10Diagnosis("L40","Psoriasis"));
        diagnosisList.add(new Icd10Diagnosis("L41","Parapsoriasis"));
        diagnosisList.add(new Icd10Diagnosis("L42","Pityriasis rosea"));
        diagnosisList.add(new Icd10Diagnosis("L43","Lichen planus"));

    }

    public static List<Icd10Diagnosis> getAll() {
        return diagnosisList;
    }

    public static int[] getLevels() {
        return new int[]{
            0,
                1,
                    2,
                    2,
                        3,
                        3,
                    2,
                        3,
                        3,
                        3,
                        3,
                        3,
                        3,
                1,
                    2,
                        3,
                        3,
                        3,
                        3,
                    2,
                        3,
                        3,
                    2,
                1,
                    2,
                    2,
                    2,
                    2
        };
    }
}
