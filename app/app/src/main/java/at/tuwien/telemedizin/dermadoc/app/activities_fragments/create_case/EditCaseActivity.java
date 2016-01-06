package at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.adapters.NewCasePagerAdapter;
import at.tuwien.telemedizin.dermadoc.app.adapters.NewCasePagerEnum;
import at.tuwien.telemedizin.dermadoc.app.entities.CaseValidationError;
import at.tuwien.telemedizin.dermadoc.app.entities.CaseValidationErrorLevel;
import at.tuwien.telemedizin.dermadoc.app.entities.PictureHelperEntity;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.CaseParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.PatientParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.PhysicianParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AnamnesisParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.CaseDataParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.CaseInfoParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.PhotoMessageParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.TextMessageParc;
import at.tuwien.telemedizin.dermadoc.app.helper.CaseDataExtractionHelper;
import at.tuwien.telemedizin.dermadoc.app.helper.ToStringHelper;
import at.tuwien.telemedizin.dermadoc.app.persistence.ContentProvider;
import at.tuwien.telemedizin.dermadoc.app.persistence.ContentProviderFactory;
import at.tuwien.telemedizin.dermadoc.app.server_interface.ServerInterface;
import at.tuwien.telemedizin.dermadoc.app.server_interface.ServerInterfaceFactory;
import at.tuwien.telemedizin.dermadoc.entities.BodyLocalization;
import at.tuwien.telemedizin.dermadoc.entities.PainIntensity;


