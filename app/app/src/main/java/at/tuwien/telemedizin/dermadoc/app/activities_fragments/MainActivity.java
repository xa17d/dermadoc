package at.tuwien.telemedizin.dermadoc.app.activities_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case.NewCaseActivity;
import at.tuwien.telemedizin.dermadoc.app.adapters.MyCasesPagerEnum;
import at.tuwien.telemedizin.dermadoc.app.comparators.CaseSortCategory;
import at.tuwien.telemedizin.dermadoc.app.persistence.ContentProvider;
import at.tuwien.telemedizin.dermadoc.app.persistence.ContentProviderFactory;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.CaseStatus;
import at.tuwien.telemedizin.dermadoc.entities.Gender;
import at.tuwien.telemedizin.dermadoc.entities.GeoLocation;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CaseListFragment.OnCaseListEventListener{

    private List<Case> currentCaseList;
    private List<Case> closedCaseList;

    public static final String TOKEN_INTENT_KEY = MainActivity.class.getName() + "TOKEN_INTENT";
    public static final String TOKEN_TYPE_INTENT_KEY = MainActivity.class.getName() + "TOKEN_TYPE_INTENT";

    private AuthenticationToken authenticationToken;

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
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                // launch activity to create a new case
                Intent intent = new Intent(MainActivity.this, NewCaseActivity.class);
                startActivity(intent);
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

        Intent intent = getIntent();
        // get the AuthenticationToken
        String aToken = intent.getStringExtra(TOKEN_INTENT_KEY);
        String aTokenType = intent.getStringExtra(TOKEN_TYPE_INTENT_KEY);
        if (aToken != null) {
            authenticationToken = new AuthenticationToken();
            authenticationToken.setToken(aToken);
            authenticationToken.setType(aTokenType);
        }

        // TODO replace
        ContentProvider cP = ContentProviderFactory.getContentProvider();
        currentCaseList = cP.getCurrentCasesOfUser();
        closedCaseList = cP.getCurrentCasesOfUser(); // TODO for testing purpose - remove or replace
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
