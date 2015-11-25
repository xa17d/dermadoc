package at.tuwien.telemedizin.dermadoc.service.rest;

/**
 * Created by Lucas on 25.11.2015.
 */
public class TestGetTestPatient {

    public static void main(String[] args) {
        IRestService testRest = new RestService();
        System.out.println(testRest.getTestPatient().getName());
    }
}
