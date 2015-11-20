package at.tuwien.telemedizin.dermadoc.app.activites_fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.adapters.MyCasesPagerEnum;
import at.tuwien.telemedizin.dermadoc.app.comparators.CaseSortCategory;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.CaseStatus;
import at.tuwien.telemedizin.dermadoc.entities.Gender;
import at.tuwien.telemedizin.dermadoc.entities.GeoLocation;
import at.tuwien.telemedizin.dermadoc.entities.Patient;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CaseListFragment.OnCaseListEventListener{

    private List<Case> currentCaseList;
    private List<Case> closedCaseList;

    private CaseSortCategory caseListSortCategory; // set when a sort is executed

    // To hide the sort-menu-item whenever fragments are changed etc.
    private MenuItem sortMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // default content is case-list
        Fragment fragment = MyCasesFragment.newInstance();
        String title = getString(R.string.nav_my_cases);

        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.contentFrame,fragment);
            fragmentTransaction.commit();
            setTitle(title);
        }

        // TODO replace
        loadCaseLists();
    }

    /**
     * loads the case-list(s) from the server
     * TODO
     */
    private void loadCaseLists() {
        // TODO remove START - for testing purpose ---------------

        Patient patient = new Patient();
        patient.setId(1l);
        patient.setMail("mail@mail.at");
        patient.setPassword("no");
        patient.setName("Peter Hans Gruber dings Norbert");
        patient.setLocation(new GeoLocation("hier", 2.0, 2.0));

        patient.setSvnr("1212");
        patient.setGender(Gender.Female);
        patient.setBirthTime(Calendar.getInstance());


        long startNumber = 100000;

        currentCaseList = new ArrayList<Case>();
        Case testCase1 = new Case(startNumber+2045, patient, new GregorianCalendar());
        testCase1.setStatus(CaseStatus.Active);
        currentCaseList.add(testCase1);
        Case testCase2 = new Case(startNumber+451, patient, new GregorianCalendar());
        testCase2.setStatus(CaseStatus.LookingForPhysician);
        currentCaseList.add(testCase2);
        for (int i = 0; i < 5; i++) {
            Case testCaseA = new Case((startNumber+10 + i), patient, new GregorianCalendar());
            testCaseA.setStatus(CaseStatus.values()[i%3]); // 3 because 4 would be closed
            currentCaseList.add(testCaseA);
        }

        closedCaseList = new ArrayList<Case>();
        Case testCase3 = new Case(startNumber+4345, patient, new GregorianCalendar());
        testCase3.setStatus(CaseStatus.Closed);
        closedCaseList.add(testCase3);
        for (int i = 0; i < 5; i++) {
            Case testCaseB = new Case((startNumber+7645 + i), patient, new GregorianCalendar());
            testCaseB.setStatus(CaseStatus.values()[i%4]);
            closedCaseList.add(testCaseB);
        }
        // TODO remove END - for testing purpose ---------------
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        sortMenuItem = menu.findItem(R.id.action_sort);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getBaseContext(), "There will be a Settings-activity", Toast.LENGTH_LONG).show(); // TODO replace with real fragment/function
            return true;
        } else if (id == R.id.action_syncronize) {
            Toast.makeText(getBaseContext(), "Synchronisation with server coming soon!", Toast.LENGTH_LONG).show(); // TODO replace with real fragment/function
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        String title = getString(R.string.app_name);
        CharSequence oldTitle = getTitle();

        if (id == R.id.nav_my_cases) {
            fragment = MyCasesFragment.newInstance();
            title = getString(R.string.nav_my_cases);
            sortMenuItem.setVisible(true);

        } else if (id == R.id.nav_my_account) {
            fragment = DummyContentFragment.newInstance("My Account ... soon"); // TODO replace with real fragment/function
            title = getString(R.string.nav_my_account);
            sortMenuItem.setVisible(false);
        } else if (id == R.id.nav_help) {
            Toast.makeText(getBaseContext(), "There will be a Help-activity", Toast.LENGTH_LONG).show();
            // TODO not as fragment - implement as own activity
            title = oldTitle.toString();
        } else if (id == R.id.nav_logout) {
            Toast.makeText(getBaseContext(), "You are not even logged in yet!", Toast.LENGTH_LONG).show(); // TODO replace with real fragment/function
            title = oldTitle.toString();
        }

        // logout does not return a fragment != null
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.contentFrame,fragment);
            fragmentTransaction.commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        setTitle(title);

        return true;
    }

    @Override
    public List<Case> onListRequest(long listKey) {
        if (listKey == MyCasesPagerEnum.CURRENT.getKey()) {
            return currentCaseList;
        } else if (listKey == MyCasesPagerEnum.OLD.getKey()) {
            return closedCaseList;
        } else {
            // nothing matched ...
            return null;
        }
    }

    @Override
    public CaseSortCategory onCaseSortCategoryRequest() {
        return caseListSortCategory;
    }

    @Override
    public void onSettingNewCaseSortCategory(CaseSortCategory caseSortCategory) {
        this.caseListSortCategory = caseSortCategory;
    }

}
