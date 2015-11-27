package at.tuwien.telemedizin.dermadoc.service.rest.requests;

import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import at.tuwien.telemedizin.dermadoc.service.rest.listener.RestListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.TypeVariable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Lucas on 27.11.2015.
 */
public class GetRequest<Tvalue> {

    ObjectMapper mapper = new XmlMapper();

    final Class<? extends Tvalue> typeParameterClass;

    public GetRequest(AuthenticationToken token, Class<? extends Tvalue> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public void get(String urlString, RestListener<Tvalue> listener) {

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //TODO insert token
            conn.setRequestProperty ("Authorization", "Bearer test");
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() < 200 || conn.getResponseCode() >= 300 ) {
                listener.onError(new Error(conn.getResponseCode() + " - " + conn.getResponseMessage()));
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            Tvalue value = mapper.readValue(reader, typeParameterClass);

            listener.onRequestComplete(value);
            conn.disconnect();

        } catch (MalformedURLException e) {
            listener.onError(new Error(e));
            e.printStackTrace();
        } catch (IOException e) {
            listener.onError(new Error(e));
        }
    }
}
