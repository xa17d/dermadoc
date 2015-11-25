package at.tuwien.telemedizin.dermadoc.service.rest;

/**
 * Created by Lucas on 25.11.2015.
 */
public class RestService implements IRestService {

    public void getTestPatient() {

        Thread t = new Thread(new Runnable() {
            public void run() {

            }
        });
        t.start();
    }
}
