package at.tuwien.telemedizin.dermadoc.service.rest.test;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import at.tuwien.telemedizin.dermadoc.service.rest.IRestCaseService;
import at.tuwien.telemedizin.dermadoc.service.rest.IRestLoginService;
import at.tuwien.telemedizin.dermadoc.service.rest.RestCaseService;
import at.tuwien.telemedizin.dermadoc.service.rest.RestLoginService;
import at.tuwien.telemedizin.dermadoc.service.rest.listener.RestListener;

/**
 * Created by Lucas on 25.11.2015.
 */
public class RestTest {

    public static void main(String[] args) {

        //TEST 1 - POST LOGIN
        AuthenticationData data = new AuthenticationData("p", "p");
        IRestLoginService loginService = new RestLoginService();
        loginService.postLogin(new RestListener<AuthenticationToken>() {
            @Override
            public void onRequestComplete(AuthenticationToken requestResult) {
                System.out.println(requestResult);
                System.out.println(requestResult.getToken().equals("Bearer test2"));
            }

            @Override
            public void onError(Error error) {
                System.out.println(error.getMessage());
            }
        }, data);

        //set authentification data
        AuthenticationToken token = new AuthenticationToken();
        token.setToken("Bearer test");
        IRestCaseService caseService = new RestCaseService(token);

        //TEST 2 - GET CASE BY ID
        long caseId = 100l;
        caseService.getCaseById(new RestListener<Case>() {
            @Override
            public void onRequestComplete(Case requestResult) {
                System.out.println(requestResult);
                System.out.println(requestResult.getId() == caseId);
            }

            @Override
            public void onError(Error error) {
                System.out.println(error.getMessage());
            }
        }, caseId);


        //TEST 3 - POST NEW CASE
        Case newCase = new Case();
        newCase.setName("a new case");
        caseService.postNewCase(new RestListener<Case>() {
            @Override
            public void onRequestComplete(Case requestResult)  {
                System.out.println(requestResult);
                System.out.println(requestResult.getName().equals(newCase.getName()));
            }

            @Override
            public void onError(Error error) {
                System.out.println(error.getMessage());
            }
        }, newCase);
    }
}
