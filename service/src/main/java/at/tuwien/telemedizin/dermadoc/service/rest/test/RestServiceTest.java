package at.tuwien.telemedizin.dermadoc.service.rest.test;

import at.tuwien.telemedizin.dermadoc.entities.Case;
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

    private static final String ID = "cases";

    public void getTestPatient() {

        final ObjectMapper mapper = new ObjectMapper();

        Thread t = new Thread(new Runnable() {
            public void run() {

                try {

                    URL url = new URL(" http://dermadoc.xa1.at:82/" + ID);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty ("Authorization", "Bearer test");
                    conn.setRequestMethod("GET");
                    //conn.setRequestProperty("Accept", "application/json");

                    if (conn.getResponseCode() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                    }

                    BufferedReader reader = new BufferedReader(new InputStreamReader((conn.getInputStream())));

                    StringBuilder builder = new StringBuilder();
                    String aux = "";

                    while ((aux = reader.readLine()) != null) {
                        builder.append(aux);
                    }

                    String text = builder.toString();

                    //Case aCase = (Case) mapper.readValue(reader, Case.class);

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
