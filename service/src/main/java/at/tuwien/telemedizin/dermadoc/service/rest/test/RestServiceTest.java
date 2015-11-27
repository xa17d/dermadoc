package at.tuwien.telemedizin.dermadoc.service.rest.test;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.User;
import at.tuwien.telemedizin.dermadoc.entities.rest.CaseList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

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

    //private static final String ID = "cases";
    private static final String ID = "user";

    public void getTestPatient() {

        final XmlMapper mapper = new XmlMapper();

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
                    Physician user = (Physician) mapper.readValue(reader, Physician.class);
                    //CaseList caselist = (CaseList) mapper.readValue(reader, CaseList.class);

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
