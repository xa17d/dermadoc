package at.tuwien.telemedizin.dermadoc.app.server_interface;

import android.util.Log;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.general_entities.Case;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Patient;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Physician;
import at.tuwien.telemedizin.dermadoc.app.general_entities.User;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.app.general_entities.rest.AuthenticationData;
import at.tuwien.telemedizin.dermadoc.app.general_entities.rest.AuthenticationToken;

/**
 * Created by FAUser on 10.12.2015.
 */
public class RestServerInterface implements ServerInterface {

    public static final String LOG_TAG = RestServerInterface.class.getSimpleName();

    private String url;
    public static final String LOGIN_URL = "/login";
    public static final String GET_USER_URL = "/user";
    public static final String AUTHORIZATION_HEADER_PREFIX = "Authorization";

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
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

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
            authToken = aToken;
            return true;
        }

        return false;
    }

    @Override
    public User getUser() {
        Log.d(LOG_TAG, "getUser()");
        // check if an authentication-token exists
        if (authToken == null || authToken.getToken() == null) {
            Log.d(LOG_TAG, "No Authentication Token - ABORT!");
            // TODO error? msg?
            return null;
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String headerVal = authToken.toString();
        Log.d(LOG_TAG, "Auth Header val: " + headerVal);
        headers.set(AUTHORIZATION_HEADER_PREFIX, headerVal);

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<User> responseEntity;
        Log.d(LOG_TAG, "send Request " + entity.getHeaders().toString());
        try {
//            responseEntity = restTemplate.getForEntity(url + LOGIN_URL, entity, User.class);

            responseEntity = restTemplate.exchange(url + GET_USER_URL, HttpMethod.GET, entity, User.class);

            if (responseEntity!=null) {
                Log.d(LOG_TAG, "responseEntity!=null " + responseEntity.getStatusCode());
                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    Log.d(LOG_TAG, "User: " + responseEntity.getBody().getName());
                    return responseEntity.getBody();
                }

            } else {
                Log.d(LOG_TAG, "responseEntity==null ");
                return null;
            }


        } catch (HttpClientErrorException e) {
            Log.e(LOG_TAG, "exception: " + e.getLocalizedMessage(), e);
            return null;
        } catch (ResourceAccessException e) {
            Log.e(LOG_TAG, "exception: " + e.getLocalizedMessage(), e);
            return null;
        } catch (Exception e) {
            // catch all other exception to prevent the app from crashing
            Log.e(LOG_TAG, "exception: " + e.getLocalizedMessage(), e);
            return null;
        }


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
