package at.tuwien.telemedizin.dermadoc.service.rest.test;

import at.tuwien.telemedizin.dermadoc.entities.Patient;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Lucas on 25.11.2015.
 */
public class RestServiceTest implements IRestServiceTest {

    public void getTestPatient() {

        final ObjectMapper mapper = new ObjectMapper();

        Thread t = new Thread(new Runnable() {
            public void run() {

                try {

                    URL url = new URL("http://localhost:8080/testpatient");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    //conn.setRequestProperty("Accept", "application/json");

                    if (conn.getResponseCode() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                    }

                    BufferedReader buffer = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                    Patient patient = (Patient) mapper.readValue(buffer, Patient.class);

                    conn.disconnect();

                } catch (MalformedURLException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
}