public class EditCaseActivity extends AppCompatActivity implements OnCaseDataRequestAndUpdateInterface,
        OnTabChangedInFragmentInterface, EditSymptomsFragment.SymptomsSourceInterface,
        EditLocationFragment.BodyLocationCallbackInterface {

    public static final String LOG_TAG = EditCaseActivity.class.getSimpleName();

    public static final String NEW_CASE_FLAG_INTENT_KEY = EditCaseActivity.class.getSimpleName() + "newCase";
    public static final String CASE_FLAG_INTENT_KEY = EditCaseActivity.class.getSimpleName() + "case";
    public static final String CASE_DATA_LIST_INTENT_KEY = EditCaseActivity.class.getSimpleName() + "caseDataList";

    public static final int REQUEST_CAMERA = 1;
    public static final int SELECT_FILE = 102;

    private AnamnesisParc defaultAnamnesis;
    private List<PhysicianParc> nearbyPhysicians;

    private List<CaseValidationError> currentErrorList;

    private LinearLayout mainContentLayout;
    private RelativeLayout loadingProgressLayout;
    private TextView loadingProgressInfoTextView;

    private LoadPhysicianListAsyncTask loadPhysicianListAsyncTask;
    private SendingEditedCaseAsyncTask sendingEditedCaseAsyncTask;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private NewCasePagerAdapter mSectionsPagerAdapter;

    private FloatingActionButton fab;
    private TabLayout tabLayout;

    private boolean newCase;
    private CaseParc caseItem; // only != null when newCase = false

    private EditAnamnesisFragment anamnesisFragmen;
    private EditSymptomsFragment symptomsFragment;
    private EditPicturesFragment picturesFragment;
    private EditLocationFragment locationFragment;
    private PhysicianSelectionFragment physicianSelectionFragment;
    private FinishEditingFragment finishFragment;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_case_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get the newCase Flag
        Intent intent = getIntent();
        newCase = intent.getBooleanExtra(NEW_CASE_FLAG_INTENT_KEY, true);

        if (!newCase) {
            // get Case info
            Parcelable caseParcel = intent.getParcelableExtra(CASE_FLAG_INTENT_KEY);
            if (caseParcel != null) {
                Log.d(LOG_TAG, "caseParcel != null");
                caseItem = (CaseParc) caseParcel;
            }
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new NewCasePagerAdapter(this, getSupportFragmentManager());
        addFragmentsToAdapter(mSectionsPagerAdapter, newCase);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabsFromPagerAdapter(mViewPager.getAdapter());
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // set FloatingActionButton Visibility.GONE until sufficient data was entered for the new case
        fab.setVisibility(View.GONE);

        int titleRId = newCase ? R.string.title_activity_create_case : R.string.title_activity_update_case;
        setTitle(titleRId);

        mainContentLayout = (LinearLayout) findViewById(R.id.content_root_container);

        loadingProgressLayout = (RelativeLayout) findViewById(R.id.loading_data_progress_layout);
        loadingProgressLayout.setVisibility(View.GONE);
        loadingProgressInfoTextView = (TextView) findViewById(R.id.loading_data_info_text);

        currentErrorList = new ArrayList<>(); // empty list

        // TODO change to this user
        setUpData();
    }

    private void addFragmentsToAdapter(NewCasePagerAdapter adapter, boolean bNewCase) {

        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();

        // add fragments according to the NewCasePagerEnum
        symptomsFragment = EditSymptomsFragment.newInstance(bNewCase);
        fragmentList.add(symptomsFragment);
        titleList.add(getString(NewCasePagerEnum.SYMPTOMS.getTitleResId()));

        picturesFragment = EditPicturesFragment.newInstance(bNewCase, false);
        fragmentList.add(picturesFragment);
        titleList.add(getString(NewCasePagerEnum.PICTURE.getTitleResId()));

        locationFragment = EditLocationFragment.newInstance(bNewCase, false);
        fragmentList.add(locationFragment);
        titleList.add(getString(NewCasePagerEnum.LOCATION.getTitleResId()));

        // only add for newCase = true
        if (newCase) {
            anamnesisFragmen = EditAnamnesisFragment.newInstance();
            fragmentList.add(anamnesisFragmen);
            titleList.add(getString(NewCasePagerEnum.ANAMNESIS.getTitleResId()));
        }

        physicianSelectionFragment = PhysicianSelectionFragment.newInstance(bNewCase);
        fragmentList.add(physicianSelectionFragment);
        titleList.add(getString(NewCasePagerEnum.PHYSICIAN.getTitleResId()));

        finishFragment = FinishEditingFragment.newInstance(bNewCase);
        fragmentList.add(finishFragment);
        titleList.add(getString(NewCasePagerEnum.FINISH_EDITING.getTitleResId()));

        adapter.addFragments(fragmentList, titleList);
    }

    private void setUpData() {
        ContentProvider cP = ContentProviderFactory.getContentProvider();

        defaultAnamnesis = cP.getAnamnesisForm(); // TODO get data from server?
        PatientParc user = cP.getCurrentUser(); // TODO get data from ?
        Calendar cal = Calendar.getInstance();

        if (newCase) {
            caseItem = new CaseParc(-1, user, cal);
        }


        // TODO get data from server
        nearbyPhysicians = new ArrayList<>();
        if (newCase) {
            showProgress(true, getString(R.string.hint_loading));
            loadPhysicianListAsyncTask = new LoadPhysicianListAsyncTask(this);
            loadPhysicianListAsyncTask.execute();
        }


    }

    /**
     * setup the tab-layout: add tabs
     * @param tabLayout
     */
    private void setupTabLayout(TabLayout tabLayout) {
////        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        // outdated - remove later TODO
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_case_menu, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void switchToTheNextTab() {
        int currentIndex = mViewPager.getCurrentItem();
        int pageCount = mViewPager.getAdapter().getCount();
        // avoid index errors
        if (currentIndex + 1 < pageCount) {
            mViewPager.setCurrentItem(currentIndex + 1);
        }

    }

    @Override
    public void switchToTab(int index) {
        if (index >= 0 && index < mViewPager.getAdapter().getCount()) {
            mViewPager.setCurrentItem(index);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(LOG_TAG, "onActivityResult() requestCode: " + requestCode + ", resultCode: " + resultCode);


    }

//    private void notifyPictureReceiver(PictureHelperEntity picture) {
//        Log.d(LOG_TAG, "notifyPictureReceiver()");
//        if (pictureReceiver != null) {
//            pictureReceiver.receiveNewPicture(picture);
//        }
//    }

    /**
     *
     * @param pictureToBeModified the picture-object which should be modified and replaced in the list
     *                            Sent for identification-purpose
     * @param description the new description
     */
    public void notifyEditPictureDescription(PictureHelperEntity pictureToBeModified, String description) {
        Log.d(LOG_TAG, "notifyEditPictureDescription()");
        if (picturesFragment != null) {
            picturesFragment.modifyDescriptionOfPicture(pictureToBeModified, description);
        }
    }

    @Override
    public AnamnesisParc getAnamnesisForm() {

        return defaultAnamnesis;
    }

    @Override
    public CaseParc getCase() {
        return caseItem;
    }

    @Override
    public List<PhysicianParc> getNearbyPhysicians() {
        return nearbyPhysicians;
    }

    /**
     * This method displays a progress-layout and does the following:
     * collects all input data
     * checks the input data for missing information
     * sends the data to the server
     */
    @Override
    public CaseParc finishEditing() {
        String infoText = getString(R.string.hint_progress_collecting_data);
        // 1) collecting all input data
        // show progress
        showProgress(true, infoText);
        CaseParc editedCaseItem = null;
        ArrayList<CaseDataParc> updatedCaseData = null;

        if (newCase) {
            editedCaseItem = createCaseAndCollectData();
        } else {
            updatedCaseData = collectCaseData();
        }

        // 2) check if the data is correct
        infoText = getString(R.string.hint_progress_checking_data);
        loadingProgressInfoTextView.setText(infoText);
        if (newCase) {
            currentErrorList = validateCase(editedCaseItem);
        }

        // get rid of old errorMessages
        finishFragment.resetErrorMessages();

        if (currentErrorList.size() > 0) {
            // if the list.size > 0 -> Errors or warnings -> display them
            finishFragment.addErrorMessages(currentErrorList);

            // check if only warnings
            if (onlyWarnings(currentErrorList)) {
                // only warnings - ask the user if he wants to continue anyway
                showValidationAlertDialog(editedCaseItem);

            } else {
                // there are errors which have to be fixed first
                showProgress(false, null);
                return editedCaseItem;
            }
        } else {


            // 3) no errors -> send it to the server
            if (newCase) {
                infoText = getString(R.string.hint_progress_sending_data);
                loadingProgressInfoTextView.setText(infoText);
                sendDataToServer(editedCaseItem);
            } else {
                finishActivityReturnCaseDataList(updatedCaseData);
                return null;
            }



        }

        return editedCaseItem;
    }

    private void finishActivityReturnCaseDataList(ArrayList<CaseDataParc> list) {
        Intent resultData = new Intent();
        resultData.putParcelableArrayListExtra(CASE_DATA_LIST_INTENT_KEY, list);
        setResult(RESULT_OK, resultData);
        finish();

    }


    @Override
    public List<CaseValidationError> getCaseValidationErrors() {
        return currentErrorList;
    }

    /**
     * display a dialog and ask the user, if warnings should be ignored.
     * If the warnings are ignored, the case is sent to the server. If the dialog is
     * canceled, the user can fix the warnings.
     * @param editedCaseItem
     */
    private void showValidationAlertDialog(CaseParc editedCaseItem) {
        final CaseParc caseItemToSend = editedCaseItem;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.msg_dialog_validation_warning)
                .setTitle(R.string.title_dialog_validation_warning);
        // Add the buttons
        builder.setPositiveButton(R.string.option_continue, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button -> ignore the warnings and send the data
                String infoText = getString(R.string.hint_progress_sending_data);
                loadingProgressInfoTextView.setText(infoText);
                sendDataToServer(caseItemToSend);
            }
        });
        builder.setNegativeButton(R.string.option_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog  - return to fix the warnings
                showProgress(false, null);
            }
        });
        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean onlyWarnings(List<CaseValidationError> errorList) {
        for (CaseValidationError e : errorList) {
            if (e.getLevel() == CaseValidationErrorLevel.ERROR) {
                return false;
            }
        }
        return true;
    }


    public CaseParc createCaseAndCollectData() {

        Log.d(LOG_TAG, "createCaseAndCollectData() for " + (newCase ? "newCase" : "updateCase"));

       List<CaseDataParc> newCaseDataList = collectCaseData();

        // physician fragment
        PhysicianParc physicianSelection = null;
        if (newCase) {
            physicianSelection = physicianSelectionFragment.getSelectedPhysician();
        }

        // finish fragment
        String caseName = null;
        if (newCase) {
            caseName = finishFragment.getCaseName();
        }

        Calendar timestamp = Calendar.getInstance();

        if (newCase) {
            caseItem.setName(caseName);
            caseItem.setPhysician(physicianSelection);
        }

        for(CaseDataParc cD : newCaseDataList) {
            caseItem.addDataElement(cD);
        }

        Log.d(LOG_TAG, "caseItem - dataElements.size()=" + caseItem.getDataElements().size());

        return caseItem;
    }


    private ArrayList<CaseDataParc> collectCaseData() {
        Log.d(LOG_TAG, "collectCaseData() for " + (newCase ? "newCase" : "updateCase"));

        ArrayList<CaseDataParc> newCaseDataElements = new ArrayList<>();

        // get data from symptom-fragment
        String symptomDescription = symptomsFragment.getSymptomDescription();
        PainIntensity painIntensity = symptomsFragment.getPainIntensity();
        double size = symptomsFragment.getSize();

        // get data from picture-fragment
        List<PictureHelperEntity> pictures = picturesFragment.getPictures();

        // get data from location-fragment
        List<BodyLocalization> localizations = locationFragment.getSelectedBodyLocalizations();

        // anamnesis data
        AnamnesisParc anamnesisForm = null;
        if (newCase) {
            anamnesisForm = anamnesisFragmen.getFilledAnamnesis();
        }

        Calendar timestamp = Calendar.getInstance();

        // CaseInfo
        CaseInfoParc caseInfo = new CaseInfoParc(-1, timestamp,
                caseItem.getPatient(), localizations, painIntensity, size, symptomDescription);


        newCaseDataElements.add(caseInfo);

        // add picture elements
        for (PictureHelperEntity p : pictures) {
            // bitmap to byte array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            p.getThumbnail().compress(Bitmap.CompressFormat.JPEG, 100, stream); // TODO full size image instead of thumbnail
            byte[] byteArray = stream.toByteArray();
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // PictureHelperEntity to PhotoMessage
            PhotoMessageParc photoMessageParc = new PhotoMessageParc(-1,
                    timestamp, caseItem.getPatient(), "JPEG", byteArray);
            // add photoMessage to caseItem
            newCaseDataElements.add(photoMessageParc);

            // put the description in a following TextMessage
            String picDescriptionStr = p.getDescription();
            if (picDescriptionStr.trim().length() > 0) {
                picDescriptionStr = "Picture: \n" + picDescriptionStr;
                TextMessageParc photoRelatedMessageParc = new TextMessageParc(-1,
                        timestamp, caseItem.getPatient(), picDescriptionStr);
                newCaseDataElements.add(photoRelatedMessageParc);
            }


        }

        // add Anamnesis element
        if (newCase && anamnesisForm != null) {
            newCaseDataElements.add(anamnesisForm);
        }
        return newCaseDataElements;

    }

    /**
     * checks the case for missing information
     * @param caseItemToValidate
     * @return
     */
    public List<CaseValidationError> validateCase(CaseParc caseItemToValidate) {
        List<CaseValidationError> validationErrors = new ArrayList<>();

        CaseDataExtractionHelper<CaseInfoParc> caseInfoExtractor = new CaseDataExtractionHelper<>(CaseInfoParc.class);
        List<CaseInfoParc> caseInfos = caseInfoExtractor.extractElements(caseItemToValidate.getDataElements());
        if (caseInfos.size() > 0) {
            CaseInfoParc lastCaseInfo = caseInfos.get(0);

            // symptom description?
            String symptomDescription = lastCaseInfo.getSymptomDescription();
            int minimalDescriptionLength = 1;
            if (symptomDescription == null || symptomDescription.trim().length() < minimalDescriptionLength) {
                validationErrors.add(new CaseValidationError(
                        getString(R.string.msg_validation_error_description), CaseValidationErrorLevel.ERROR));
            }

            // pain-intensity selected?
            PainIntensity painIntensity = lastCaseInfo.getPain();
            if (painIntensity == null || painIntensity == PainIntensity.Undefined) {
                validationErrors.add(new CaseValidationError(
                        getString(R.string.msg_validation_error_pain), CaseValidationErrorLevel.WARNING));
            }

            // size selected?
            double sizeD = lastCaseInfo.getSize();
            if (sizeD <= 0) {
                validationErrors.add(new CaseValidationError(
                        getString(R.string.msg_validation_error_size), CaseValidationErrorLevel.WARNING));
            }

            //  >0 locations selected
            List<BodyLocalization> localizations = lastCaseInfo.getLocalizations();
            if (localizations.size() == 0) {
                validationErrors.add(new CaseValidationError(
                        getString(R.string.msg_validation_error_localizations), CaseValidationErrorLevel.ERROR));
            }
        }

        // case name set?
        String name = caseItemToValidate.getName();
        if (name == null || name.trim().length() == 0) {
            validationErrors.add(new CaseValidationError(
                    getString(R.string.msg_validation_error_name), CaseValidationErrorLevel.ERROR));
        }

        // >0 pictures added?
        CaseDataExtractionHelper<PhotoMessageParc> photoMessageExtractor = new CaseDataExtractionHelper<>(PhotoMessageParc.class);
        List<PhotoMessageParc> pictures = photoMessageExtractor.extractElements(caseItemToValidate.getDataElements());
        if (pictures.size() == 0) {
            validationErrors.add(new CaseValidationError(
                    getString(R.string.msg_validation_error_pictures), CaseValidationErrorLevel.WARNING));
        }

        // all anamnesis-questions answered?
        CaseDataExtractionHelper<AnamnesisParc> anamnesisDataExtractor = new CaseDataExtractionHelper<>(AnamnesisParc.class);
        List<AnamnesisParc> anamnesisList = anamnesisDataExtractor.extractElements(caseItemToValidate.getDataElements());
        if (anamnesisList.size() > 0) {
            AnamnesisParc lastAnamnesis = anamnesisList.get(0);
            // TODO is it really a good idea to test all questions for answers? TODO
//            validationErrors.add(new CaseValidationError(
//                    getString(R.string.msg_validation_error_pictures), CaseValidationErrorLevel.WARNING));
        }


        return validationErrors;
    }


    /**
     * starts a async-task and send the data to the server
     */
    private void sendDataToServer(CaseParc caseItem) {
        sendingEditedCaseAsyncTask = new SendingEditedCaseAsyncTask(this);
        sendingEditedCaseAsyncTask.execute(caseItem);
    }

    private void finishActivity() {
        this.finish();
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

    @Override
    public List<BodyLocalization> getBodyLocations() {
        CaseDataExtractionHelper<CaseInfoParc> infoExtractor = new CaseDataExtractionHelper<>(CaseInfoParc.class);
        List<CaseInfoParc> infoList = infoExtractor.extractElements(caseItem.getDataElements());
        if (infoList.size() > 0) {
            return infoList.get(0).getLocalizations();
        }
        return new ArrayList<BodyLocalization>();
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class LoadPhysicianListAsyncTask extends AsyncTask<Void, Void, List<PhysicianParc>> {
        private EditCaseActivity activity;

        private String outp;

        LoadPhysicianListAsyncTask(EditCaseActivity activity) {
            this.activity = activity;
        }

        @Override
        protected List<PhysicianParc> doInBackground(Void... params) {
            Log.d(LOG_TAG, "doInBackground()");

            List<PhysicianParc> physicianList = new ArrayList<>();

            ServerInterface sI = ServerInterfaceFactory.getInstance();
            // TODO

            // TODO remoeve #####
            ContentProvider cP = ContentProviderFactory.getContentProvider();
            physicianList = cP.getNearbyPhysicians(cP.getCurrentUser().getLocation());
            try {
                Thread.sleep(3000);                 //1000 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            Log.d(LOG_TAG, "retrieved physicians: " + "TODO");

            return physicianList;
        }

        @Override
        protected void onPostExecute(final List<PhysicianParc> physicianList) {
            Log.d(LOG_TAG,"onPostExecute() physicianList: " + (physicianList != null ? physicianList.size() : null));
            nearbyPhysicians = physicianList;
            loadPhysicianListAsyncTask = null;
            showProgress(false);
            // TODO check result etc.
        }

        @Override
        protected void onCancelled() {
            loadPhysicianListAsyncTask = null;
            showProgress(false);
        }
    }

    /**
     * sending the case to the server
     * the user.
     */
    public class SendingEditedCaseAsyncTask extends AsyncTask<CaseParc, Void, Boolean> {
        private EditCaseActivity activity;

        private String outp;

        SendingEditedCaseAsyncTask(EditCaseActivity activity) {
            this.activity = activity;
        }

        @Override
        protected Boolean doInBackground(CaseParc... params) {
            Log.d(LOG_TAG, "doInBackground()");

            ServerInterface sI = ServerInterfaceFactory.getInstance();
            // TODO

            // TODO remoeve
            try {
                Thread.sleep(3000);                 //1000 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            Log.d(LOG_TAG, "end doInBackground()");
            return null;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            Log.d(LOG_TAG,"onPostExecute() " + success);
            sendingEditedCaseAsyncTask = null;
            showProgress(false);
            Toast.makeText(getBaseContext(), "Data was sent to the server", Toast.LENGTH_LONG).show();
            finishActivity();

        }

        @Override
        protected void onCancelled() {
            sendingEditedCaseAsyncTask = null;
            showProgress(false);
            Toast.makeText(getBaseContext(), "Sending data to the server was cancelled", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public double getSize() {
        CaseDataExtractionHelper<CaseInfoParc> infoExtractor = new CaseDataExtractionHelper<>(CaseInfoParc.class);
        List<CaseInfoParc> infoList = infoExtractor.extractElements(caseItem.getDataElements());
        if (infoList.size() > 0) {
            return infoList.get(0).getSize();
        }

        return 0.0;
    }

    @Override
    public String getSymptomDescription() {
        CaseDataExtractionHelper<CaseInfoParc> infoExtractor = new CaseDataExtractionHelper<>(CaseInfoParc.class);
        List<CaseInfoParc> infoList = infoExtractor.extractElements(caseItem.getDataElements());
        if (infoList.size() > 0) {
            return infoList.get(0).getSymptomDescription();
        }
        return "";
    }

    @Override
    public PainIntensity getPainIntensity() {
        CaseDataExtractionHelper<CaseInfoParc> infoExtractor = new CaseDataExtractionHelper<>(CaseInfoParc.class);
        List<CaseInfoParc> infoList = infoExtractor.extractElements(caseItem.getDataElements());
        if (infoList.size() > 0) {
            return infoList.get(0).getPain();
        }
        return PainIntensity.Undefined;
    }
}
