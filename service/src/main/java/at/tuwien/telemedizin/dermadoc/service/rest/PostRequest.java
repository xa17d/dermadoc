package at.tuwien.telemedizin.dermadoc.service.rest;

import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import at.tuwien.telemedizin.dermadoc.entities.rest.Error;
import at.tuwien.telemedizin.dermadoc.service.rest.listener.RestListener;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Lucas on 27.11.2015.
 */
public class PostRequest<Trequest, Tresponse> {

    private ObjectMapper mapper = new ObjectMapper();

    private AuthenticationToken token;
    private Class<? extends Tresponse> typeParameterClassResponse;

    protected PostRequest(AuthenticationToken token, Class<? extends Tresponse> typeParameterClassResponse) {

        this.token = token;
        this.typeParameterClassResponse = typeParameterClassResponse;
    }

    protected void post(String urlString, RestListener<Tresponse> listener, Trequest requestObject) {

        try {
            URL url = new URL(urlString);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            if(token != null) {
                httpConnection.setRequestProperty("Authorization", token.toString());
            }
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "text/json");
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);

            DataOutputStream outStream = new DataOutputStream(httpConnection.getOutputStream());
            mapper.writeValue(outStream, requestObject);
            outStream.flush();
            outStream.close();

            if (httpConnection.getResponseCode() >= 200 && httpConnection.getResponseCode() < 300 ) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                Tresponse response = mapper.readValue(reader, typeParameterClassResponse);

                listener.onRequestComplete(response);
            }
            else {
                listener.onError(new Error(httpConnection.getResponseCode() + " - " + httpConnection.getResponseMessage()));
            }

            httpConnection.disconnect();

        } catch (MalformedURLException e) {
            listener.onError(new Error(e));
            e.printStackTrace();
        } catch (IOException e) {
            listener.onError(new Error(e));
        }
    }
}
