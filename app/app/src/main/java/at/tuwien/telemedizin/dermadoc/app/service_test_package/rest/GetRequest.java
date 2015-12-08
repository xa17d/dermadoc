package at.tuwien.telemedizin.dermadoc.app.service_test_package.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import at.tuwien.telemedizin.dermadoc.entities.rest.Error;
import at.tuwien.telemedizin.dermadoc.app.service_test_package.rest.listener.RestListener;

/**
 * Created by Lucas on 27.11.2015.
 */
public class GetRequest<Tresponse> {

    private ObjectMapper mapper = new XmlMapper();

    private AuthenticationToken token;
    private Class<? extends Tresponse> typeParameterClass;

    protected GetRequest(AuthenticationToken token, Class<? extends Tresponse> typeParameterClass) {

        this.token = token;
        this.typeParameterClass = typeParameterClass;
    }

    protected void get(String urlString, RestListener<Tresponse> listener) {

        try {
            URL url = new URL(urlString);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            if(token != null) {
                httpConnection.setRequestProperty("Authorization", token.toString());
            }
            httpConnection.setRequestMethod("GET");

            if (httpConnection.getResponseCode() >= 200 || httpConnection.getResponseCode() < 300 ) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                Tresponse response = mapper.readValue(reader, typeParameterClass);

                listener.onRequestComplete(response);
            }
            else {
                listener.onError(new Error(httpConnection.getResponseCode() + " - " + httpConnection.getResponseMessage()));
            }

            httpConnection.disconnect();

        } catch (MalformedURLException e) {
            listener.onError(new Error(e));
        } catch (IOException e) {
            listener.onError(new Error(e));
        }
    }
}
