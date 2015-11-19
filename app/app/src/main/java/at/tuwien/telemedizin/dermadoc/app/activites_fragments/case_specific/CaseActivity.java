package at.tuwien.telemedizin.dermadoc.app.activites_fragments.case_specific;

import android.os.Bundle;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.activites_fragments.CaseListFragment;
import at.tuwien.telemedizin.dermadoc.app.activites_fragments.DummyContentFragment;
import at.tuwien.telemedizin.dermadoc.app.adapters.MyCasesPagerEnum;
import at.tuwien.telemedizin.dermadoc.app.comparators.CaseSortCategory;
import at.tuwien.telemedizin.dermadoc.entities.Case;

public class CaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Case caseItem;


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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // default content is case-list
        Fragment fragment = DummyContentFragment.newInstance("Dummy");
        String title = "Case A"; // TODO

        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.contentFrame,fragment);
            fragmentTransaction.commit();
            setTitle(title);
        }

        // load case TODO
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
        String title = "NAME"; // TODO change to name or something
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



}
