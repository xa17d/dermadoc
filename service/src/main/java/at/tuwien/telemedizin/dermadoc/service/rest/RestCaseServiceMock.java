package at.tuwien.telemedizin.dermadoc.service.rest;

import at.tuwien.telemedizin.dermadoc.entities.*;
import at.tuwien.telemedizin.dermadoc.entities.casedata.*;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import at.tuwien.telemedizin.dermadoc.entities.rest.CaseList;
import at.tuwien.telemedizin.dermadoc.service.rest.listener.DermadocNotificationHandler;
import at.tuwien.telemedizin.dermadoc.service.rest.listener.RestListener;
import javafx.application.Platform;

import java.util.*;

/**
 * Created by Lucas on 26.11.2015.
 */
public class RestCaseServiceMock implements IRestCaseService {

    private CaseList openCaseList = new CaseList();
    private CaseList caseList = new CaseList();
    private List<CaseData> caseDataList = new ArrayList<>();
    private Physician physician;

    public RestCaseServiceMock(AuthenticationToken token) {

        physician = new Physician();
        physician.setId(-1l);
        physician.setName("Lucas Dobler");

        Patient p1 = new Patient();
        p1.setName("Daniel Gehrer");
        p1.setSvnr("1234");
        p1.setGender(Gender.Male);
        p1.setBirthTime(Calendar.getInstance());

        Patient p2 = new Patient();
        p2.setName("Lilly Treml");
        p2.setSvnr("2345");
        p2.setGender(Gender.Female);
        p2.setBirthTime(Calendar.getInstance());

        Patient p3 = new Patient();
        p3.setName("Fabian Filip");
        p3.setSvnr("3456");
        p3.setGender(Gender.Male);
        p3.setBirthTime(Calendar.getInstance());

        Case c11 = new Case(-1l, p1, Calendar.getInstance());
        c11.setName("HELP!!");
        Case c12 = new Case(-1l, p1, Calendar.getInstance());
        c12.setName("OMG!!");
        Case c13 = new Case(-1l, p1, Calendar.getInstance());
        c13.setName("I'm a girl!!");
        caseList.add(c11);
        caseList.add(c12);
        caseList.add(c13);

        Case c21 = new Case(-1l, p2, Calendar.getInstance());
        c21.setName("Rash on Hand!!");
        Case c22 = new Case(-1l, p2, Calendar.getInstance());
        c22.setName("What shall i do??!!");
        Case c23 = new Case(-1l, p2, Calendar.getInstance());
        c23.setName("I have a question!!");
        caseList.add(c21);
        caseList.add(c22);
        caseList.add(c23);

        Case c31 = new Case(-1l, p3, Calendar.getInstance());
        c31.setName("rash on upper arm");
        caseList.add(c31);


        Anamnesis anamnesis = new Anamnesis(-1l, Calendar.getInstance(), p1, "message", new ArrayList<>(Arrays.asList(new Medication[]{ new Medication("Medication 1") })));
        TextMessage textMessage1 = new TextMessage(-2l, Calendar.getInstance(), p1, "Dear Mister Doc, i really need your help!");
        TextMessage textMessage2 = new TextMessage(-3l, Calendar.getInstance(), new Physician(), "Please send me som images");
        TextMessage textMessage3 = new TextMessage(-4l, Calendar.getInstance(), p1, "Sorry, my phone doesn't have a cam... :/");
        Diagnosis diagnosis = new Diagnosis(-5l, Calendar.getInstance(), new Physician(), "R12.1");
        Advice advice = new Advice(-6l, Calendar.getInstance(), new Physician(), "i'll prescribe you a cream for your rush... if it's not getting smaller in the next 3 weeks, please contact me again.", new ArrayList<>(Arrays.asList(new Medication[]{ new Medication("Medication 1") })));

        //TODO add more
        //caseDataList.add(anamnesis);
        caseDataList.add(textMessage1);
        caseDataList.add(textMessage2);
        caseDataList.add(textMessage3);
        //caseDataList.add(diagnosis);
        //caseDataList.add(advice);

        for (int i = 0; i < 20; i++) {

            Patient p = new Patient();
            p.setId(142 + i * (i % 5 ) * 2 + (i % 2) + (i * i % 8) + 5 * i);
            p.setName("abc");
            p.setGender(Gender.Male);
            p.setBirthTime(new GregorianCalendar(1990, 9, 18));
            Case mockCase = new Case(-1l, p, new GregorianCalendar(2015, 11, (int)Math.floor(i / 2) + 10));
            mockCase.setName("case " + i);
            mockCase.setStatus(CaseStatus.WaitingForAccept);

            openCaseList.add(mockCase);
        }
    }


    @Override
    public void getOpenCases(RestListener<CaseList> listener) {

        listener.onRequestComplete(openCaseList);
    }

    @Override
    public void getAllCases(RestListener<CaseList> listener) {

        listener.onRequestComplete(caseList);
    }

    @Override
    public void getCaseData(RestListener<List<CaseData>> listener, Case aCase) {

        listener.onRequestComplete(caseDataList);
    }

    @Override
    public void getUser(RestListener<User> listener) {

        listener.onRequestComplete(physician);
    }

    @Override
    public void setNotificationHandler(DermadocNotificationHandler handler) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    try {
                        Thread.sleep(8000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Notification n1 = new Notification();
                    n1.setText("Daniel Gehrer sent you a new message!");
                    n1.setCaseId(1000l);
                    Notification n2 = new Notification();
                    n2.setText("Lilly Treml sent you a new message!");
                    n2.setCaseId(1001l);

                    Patient p1 = new Patient();
                    p1.setName("Daniel Gehrer");
                    p1.setGender(Gender.Male);
                    p1.setBirthTime(Calendar.getInstance());
                    p1.setSvnr("1234");
                    Case c1 = new Case(1000l, p1, Calendar.getInstance());
                    c1.setName("a new case!!");
                    caseList.add(c1);

                    Patient p2 = new Patient();
                    p2.setName("Lilly Treml");
                    p2.setGender(Gender.Male);
                    p2.setBirthTime(Calendar.getInstance());
                    p2.setSvnr("1234");
                    Case c2 = new Case(1001l, p2, Calendar.getInstance());
                    c2.setName("oh lala!!");
                    caseList.add(c2);

                    List<Notification> list = new ArrayList<>();
                    if(i % 2 == 0)
                        list.add(n1);
                    else
                        list.add(n2);

                    handler.onNewNotifications(list);
                }
            }
        });
        t.start();
    }

    @Override
    public void postAcceptCase(RestListener<Void> listener, Case aCase) {

        listener.onRequestComplete(null);
    }

    @Override
    public void postCaseData(RestListener<CaseData> listener, Case aCase, CaseData caseData) {

        //caseDataList.add(caseData);
        listener.onRequestComplete(caseData);
    }

    @Override
    public void postNewCase(RestListener<Case> listener, Case aCase) {

        caseList.add(aCase);
        listener.onRequestComplete(aCase);
    }
}
