package at.tuwien.telemedizin.dermadoc.app.activities_fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case.EditCaseActivity;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.help.HelpActivity;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.login.LoginActivity;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.preferences.PreferenceActivity;
import at.tuwien.telemedizin.dermadoc.app.adapters.MyCasesPagerEnum;
import at.tuwien.telemedizin.dermadoc.app.comparators.CaseSortCategory;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.CaseParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.NotificationParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.PatientParc;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Case;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Notification;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Patient;
import at.tuwien.telemedizin.dermadoc.app.general_entities.User;
import at.tuwien.telemedizin.dermadoc.app.helper.ConnectionDetector;
import at.tuwien.telemedizin.dermadoc.app.helper.ParcelableHelper;
import at.tuwien.telemedizin.dermadoc.app.server_interface.RestServerInterface;
import at.tuwien.telemedizin.dermadoc.app.server_interface.ServerInterface;
import at.tuwien.telemedizin.dermadoc.app.server_interface.ServerInterfaceFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CaseListFragment.OnCaseListEventListener, UserDataCallbackInterface {


    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    boolean doubleBackToExitPressedOnce = false;

    private Patient user; // the currently active user

    private List<CaseParc> currentCaseList;
    private List<CaseParc> closedCaseList;

    private List<NotificationParc> currentNotificationList;


    private CaseSortCategory caseListSortCategory; // set when a sort is executed

    // To hide the sort-menu-item whenever fragments are changed etc.
    private MenuItem sortMenuItem;
    private MenuItem sortIdMenuItem;
    private MenuItem sortNameMenuItem;
    private MenuItem sortDateMenuItem;
    private MenuItem sortStatusMenuItem;
    private MenuItem sortNotificationMenuItem;



    private RelativeLayout mainContentLayout;
    private RelativeLayout loadingProgressLayout;
    private TextView loadingProgressInfoTextView;

    private FloatingActionButton fab;

    private LoadUserDataTask loadUserDataTask;
    private LoadCaseListDataTask loadCurrentCaseListDataTask;
    private LoadNotificationsTask loadCurrentNotificationsTask;

    private PatientParc currentUser;

    private CaseListFragment fragmentCurrentCaseList;
    private CaseListFragment fragmentOldCaseList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // load sort-category from preferences
        this.caseListSortCategory =  loadSortCategoryFromPreferences();
        Log.d(LOG_TAG, "defaultListSortCategory=" + caseListSortCategory);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                // launch activity to create a new case
                Intent intent = new Intent(MainActivity.this, EditCaseActivity.class);
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


        currentNotificationList = new ArrayList<>();


    }

    private CaseSortCategory loadSortCategoryFromPreferences() {
        // initialize sorting category
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String selectedSortCategoryStr = sp.getString(getString(R.string.pref_sort_category_key), "-1");
        int selectedSortCategoryInt = Integer.parseInt(selectedSortCategoryStr);
        if (selectedSortCategoryInt != -1) {
            return CaseSortCategory.getCategory(selectedSortCategoryInt);
        }
        return null;
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

        // 1. Load Case-List
        currentCaseList = new ArrayList<>();
        closedCaseList = new ArrayList<>();
        loadCaseListData();

        // 2. load user Data
        loadUserData();

        // 3. load Notifications
        loadNotificationList();
    }

    private void loadUserData() {
        Log.d(LOG_TAG, "loadUserData()");

        // check internet connection
        if (!checkInternetConnection()) {
            return;
        }

        String infoText = getString(R.string.label_loading_data_dynamic, getString(R.string.option_loading_user_data_insert));
        showProgress(true, infoText);
        loadUserDataTask = new LoadUserDataTask(this);
        loadUserDataTask.execute((Void) null);
    }

    private void loadCaseListData() {
        Log.d(LOG_TAG, "loadCaseListData()");

        // check internet connection
        if (!checkInternetConnection()) {
            return;
        }

        String infoText = getString(R.string.label_loading_data_dynamic, getString(R.string.option_loading_case_list_data_insert));
        showProgress(true, infoText);
        loadCurrentCaseListDataTask = new LoadCaseListDataTask(this);
        loadCurrentCaseListDataTask.execute((Void) null);
    }

    private void loadNotificationList() {
        Log.d(LOG_TAG, "loadNotificationList()");

        // check internet connection
        if (!checkInternetConnection()) {
            return;
        }

        String infoText = getString(R.string.label_loading_data_dynamic, getString(R.string.option_loading_notification_list_insert));
        showProgress(true, infoText);
        loadCurrentNotificationsTask = new LoadNotificationsTask(this);
        loadCurrentNotificationsTask.execute((Void) null);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // press twice to exit
            if (doubleBackToExitPressedOnce) {
//                super.onBackPressed();
                performLogout();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getString(R.string.msg_press_back_to_exit), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);

        }
    }

    /**
     * checks, if internet-connection is possible and returns a boolean value
     * if no connection is available, it shows a info-message to the user
     * @return
     */
    private boolean checkInternetConnection() {
        boolean connected = ConnectionDetector.isConnectingToInternet(this);
        Log.d(LOG_TAG, "checkInternetConnection: " + connected);
        if (!connected) {
            // show message to user
            Toast.makeText(this, getString(R.string.msg_no_internet_connection_available), Toast.LENGTH_SHORT).show();
        }
        return connected;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDataFromServer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        sortMenuItem = menu.findItem(R.id.action_sort);

        sortIdMenuItem = menu.findItem(R.id.action_sort_id);
        sortNameMenuItem = menu.findItem(R.id.action_sort_name);
        sortDateMenuItem = menu.findItem(R.id.action_sort_date_of_creation);
        sortStatusMenuItem = menu.findItem(R.id.action_sort_status);
        sortNotificationMenuItem = menu.findItem(R.id.action_sort_notification);

        checkActiveSortMenuItem(this.caseListSortCategory); // default item should be selected -> execute method
        return true;
    }

    private void checkActiveSortMenuItem(CaseSortCategory category) {
        switch (category) {
            case ID: sortIdMenuItem.setChecked(true); break;
            case STATUS: sortStatusMenuItem.setChecked(true); break;
            case NAME: sortNameMenuItem.setChecked(true); break;
            case DATE_OF_CREATION: sortDateMenuItem.setChecked(true); break;
            case NOTIFICATIONS: sortNotificationMenuItem.setChecked(true); break;


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
//            Toast.makeText(getBaseContext(), "There will be a Settings-activity", Toast.LENGTH_LONG).show(); // TODO replace with real fragment/function
            Intent intent = new Intent(MainActivity.this, PreferenceActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_syncronize) {
            syncData();

            return true;
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
            fab.setVisibility(View.VISIBLE);

        } else if (id == R.id.nav_my_account) {

            fragment = UserDataOverviewFragment.newInstance();
//            Toast.makeText(getBaseContext(), "There will be a User-Fragment", Toast.LENGTH_LONG).show(); // TODO
            title = getString(R.string.nav_my_account);
            fab.setVisibility(View.GONE);
            sortMenuItem.setVisible(false);
        } else if (id == R.id.nav_help) {
//            Toast.makeText(getBaseContext(), "There will be a Help-activity", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);

            title = oldTitle.toString();
        } else if (id == R.id.nav_logout) {
            performLogout();
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

    /**
     * finishes the current activity and starts the login_Activity
     */
    private void performLogout() {
        // Server - Logout TODO!!
        finish();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    @Override
    public List<CaseParc> onListRequest(long listKey, CaseListFragment fragment) {
        if (listKey == MyCasesPagerEnum.CURRENT.getKey()) {
            fragmentCurrentCaseList = fragment; // set the fragment for list-change-events
            return currentCaseList;
        } else if (listKey == MyCasesPagerEnum.OLD.getKey()) {
            fragmentOldCaseList = fragment; // set the fragment for list-change-events
            return closedCaseList;
        } else {
            // nothing matched ...
            return null;
        }
    }

    @Override
    public List<NotificationParc> onNotificationsRequest() {
        return currentNotificationList;
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
     * sets the task = null and deactivates showProgess, if all tasks are null
     * @param task
     */
    private synchronized void asyncTaskFinished(AsyncTask task) {
        if (task instanceof LoadUserDataTask) {
           loadUserDataTask = null;
        } else if (task instanceof LoadCaseListDataTask) {
            loadCurrentCaseListDataTask = null;

        } else if (task instanceof LoadNotificationsTask) {
            loadCurrentNotificationsTask = null;
        }

        if (loadUserDataTask == null
                && loadCurrentCaseListDataTask == null
                && loadCurrentNotificationsTask == null) {
            showProgress(false);
        }
    }

    /**
     * Represents an asynchronous taks that laods the detailed user data of the current user
     */
    public class LoadUserDataTask extends AsyncTask<Void, Void, Patient> {
        private MainActivity activity;

        LoadUserDataTask(MainActivity activity) {
            this.activity = activity;
        }

        @Override
        protected Patient doInBackground(Void... params) {
            Log.d(LOG_TAG, "doInBackground()");


            ServerInterface sI = ServerInterfaceFactory.getInstance();
            User currentUser = sI.getUser();
            if (currentUser instanceof Patient) {
                Patient currentPatientUser = (Patient) currentUser;
                return currentPatientUser;

            }

            return null;
        }

        @Override
        protected void onPostExecute(final Patient user) {
            Log.d(LOG_TAG,"onPostExecute() user: " + user);

            if (user == null) {
                Log.d(LOG_TAG,"user == null -> finish activity");
                // TODO

            } else {
                currentUser = new PatientParc(user);
            }


            asyncTaskFinished(LoadUserDataTask.this);
        }

        @Override
        protected void onCancelled() {
            asyncTaskFinished(LoadUserDataTask.this);
        }
    }

    /**
     * Represents an asynchronous task that loads the case list for the current user
     */
    public class LoadCaseListDataTask extends AsyncTask<Void, Void, List<Case>> {
        private MainActivity activity;

        LoadCaseListDataTask(MainActivity activity) {
            this.activity = activity;
        }

        @Override
        protected List<Case> doInBackground(Void... params) {
            Log.d(LOG_TAG, "doInBackground()");


            ServerInterface sI = ServerInterfaceFactory.getInstance();
            List<Case> caseListRaw = sI.getCases();
            if (caseListRaw != null) {
                Log.d(LOG_TAG, "casesListRaw.size(): " + caseListRaw.size());
                return caseListRaw;

            }

            return null;
        }

        @Override
        protected void onPostExecute(final List<Case> list) {
            Log.d(LOG_TAG,"onPostExecute() list!=null: " + (list!=null));

            if (list == null) {
                Log.d(LOG_TAG,"list == null -> finish activity");
                // TODO

            } else {
                currentCaseList = ParcelableHelper.mapCaseListToParc(list);
                Log.d(LOG_TAG, "currentCaseList.size(): " + currentCaseList.size());
                // inform the list-holde fragment, that the list changed
                if (fragmentCurrentCaseList != null) {
                    fragmentCurrentCaseList.informCaseListChanged();
                }
                if (fragmentOldCaseList != null) {
                    fragmentOldCaseList.informCaseListChanged();
                }

            }


            asyncTaskFinished(LoadCaseListDataTask.this);
        }

        @Override
        protected void onCancelled() {
            asyncTaskFinished(LoadCaseListDataTask.this);
        }
    }

    @Override
    public PatientParc getUser() {
        return currentUser;
    }

    /**
     * Represents an asynchronous taks that loads all notifications
     */
    public class LoadNotificationsTask extends AsyncTask<Void, Void, List<Notification>> {
        private MainActivity activity;

        LoadNotificationsTask(MainActivity activity) {
            this.activity = activity;
        }

        @Override
        protected List<Notification> doInBackground(Void... params) {
            Log.d(LOG_TAG, "doInBackground()");


            ServerInterface sI = ServerInterfaceFactory.getInstance();
            List<Notification> notificationList = sI.getNotifications();
            if (notificationList != null) {
                Log.d(LOG_TAG, "notificationList.size(): " + notificationList.size());
                return notificationList;

            }

            return null;
        }

        @Override
        protected void onPostExecute(final List<Notification> list) {
            Log.d(LOG_TAG,"onPostExecute()");

            if (list == null) {
                Log.d(LOG_TAG,"list == null");

            } else {
                currentNotificationList = ParcelableHelper.mapToNotificationParcList(list);
                Log.d(LOG_TAG, "currentNotificationList.size(): " + currentNotificationList.size());
                // inform the list-holde fragment, that the list changed
                if (fragmentCurrentCaseList != null) {
                    fragmentCurrentCaseList.informNotificationListChanged();
                }
                if (fragmentOldCaseList != null) {
                    fragmentOldCaseList.informNotificationListChanged();
                }

            }


            asyncTaskFinished(LoadNotificationsTask.this);
        }

        @Override
        protected void onCancelled() {
            asyncTaskFinished(LoadNotificationsTask.this);
        }
    }
}
