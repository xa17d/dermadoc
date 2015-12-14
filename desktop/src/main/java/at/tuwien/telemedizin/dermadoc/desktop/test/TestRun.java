package at.tuwien.telemedizin.dermadoc.desktop.test;

import at.tuwien.telemedizin.dermadoc.desktop.service.CaseService;
import at.tuwien.telemedizin.dermadoc.desktop.service.ICaseService;
import at.tuwien.telemedizin.dermadoc.desktop.service.ILoginService;
import at.tuwien.telemedizin.dermadoc.desktop.service.LoginService;
import at.tuwien.telemedizin.dermadoc.desktop.service.dto.AuthenticationListener;
import at.tuwien.telemedizin.dermadoc.entities.*;
import at.tuwien.telemedizin.dermadoc.entities.casedata.*;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import at.tuwien.telemedizin.dermadoc.entities.rest.Error;
import at.tuwien.telemedizin.dermadoc.service.rest.IRestCaseService;
import at.tuwien.telemedizin.dermadoc.service.rest.RestCaseService;
import at.tuwien.telemedizin.dermadoc.service.rest.listener.RestListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Lucas on 03.12.2015.
 */
public class TestRun {

    private IRestCaseService service;
    private Case aCase;
    private Patient p;

    public static void main(String[] args) {

        TestRun testRun = new TestRun();
        testRun.run();
    }

    private void run() {

        ILoginService login = new LoginService();
        login.login(new AuthenticationListener() {
            @Override
            public void onAuthenticationComplete(AuthenticationToken token) {

                p = new Patient();
                p.setGender(Gender.Male);
                p.setName("John DoeDoe");
                p.setSvnr("123456789");

                Case aCase = new Case();
                aCase.setName("rush on upper leg");
                aCase.setPatient(p);
                aCase.setCreated(Calendar.getInstance());

                try {
                    service = new RestCaseService(token);

                    Thread.sleep(4000);

                    service.postNewCase(nullListenerC, aCase);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAuthenticationError(Error error) {

            }
        }, new AuthenticationData("p", "p"));
    }

    private void createCaseData() {

        List<AnamnesisQuestion> questions = new ArrayList<>();
        AnamnesisQuestionText q1 = new AnamnesisQuestionText();
        q1.setQuestion("question 1");
        q1.setAnswer("text answer 1");
        AnamnesisQuestionText q2 = new AnamnesisQuestionText();
        q2.setQuestion("question 2");
        q2.setAnswer("text answer 2");
        AnamnesisQuestionText q3 = new AnamnesisQuestionText();
        q3.setQuestion("question 3");
        q3.setAnswer("text answer 3");
        AnamnesisQuestionText q4 = new AnamnesisQuestionText();
        q4.setQuestion("question 4");
        q4.setAnswer("text answer 4");
        AnamnesisQuestionBool q5 = new AnamnesisQuestionBool();
        q5.setQuestion("question 5");
        q5.setAnswer(true);
        AnamnesisQuestionBool q6 = new AnamnesisQuestionBool();
        q6.setQuestion("question 6");
        q6.setAnswer(false);
        AnamnesisQuestionBool q7 = new AnamnesisQuestionBool();
        q7.setQuestion("question 7");
        q7.setAnswer(true);

        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        questions.add(q4);
        questions.add(q5);
        questions.add(q6);
        questions.add(q7);

        CaseData anamnesis = new Anamnesis(-1l, Calendar.getInstance(), p, "this is my anamnesis", questions);
        CaseData text = new TextMessage(-2l, Calendar.getInstance(), p, "please help me!!!");
        CaseData text2 = new TextMessage(-3l, Calendar.getInstance(), p, "thank you :)");

        try {
            Thread.sleep(1000);

            service.postCaseData(nullListenerCd, aCase, anamnesis);

            Thread.sleep(3000);

            service.postCaseData(nullListenerCd, aCase, text);

            Thread.sleep(10000);

            service.postCaseData(nullListenerCd, aCase, text2);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private RestListener<Case> nullListenerC = new RestListener<Case>() {
        @Override
        public void onRequestComplete(Case requestResult) {
            aCase = requestResult;
            createCaseData();
        }

        @Override
        public void onError(Error error) {

        }
    };

    private RestListener<CaseData> nullListenerCd = new RestListener<CaseData>() {
        @Override
        public void onRequestComplete(CaseData requestResult) {

        }

        @Override
        public void onError(Error error) {

        }
    };
}
