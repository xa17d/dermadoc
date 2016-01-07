package at.tuwien.telemedizin.dermadoc.app.server_interface;

import android.util.Log;

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

import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.general_entities.Case;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Physician;
import at.tuwien.telemedizin.dermadoc.app.general_entities.User;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.app.general_entities.rest.AuthenticationData;
import at.tuwien.telemedizin.dermadoc.app.general_entities.rest.AuthenticationToken;
import at.tuwien.telemedizin.dermadoc.app.general_entities.rest.CaseDataList;
import at.tuwien.telemedizin.dermadoc.app.general_entities.rest.CaseList;
import at.tuwien.telemedizin.dermadoc.app.general_entities.rest.PhysicianList;

/**
 * Created by FAUser on 10.12.2015.
 */
public class RestServerInterface implements ServerInterface {

    public static final String LOG_TAG = RestServerInterface.class.getSimpleName();

    private String url;
    public static final String LOGIN_URL = "/login";
    public static final String GET_USER_URL = "/user";
    public static final String GET_CASES_URL = "/cases";
    public static final String GET_CASE_DATA_URL = "/data";
    public static final String GET_PHYSICIANS = "/physicians";
    public static final String AUTHORIZATION_HEADER_PREFIX = "Authorization";

    public static final String POST_CASE_URL = "/cases";
    public static final String POST_CASE_DATA_URL = "/data";

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

