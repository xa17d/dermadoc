package at.tuwien.telemedizin.dermadoc.app.activities_fragments.case_specific;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case.EditCaseActivity;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case.EditLocationFragment;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case.EditSymptomsFragment;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.edit_case.AddPictureActivity;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.CaseParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.PatientParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.PhysicianParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.CaseDataParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.CaseInfoParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.TextMessageParc;
import at.tuwien.telemedizin.dermadoc.app.helper.CaseDataExtractionHelper;
import at.tuwien.telemedizin.dermadoc.app.helper.FormatHelper;
import at.tuwien.telemedizin.dermadoc.app.server_interface.ServerInterface;
import at.tuwien.telemedizin.dermadoc.app.server_interface.ServerInterfaceFactory;
import at.tuwien.telemedizin.dermadoc.entities.BodyLocalization;
import at.tuwien.telemedizin.dermadoc.entities.PainIntensity;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseInfo;


public class CaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CaseDataCallbackInterface,
        EditLocationFragment.BodyLocationCallbackInterface {

    public static final String LOG_TAG = CaseActivity.class.getSimpleName();

    public static final String USER_INTENT_KEY = CaseActivity.class.getSimpleName() + "UserIntent";
    public static final int PICTURE_REQUEST_KEY = 1;
    public static final int UPDATE_CASE_REQUEST_KEY = 2;


    private CaseParc caseItem;
    private PatientParc currentUser;

    private NavigationView navigationView;
    private TextView navHeaderMainTextView;
    private TextView navHeaderPatientTextView;
    private TextView navHeaderStatusTextView;
    private TextView navHeaderPhysicianTextView;
    private TextView navHeaderDateOfCreationTextView;

    private MenuItem attachItem;

    private RelativeLayout mainContentLayout;
    private RelativeLayout loadingProgressLayout;
    private TextView loadingProgressInfoTextView;

    private FloatingActionButton fab;

    private SendMessagesAsyncTask sendMessageTask;

    private CaseOverviewFragment overviewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Edit CaseInfo
                Intent editCaseIntent = new Intent(CaseActivity.this, EditCaseActivity.class);
                editCaseIntent.putExtra(EditCaseActivity.NEW_CASE_FLAG_INTENT_KEY, false);
                editCaseIntent.putExtra(EditCaseActivity.CASE_FLAG_INTENT_KEY, caseItem);
                startActivityForResult(editCaseIntent, UPDATE_CASE_REQUEST_KEY);

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
        overviewFragment = CaseOverviewFragment.newInstance();
        String title = getString(R.string.nav_case_overview);

        if (overviewFragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.contentFrame,overviewFragment);
            fragmentTransaction.commit();
            setTitle(title);
        }

        // load case
        Intent intent = getIntent();
        Parcelable caseParcel = intent.getParcelableExtra(CaseParc.INTENT_KEY);
        Parcelable currentUserParcel = intent.getParcelableExtra(USER_INTENT_KEY);
        if (caseParcel != null) {
//            Log.d(LOG_TAG, "case-parcelable in intent != null -> casting");
            caseItem = (CaseParc) caseParcel;
        } else {
//            Log.d(LOG_TAG, "case-parcelable == null -> exiting activity");
            Toast.makeText(getBaseContext(), getString(R.string.msg_err_item_not_received), Toast.LENGTH_LONG).show();
            this.finish();
        }

        if (currentUserParcel != null) {
//            Log.d(LOG_TAG, "case-parcelable in intent != null -> casting");
            currentUser = (PatientParc) currentUserParcel;
        }

        Log.d(LOG_TAG, "caseItem == null? " + (caseItem == null));
        if (caseItem != null) {
            Log.d(LOG_TAG, "case: " + caseItem.getStatus()
                    + ", patient: " + caseItem.getPatient().getName());
        }

        // set up navigation drawer - header
        setUpNavigationViewHeader();

        mainContentLayout = (RelativeLayout) findViewById(R.id.contentFrame);
        loadingProgressLayout = (RelativeLayout) findViewById(R.id.loading_data_progress_layout);
        loadingProgressLayout.setVisibility(View.GONE);
        loadingProgressInfoTextView = (TextView) findViewById(R.id.loading_data_info_text);



    }



    /**
     * fills the header of the navigation-drawer with data
     */
    private void setUpNavigationViewHeader() {
        navHeaderMainTextView = (TextView)
                navigationView.getHeaderView(0).findViewById(R.id.case_nav_header_primary_text);
        navHeaderMainTextView.setText(getString(R.string.nav_case) + ": " + caseItem.getName());

        // TODO patient info is less interesting for the patient -> remove
        navHeaderPatientTextView = (TextView)
                navigationView.getHeaderView(0).findViewById(R.id.case_overview_id);
        PatientParc patient = caseItem.getPatient();
        navHeaderPatientTextView.setText(caseItem.getId() + "");

        navHeaderPhysicianTextView = (TextView)
                navigationView.getHeaderView(0).findViewById(R.id.case_overview_physician);
        PhysicianParc physician = caseItem.getPhysician();
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


        attachItem = menu.findItem(R.id.action_attach);
        attachItem.setVisible(false); // hide initially
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
        } else if (id == R.id.action_attach) {
            // start the activity for result to return a list of pictures
            Intent intent = new Intent(this, AddPictureActivity.class);
            startActivityForResult(intent, AddPictureActivity.PICTURES_RESULT_INTENT_KEY);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(LOG_TAG, "onActivityResult() requestCode: " + requestCode + ", resultCode: " + resultCode);

        if (resultCode == RESULT_OK) {

            if (requestCode == PICTURE_REQUEST_KEY) {
                Log.d(LOG_TAG, "PICTURE_REQUEST_KEY");
                List<CaseDataParc> photoRelatedMessageList = data.getParcelableArrayListExtra(AddPictureActivity.PICTURE_LIST_INTENT);
                // TODO
                if (photoRelatedMessageList != null && photoRelatedMessageList.size() > 0) {
                    Log.d(LOG_TAG, "pictureMessageList=" + photoRelatedMessageList.size());
                    sendCaseDataMessages(photoRelatedMessageList);

                }
            } else if (requestCode == UPDATE_CASE_REQUEST_KEY) {
                Log.d(LOG_TAG, "UPDATE_CASE_REQUEST_KEY");
                List<CaseDataParc> updatedCaseDataElements = data.getParcelableArrayListExtra(EditCaseActivity.CASE_DATA_LIST_INTENT_KEY);

                if (updatedCaseDataElements != null && updatedCaseDataElements.size() > 0) {
                    Log.d(LOG_TAG, "updatedCaseDataElements=" + updatedCaseDataElements.size());
                    sendCaseDataMessages(updatedCaseDataElements);
                    // update the overview - view TODO besser einbinden
                    overviewFragment = CaseOverviewFragment.newInstance();
                    String title = getString(R.string.nav_case_overview);

                    if (overviewFragment != null) {
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.contentFrame,overviewFragment);
                        fragmentTransaction.commit();
                        setTitle(title);
                    }

                }
            }
        }


    }

    private void sendCaseDataMessages(List<CaseDataParc> messages) {

        // add to case
        // TODO remove perhaps? START
        for(CaseDataParc pM : messages) {
            caseItem.addDataElement(pM);
        }
        // TODO remove END

        showProgress(true, getString(R.string.hint_sending));
        sendMessageTask = new SendMessagesAsyncTask(this);

        CaseDataParc[] msgArray = new CaseDataParc[messages.size()];
        msgArray = messages.toArray(msgArray);

        sendMessageTask.execute(msgArray);
    }



    public void switchToOverview() {
        onNavigationItemSelected(R.id.nav_case_overview);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        onNavigationItemSelected(item.getItemId());

        return true;
    }

    public boolean onNavigationItemSelected(int id) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        Fragment fragment = null;
        String title = "" + caseItem.getId(); // TODO change to name or something
        CharSequence oldTitle = getTitle();


        if (id == R.id.nav_case_overview) {
            fragment = CaseOverviewFragment.newInstance(); //
            overviewFragment = (CaseOverviewFragment) fragment;
            title = getString(R.string.nav_case_overview);
            fab.setVisibility(View.VISIBLE);
            fab.setImageResource(R.drawable.ic_action_edit_white_18dp);

            attachItem.setVisible(false); // hide

        } else if (id == R.id.nav_case_conversation) {
            fragment = CaseDataListFragment.newInstance();
            title = getString(R.string.nav_case_diagnoses);
            fab.setVisibility(View.GONE);
//            fab.setImageResource(R.drawable.ic_add_white_24dp);

            attachItem.setVisible(true); // hide

        } else if (id == R.id.nav_back_to_main) {
            // finish this activity
            CaseActivity.this.finish();
            return true;
        }
        // logout does not return a fragment != null
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.contentFrame,fragment);
            fragmentTransaction.commit();
        }



        drawer.closeDrawer(GravityCompat.START);

        setTitle(title);

        return true;
    }



    @Override
    public CaseParc getCase() {
        return caseItem;
    }

    @Override
    public List<BodyLocalization> getBodyLocations() {
        // provide the localizations to the fragment
        CaseInfoParc cI = CaseDataExtractionHelper.getLatestCaseInfo(caseItem.getDataElements());
        if (cI != null) {
            return cI.getLocalizations();
        } else {
            return new ArrayList<BodyLocalization>();
        }
    }

    /**
     * creates and send the text message
     * @param text
     */
    public void sendTextMessage(String text) {
        TextMessageParc newMessage = new TextMessageParc(-1, Calendar.getInstance(), currentUser, text);

        // add to case
        // TODO remove perhaps?
        caseItem.addDataElement(newMessage);

        showProgress(true, getString(R.string.hint_sending));
        sendMessageTask = new SendMessagesAsyncTask(this);
        sendMessageTask.execute(newMessage);

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
    public class SendMessagesAsyncTask extends AsyncTask<CaseDataParc, Void, Void> {
        private CaseActivity activity;

        private String outp;

        SendMessagesAsyncTask(CaseActivity activity) {
            this.activity = activity;
        }

        @Override
        protected Void doInBackground(CaseDataParc... params) {
            Log.d(LOG_TAG, "doInBackground() " + params.length);

            List<CaseDataParc> caseDataList = new ArrayList<CaseDataParc>(Arrays.asList(params));

            ServerInterface sI = ServerInterfaceFactory.getInstance();
            // TODO POST /cases/{caseId}/data

            // TODO remoeve
            try {
                Thread.sleep(3000);                 //1000 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            Log.d(LOG_TAG, "onPostExecute()");
            // TODO start sync task back
            sendMessageTask = null;
            showProgress(false);
        }

        @Override
        protected void onCancelled() {
            sendMessageTask = null;
            showProgress(false);
        }
    }
}
