package at.tuwien.telemedizin.dermadoc.app.activities_fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case.NewCaseActivity;
import at.tuwien.telemedizin.dermadoc.app.adapters.MyCasesPagerEnum;
import at.tuwien.telemedizin.dermadoc.app.comparators.CaseSortCategory;
import at.tuwien.telemedizin.dermadoc.app.helper.ToStringHelper;
import at.tuwien.telemedizin.dermadoc.app.persistence.ContentProvider;
import at.tuwien.telemedizin.dermadoc.app.persistence.ContentProviderFactory;
import at.tuwien.telemedizin.dermadoc.app.server_interface.RestServerInterface;
import at.tuwien.telemedizin.dermadoc.app.server_interface.ServerInterface;
import at.tuwien.telemedizin.dermadoc.app.server_interface.ServerInterfaceFactory;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.CaseStatus;
import at.tuwien.telemedizin.dermadoc.entities.Gender;
import at.tuwien.telemedizin.dermadoc.entities.GeoLocation;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.CaseParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.PatientParc;
import at.tuwien.telemedizin.dermadoc.app.persistence.ContentProvider;
import at.tuwien.telemedizin.dermadoc.app.persistence.ContentProviderFactory;
import at.tuwien.telemedizin.dermadoc.entities.CaseStatus;
import at.tuwien.telemedizin.dermadoc.entities.Gender;
import at.tuwien.telemedizin.dermadoc.entities.GeoLocation;
import at.tuwien.telemedizin.dermadoc.entities.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CaseListFragment.OnCaseListEventListener, UserDataCallbackInterface {


    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private Patient user; // the currently active user

    private List<CaseParc> currentCaseList;
    private List<CaseParc> closedCaseList;
    private ServerInterface serverInterface;

    public static final String TOKEN_INTENT_KEY = MainActivity.class.getName() + "TOKEN_INTENT";
    public static final String TOKEN_TYPE_INTENT_KEY = MainActivity.class.getName() + "TOKEN_TYPE_INTENT";

    private AuthenticationToken authenticationToken;


    private CaseSortCategory caseListSortCategory; // set when a sort is executed

    // To hide the sort-menu-item whenever fragments are changed etc.
    private MenuItem sortMenuItem;

    private RelativeLayout mainContentLayout;
    private RelativeLayout loadingProgressLayout;
    private TextView loadingProgressInfoTextView;

    private LoadUserDataTask loadUserDataTask;
    private PatientParc currentUser;


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

//        Intent intent = getIntent();
//        // get the AuthenticationToken
//        String aToken = intent.getStringExtra(TOKEN_INTENT_KEY);
//        String aTokenType = intent.getStringExtra(TOKEN_TYPE_INTENT_KEY);
//        if (aToken != null) {
//            authenticationToken = new AuthenticationToken();
//            authenticationToken.setToken(aToken);
//            authenticationToken.setType(aTokenType);
//        }

        mainContentLayout = (RelativeLayout) findViewById(R.id.contentFrame);

        loadingProgressLayout = (RelativeLayout) findViewById(R.id.loading_data_progress_layout);
        loadingProgressLayout.setVisibility(View.GONE);
        loadingProgressInfoTextView = (TextView) findViewById(R.id.loading_data_info_text);

        serverInterface = ServerInterfaceFactory.getInstance();

        Log.d(LOG_TAG, "serverInterface - authToken?: " + ((RestServerInterface)serverInterface).hasAuthToken());

        // TODO

        getDataFromServer();


        // TODO replace
        ContentProvider cP = ContentProviderFactory.getContentProvider();
        currentCaseList = cP.getCurrentCasesOfUser();
        closedCaseList = cP.getCurrentCasesOfUser(); // TODO for testing purpose - remove or replace

        // TODO load User Data
        currentUser = cP.getCurrentUser();
    }

    /**
     * sends local data to server and retrieves data from server
     * TODO
     */
    private void syncData() {
        // send data TODO
        // get data
        getDataFromServer();
    }

    private void getDataFromServer() {
        // TODO
        // 1. load Patient data

        // 2. Load Case-List

        // 3. load user Data
        loadUserData();
    }

    private void loadUserData() {
        String infoText = getString(R.string.label_loading_data_dynamic, getString(R.string.option_loading_user_data_insert));
        showProgress(true, infoText);
        loadUserDataTask = new LoadUserDataTask(this);
        loadUserDataTask.execute((Void) null);
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
            syncData();
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

            fragment = UserDataOverviewFragment.newInstance();
//            Toast.makeText(getBaseContext(), "There will be a User-Fragment", Toast.LENGTH_LONG).show(); // TODO
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
    public List<CaseParc> onListRequest(long listKey) {
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

    /**
     * this method supplements the showProgress(boolean, String) method
     * @param show
     */
    private void showProgress(boolean show) {
        showProgress(show, getString(R.string.label_loading_data_static));
    }

    /**
     * Shows the progress UI and hides the login form.
     * @param show - should the progress view be visible or not
     * @param infoText - the text that should be displayed along with the progress-view
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show, String infoText) {
        // set the info-text in the text-view
        loadingProgressInfoTextView.setText(infoText);

        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mainContentLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            mainContentLayout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mainContentLayout.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            loadingProgressLayout.setVisibility(show ? View.VISIBLE : View.GONE);
            loadingProgressLayout.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loadingProgressLayout.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            loadingProgressLayout.setVisibility(show ? View.VISIBLE : View.GONE);
            mainContentLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class LoadUserDataTask extends AsyncTask<Void, Void, Patient> {
        private MainActivity activity;

        private String outp;

        LoadUserDataTask(MainActivity activity) {
            this.activity = activity;
        }

        @Override
        protected Patient doInBackground(Void... params) {
            Log.d(LOG_TAG, "doInBackground()");


            ServerInterface sI = ServerInterfaceFactory.getInstance();
            Patient currentUser = sI.getUser();

            // TODO remoeve
            try {
                Thread.sleep(3000);                 //1000 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            Log.d(LOG_TAG, "retrieved User: " + ToStringHelper.toString(currentUser));

            return currentUser;
        }

        @Override
        protected void onPostExecute(final Patient user) {
            Log.d(LOG_TAG,"onPostExecute() user: " + user);
            loadUserDataTask = null;
            showProgress(false);
            // TODO check result etc.
        }

        @Override
        protected void onCancelled() {
            loadUserDataTask = null;
            showProgress(false);
        }
    }

    @Override
    public PatientParc getUser() {
        return currentUser;
    }
}