        ResponseEntity<User> responseEntity;
        try {
//            responseEntity = restTemplate.getForEntity(url + LOGIN_URL, entity, User.class);

            responseEntity = restTemplate.exchange(url + GET_USER_URL, HttpMethod.GET, getAuthGETEntity(), User.class);

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
        Log.d(LOG_TAG, "getCases()");
        // check if an authentication-token exists
        if (authToken == null || authToken.getToken() == null) {
            Log.d(LOG_TAG, "No Authentication Token - ABORT!");
            // TODO error? msg?
            return null;
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


        ResponseEntity<CaseList> responseEntity;

        try {

            responseEntity = restTemplate.exchange(url + GET_CASES_URL, HttpMethod.GET, getAuthGETEntity(), CaseList.class);

            if (responseEntity!=null) {
                Log.d(LOG_TAG, "responseEntity!=null " + responseEntity.getStatusCode());
                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    Log.d(LOG_TAG, "CaseDataList != null:" + (responseEntity.getBody() != null));
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

    private HttpEntity<String> getAuthGETEntity() {
        HttpEntity<String> entity = new HttpEntity<String>(getAuthHeaders());
        return entity;
    }

    private HttpHeaders getAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String headerVal = authToken.toString();
        Log.d(LOG_TAG, "Auth Header val: " + headerVal);
        headers.set(AUTHORIZATION_HEADER_PREFIX, headerVal);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Override
    public Case getCase(long id) {
        Log.d(LOG_TAG, "getCase(" + id + ")");
        // check if an authentication-token exists
        if (authToken == null || authToken.getToken() == null) {
            Log.d(LOG_TAG, "No Authentication Token - ABORT!");
            // TODO error? msg?
            return null;
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

//        // connection properties for the id
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl()

        ResponseEntity<Case> responseEntity;

        try {

            String urlStr = url + GET_CASES_URL + "/" + id;

//            ResponseEntity<String> responseEntityStr = restTemplate.exchange(urlStr, HttpMethod.GET, getAuthGETEntity(), String.class);
//
//            // TODO remove
//            if (responseEntityStr!=null) {
//                Log.d(LOG_TAG, "responseEntityStr!=null \n" + responseEntityStr.getBody());
//
//
//            } else {
//                Log.d(LOG_TAG, "responseEntityStr==null ");
//
//            }

            responseEntity = restTemplate.exchange(urlStr, HttpMethod.GET, getAuthGETEntity(), Case.class);

            if (responseEntity!=null) {
                Log.d(LOG_TAG, "responseEntity!=null " + responseEntity.getStatusCode());
                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    Log.d(LOG_TAG, "Case != null:" + (responseEntity.getBody() != null));
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
    public List<CaseData> getCaseData(long caseId) {
        Log.d(LOG_TAG, "getCaseData(" + caseId + ")");
        // check if an authentication-token exists
        if (authToken == null || authToken.getToken() == null) {
            Log.d(LOG_TAG, "No Authentication Token - ABORT!");
            // TODO error? msg?
            return null;
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ResponseEntity<CaseDataList> responseEntity;

        try {

            String urlStr = url + GET_CASES_URL + "/" + caseId + GET_CASE_DATA_URL;
            responseEntity = restTemplate.exchange(urlStr, HttpMethod.GET, getAuthGETEntity(), CaseDataList.class);

            if (responseEntity!=null) {
                Log.d(LOG_TAG, "responseEntity!=null " + responseEntity.getStatusCode());
                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    Log.d(LOG_TAG, "CaseDataList != null:" + (responseEntity.getBody() != null));
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
    public Case createCase(Case caseItem) {
        Log.d(LOG_TAG, "createCase()");
        // check if an authentication-token exists
        if (authToken == null || authToken.getToken() == null) {
            Log.d(LOG_TAG, "No Authentication Token - ABORT!");
            // TODO error? msg?
            return null;
        }

        if (caseItem == null) {
            Log.d(LOG_TAG, "Case is null! - ABORT");
            // TODO error? msg?
            return null;
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        ResponseEntity<Case> responseEntity;

        HttpEntity<Case> requestEntity = new HttpEntity<>(caseItem, getAuthHeaders());

        try {

            String urlStr = url + POST_CASE_URL;
            responseEntity = restTemplate.exchange(urlStr, HttpMethod.POST, requestEntity, Case.class);

            if (responseEntity!=null) {
                Log.d(LOG_TAG, "responseEntity!=null " + responseEntity.getStatusCode());
                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    Log.d(LOG_TAG, "caseResult != null:" + (responseEntity.getBody() != null));
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
    public CaseData addCaseData(CaseData caseData, long caseId) {
        Log.d(LOG_TAG, "addCaseData() for case " + caseId);
        // check if an authentication-token exists
        if (authToken == null || authToken.getToken() == null) {
            Log.d(LOG_TAG, "No Authentication Token - ABORT!");
            // TODO error? msg?
            return null;
        }

        if (caseData == null) {
            Log.d(LOG_TAG, "CaseData is null! - ABORT");
            // TODO error? msg?
            return null;
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        ResponseEntity<CaseData> responseEntity;

        HttpEntity<CaseData> requestEntity = new HttpEntity<>(caseData, getAuthHeaders());

        try {

            String urlStr = url + POST_CASE_DATA_URL;
            responseEntity = restTemplate.exchange(urlStr, HttpMethod.POST, requestEntity, CaseData.class);

            if (responseEntity != null) {
                Log.d(LOG_TAG, "responseEntity!=null " + responseEntity.getStatusCode());
                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    Log.d(LOG_TAG, "caseResult != null:" + (responseEntity.getBody() != null));
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
    public List<Physician> getPhysicians() {
        Log.d(LOG_TAG, "getPhysicians()");
        // check if an authentication-token exists
        if (authToken == null || authToken.getToken() == null) {
            Log.d(LOG_TAG, "No Authentication Token - ABORT!");
            // TODO error? msg?
            return null;
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        ResponseEntity<PhysicianList> responseEntity;

        try {

            String urlStr = url + GET_PHYSICIANS;
            responseEntity = restTemplate.exchange(urlStr, HttpMethod.GET, getAuthGETEntity(), PhysicianList.class);

            if (responseEntity!=null) {
                Log.d(LOG_TAG, "responseEntity!=null " + responseEntity.getStatusCode());
                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    Log.d(LOG_TAG, "PhysicianList != null:" + (responseEntity.getBody() != null));
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
}
