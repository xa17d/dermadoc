package at.tuwien.telemedizin.dermadoc.app.activites_fragments.case_specific;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.activites_fragments.DummyContentFragment;
import at.tuwien.telemedizin.dermadoc.app.entities.CaseEntity;
import at.tuwien.telemedizin.dermadoc.app.helper.FormatHelper;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import at.tuwien.telemedizin.dermadoc.entities.Physician;

public class CaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CaseDataCallbackInterface {

    public static final String LOG_TAG = CaseActivity.class.getSimpleName();

    private Case caseItem;

    private NavigationView navigationView;
    private TextView navHeaderMainTextView;
    private TextView navHeaderPatientTextView;
    private TextView navHeaderStatusTextView;
    private TextView navHeaderPhysicianTextView;
    private TextView navHeaderDateOfCreationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_layout);
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

        navigationView = (NavigationView) findViewById(R.id.nav_view_case);
        navigationView.setNavigationItemSelectedListener(this);

        // default content is case-list
        Fragment fragment = CaseOverviewFragment.newInstance(); // TODO replace with real fragment/function
        String title = getString(R.string.nav_case_overview);

        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.contentFrame,fragment);
            fragmentTransaction.commit();
            setTitle(title);
        }

        // load case
        Intent intent = getIntent();
        Parcelable caseParcel = intent.getParcelableExtra(CaseEntity.INTENT_KEY);
        if (caseParcel != null) {
//            Log.d(LOG_TAG, "case-parcelable in intent != null -> casting");
            caseItem = (Case) caseParcel;
        } else {
//            Log.d(LOG_TAG, "case-parcelable == null -> exiting activity");
            Toast.makeText(getBaseContext(), getString(R.string.msg_err_item_not_received), Toast.LENGTH_LONG).show();
            this.finish();
        }

        Log.d(LOG_TAG, "caseItem == null? " + (caseItem == null));
        if (caseItem != null) {
            Log.d(LOG_TAG, "case: " + caseItem.getStatus()
                    + ", patient: " + caseItem.getPatient().getName());
        }

        // set up navigation drawer - header
        setUpNavigationViewHeader();





    }

    /**
     * fills the header of the navigation-drawer with data
     */
    private void setUpNavigationViewHeader() {
        navHeaderMainTextView = (TextView)
                navigationView.getHeaderView(0).findViewById(R.id.case_nav_header_primary_text);
        navHeaderMainTextView.setText(getString(R.string.nav_case) + ": " + caseItem.getId()); // TODO name or so

        // TODO patient info is less interesting for the patient -> remove
        navHeaderPatientTextView = (TextView)
                navigationView.getHeaderView(0).findViewById(R.id.case_overview_patient);
        Patient patient = caseItem.getPatient();
        navHeaderPatientTextView.setText(patient != null ? patient.getName() : "No Info");

        navHeaderPhysicianTextView = (TextView)
                navigationView.getHeaderView(0).findViewById(R.id.case_overview_physician);
        Physician physician = caseItem.getPhysician();
        navHeaderPhysicianTextView.setText(physician != null ?
                physician.getName() : getString(R.string.msg_no_physician_info_found));

        navHeaderStatusTextView = (TextView)
                navigationView.getHeaderView(0).findViewById(R.id.case_overview_status);
        navHeaderStatusTextView.setText("" + caseItem.getStatus());

        navHeaderDateOfCreationTextView = (TextView)
                navigationView.getHeaderView(0).findViewById(R.id.case_overview_date_of_creation);
        String dateOfCreationStr = FormatHelper.calendarToDateFormatString(caseItem.getCreated(), this);
        navHeaderDateOfCreationTextView.setText(dateOfCreationStr);
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
        getMenuInflater().inflate(R.menu.case_menu, menu);

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
        String title = "" + caseItem.getId(); // TODO change to name or something
        CharSequence oldTitle = getTitle();

        if (id == R.id.nav_case_overview) {
            fragment = DummyContentFragment.newInstance("Overview"); // TODO replace with real fragment/function
            title = getString(R.string.nav_case_overview);

        } else if (id == R.id.nav_case_advice) {
            fragment = DummyContentFragment.newInstance("Advice"); // TODO replace with real fragment/function
            title = getString(R.string.nav_case_advice);

        } else if (id == R.id.nav_case_diagnoses) {
            fragment = DummyContentFragment.newInstance("Diagnoses"); // TODO replace with real fragment/function
            title = getString(R.string.nav_case_diagnoses);
        } else if (id == R.id.nav_case_etc) {
            fragment = DummyContentFragment.newInstance("etc..."); // TODO replace with real fragment/function
            title = "ETC";
        } else if (id == R.id.nav_back_to_main) {
            // finish this activity
            this.finish();
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
    public Case getCase() {
        return caseItem;
    }
}
