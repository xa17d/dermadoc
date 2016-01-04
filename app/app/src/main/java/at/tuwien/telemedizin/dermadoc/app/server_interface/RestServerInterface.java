package at.tuwien.telemedizin.dermadoc.app.server_interface;

import android.util.Log;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;

/**
 * Created by FAUser on 10.12.2015.
 */
public class RestServerInterface implements ServerInterface {

    public static final String LOG_TAG = RestServerInterface.class.getSimpleName();

    private String url;
    public static final String LOGIN_URL = "/login";
    public static final String AUTHORIZATION_HEADER_PREFIX = "Authorization:";

    public static final String HEADER_AUTH_TYPE_ELEMENT = "type";
    public static final String HEADER_AUTH_TOKEN_ELEMENT = "token";

    private AuthenticationToken authToken;

    public boolean hasAuthToken() {
        return authToken != null && authToken.getToken() != null;
    }

    public RestServerInterface(String url) {
        this.url = url;
    }

    @Override
    public boolean login(AuthenticationData authenticationData) {

        RestTemplate restTemplate = new RestTemplate();

        AuthenticationToken aToken = null;
        Log.d(LOG_TAG, "send Request");
        try {
            aToken = restTemplate.postForObject(url + LOGIN_URL, authenticationData, AuthenticationToken.class);

        } catch (HttpClientErrorException e) {
            Log.e(LOG_TAG, "exception: " + e.getLocalizedMessage(), e);
            return false;
        } catch (ResourceAccessException e) {
            Log.e(LOG_TAG, "exception: " + e.getLocalizedMessage(), e);
            return false;
        } catch (Exception e) {
            // catch all other exception to prevent the app from crashing
            Log.e(LOG_TAG, "exception: " + e.getLocalizedMessage(), e);
            return false;
        }

        // check token
        if (aToken != null && aToken.getToken() != null) {
            return true;
        }

        return false;
    }

    @Override
    public Patient getUser() {
        Log.d(LOG_TAG, "getUser()");
        // check if an authentication-token exists
        if (authToken == null || authToken.getToken() == null) {
            Log.d(LOG_TAG, "No Authentication Token - ABORT!");
            // TODO error? msg?
            return null;
        }

//        RestTemplate restTemplate = new RestTemplate();
//        // setting up the authentication-token-header
//        String authHeader = AUTHORIZATION_HEADER_PREFIX
//                + " " + authToken.getType()
//                + " " + authToken.getToken();
//        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.setContentType(MediaType.APPLICATION_XHTML_XML);
//        requestHeaders.add()
//        HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
//        request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
//
////        header.setContentType(MediaType.APPLICATION_XHTML_XML);
////        header.add(HEADER_AUTH_TYPE_ELEMENT, authToken.getType());
////        header.add(HEADER_AUTH_TOKEN_ELEMENT, authToken.getToken());
//
//
//
//        Log.d(LOG_TAG, "send Request");
//        try {
//            aToken = restTemplate.postForObject(url + LOGIN_URL, authenticationData, AuthenticationToken.class);
//
//        } catch (HttpClientErrorException e) {
//            Log.e(LOG_TAG, "exception: " + e.getLocalizedMessage(), e);
//            return null;
//        } catch (ResourceAccessException e) {
//            Log.e(LOG_TAG, "exception: " + e.getLocalizedMessage(), e);
//            return null;
//        } catch (Exception e) {
//            // catch all other exception to prevent the app from crashing
//            Log.e(LOG_TAG, "exception: " + e.getLocalizedMessage(), e);
//            return null;
//        }


        // TODO
        return null;
    }

    @Override
    public List<Case> getCases() {
        return null;
    }

    @Override
    public Case getCase(long id) {
        return null;
    }

    @Override
    public Case createCase() {
        return null;
    }

    @Override
    public CaseData addCaseData(CaseData caseData, long caseId) {
        return null;
    }

    @Override
    public List<Physician> getPhysicians() {
        return null;
    }
}
