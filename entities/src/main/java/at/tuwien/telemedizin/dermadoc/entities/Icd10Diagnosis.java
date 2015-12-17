package at.tuwien.telemedizin.dermadoc.entities;

/**
 * Created by Lucas on 01.12.2015.
 */
public class Icd10Diagnosis {

    //TODO actually use icd 10

    public Icd10Diagnosis() {  }

    public Icd10Diagnosis(String icd10Code, String icd10Name) {
        this.icd10Code = icd10Code;
        this.icd10Name = icd10Name;
    }

    private String icd10Code;
    public void setIcd10Code(String icd10Code) { this.icd10Code = icd10Code; }
    public String getIcd10Code() { return icd10Code; }

    private String icd10Name;
    public void setIcd10Name(String icd10Name) { this.icd10Name = icd10Name; }
    public String getIcd10Name() { return icd10Name; }

    @Override
    public String toString() {
        return icd10Code + " - " + (icd10Name.length()+icd10Code.length() > 50 ? (icd10Name.substring(0,40) + "...") : icd10Name);
    }
}
