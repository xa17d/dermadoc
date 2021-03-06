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
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.UserParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AnamnesisParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.CaseDataParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.CaseInfoParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.PhotoMessageParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.TextMessageParc;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Case;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Physician;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.app.helper.CaseDataExtractionHelper;
import at.tuwien.telemedizin.dermadoc.app.helper.ConnectionDetector;
import at.tuwien.telemedizin.dermadoc.app.helper.ParcelableHelper;
import at.tuwien.telemedizin.dermadoc.app.helper.ToStringHelper;
import at.tuwien.telemedizin.dermadoc.app.persistence.ContentProvider;
import at.tuwien.telemedizin.dermadoc.app.persistence.ContentProviderFactory;
import at.tuwien.telemedizin.dermadoc.app.server_interface.ServerInterface;
import at.tuwien.telemedizin.dermadoc.app.server_interface.ServerInterfaceFactory;
import at.tuwien.telemedizin.dermadoc.app.general_entities.BodyLocalization;
import at.tuwien.telemedizin.dermadoc.app.general_entities.PainIntensity;


public class EditCaseActivity extends AppCompatActivity implements OnCaseDataRequestAndUpdateInterface,
        OnTabChangedInFragmentInterface, EditSymptomsFragment.SymptomsSourceInterface,
        EditLocationFragment.BodyLocationCallbackInterface {

    public static final String LOG_TAG = EditCaseActivity.class.getSimpleName();

    public static final String NEW_CASE_FLAG_INTENT_KEY = EditCaseActivity.class.getSimpleName() + "newCase";
    public static final String CASE_FLAG_INTENT_KEY = EditCaseActivity.class.getSimpleName() + "case";
    public static final String USER_FLAG_INTENT_KEY = EditCaseActivity.class.getSimpleName() + "user";
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
        PatientParc cUser = null;

        if (!newCase) {
            // get Case info
            Parcelable caseParcel = intent.getParcelableExtra(CASE_FLAG_INTENT_KEY);
            if (caseParcel != null) {
                Log.d(LOG_TAG, "caseParcel != null");
                caseItem = (CaseParc) caseParcel;
                cUser = caseItem.getPatient();
            }
        } else {
            // get Case info
            Parcelable userParcel = intent.getParcelableExtra(USER_FLAG_INTENT_KEY);
            if (userParcel != null) {
                Log.d(LOG_TAG, "userParcel != null");
                cUser = (PatientParc) userParcel;
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
        setUpData(cUser);
    }

    private void addFragmentsToAdapter(NewCasePagerAdapter adapter, boolean bNewCase) {
        Log.d(LOG_TAG, "addFragmentsToAdapter(newCase= " + bNewCase + ")");

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

    private void setUpData(PatientParc cUser) {
        ContentProvider cP = ContentProviderFactory.getContentProvider();

        defaultAnamnesis = cP.getAnamnesisForm(); // TODO get data from server?
        Calendar cal = Calendar.getInstance();

        if (newCase) {
            caseItem = new CaseParc(null, cUser, cal);
        }

        nearbyPhysicians = new ArrayList<>();
        if (newCase) {
            loadPhysicianList();
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
        Log.d(LOG_TAG, "finishEditing() newCase= " + newCase);
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

        Calendar timestamp = Calendar.getInstance();

        // anamnesis data
        AnamnesisParc anamnesisForm = null;
        if (newCase) {
            AnamnesisParc anamnesisFormDummy= anamnesisForm = anamnesisFragmen.getFilledAnamnesis();
            if (anamnesisFormDummy != null) {
                // setting the user null for DB/Server reasons
                anamnesisForm = new AnamnesisParc(null, timestamp, null,
                        anamnesisFormDummy.getMessage(), anamnesisFormDummy.getQuestions());
            }

        }

        // CaseInfo
        CaseInfoParc caseInfo = new CaseInfoParc(null, timestamp,
                caseItem.getPatient(), localizations, painIntensity, size);


        newCaseDataElements.add(caseInfo);

        // symptom description as TextMessage
        TextMessageParc symptomMessageParc = new TextMessageParc(null,
                timestamp, caseItem.getPatient(), getString(R.string.label_symtom_description_text_header, symptomDescription));
        newCaseDataElements.add(symptomMessageParc);

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
            PhotoMessageParc photoMessageParc = new PhotoMessageParc(null,
                    timestamp, caseItem.getPatient(), "JPEG", byteArray);
            // add photoMessage to caseItem
            newCaseDataElements.add(photoMessageParc);

            // put the description in a following TextMessage
            String picDescriptionStr = p.getDescription();
            if (picDescriptionStr.trim().length() > 0) {
                picDescriptionStr = "Picture: \n" + picDescriptionStr;
                TextMessageParc photoRelatedMessageParc = new TextMessageParc(null,
                        timestamp, caseItem.getPatient(), picDescriptionStr);
                newCaseDataElements.add(photoRelatedMessageParc);
            }


        }

        // add Anamnesis element
        if (newCase && anamnesisForm != null) {
            Log.d(LOG_TAG, "anamnesisForm: " + ToStringHelper.toString(anamnesisForm));
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

        // symptom description?
        CaseDataExtractionHelper<TextMessageParc> textMessageExtractor = new CaseDataExtractionHelper<>(TextMessageParc.class);
        List<TextMessageParc> allTextMessages = textMessageExtractor.extractElements(caseItemToValidate.getDataElements());

        boolean symptDfound = false;
        for(TextMessageParc m : allTextMessages) {
            // check, if the key-phrase is in one of the messages
            if (m.getMessage() != null && m.getMessage().contains(getString(R.string.symptom_description_validation_helper_text))) {
                // the key-phrase was found = there is at least one symptom description - element
                symptDfound = true;
                // validate it
                String symptomDescription = m.getMessage();
                int minimalDescriptionLength = 5;
                int minimalLength = minimalDescriptionLength + getString(R.string.symptom_description_validation_helper_text).length();
                if (symptomDescription == null || symptomDescription.trim().length() < minimalLength) {
                    validationErrors.add(new CaseValidationError(
                            getString(R.string.msg_validation_error_description), CaseValidationErrorLevel.ERROR));
                }
            }
        }
        if (!symptDfound) {
            // no descripton element -> error
            validationErrors.add(new CaseValidationError(
                    getString(R.string.msg_validation_error_description), CaseValidationErrorLevel.ERROR));
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

        // check internet connection
        if (!checkInternetConnection()) {
            return;
        }

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

    private void syncData() {
        // send data TODO
        // get data
        getDataFromServer();
    }

    private void getDataFromServer() {
        // load Case Data

        if (newCase) {
            loadPhysicianList();
        }

    }

    private void loadPhysicianList() {
        Log.d(LOG_TAG, "loadPhysicianList()");

        // check internet connection
        if (!checkInternetConnection()) {
            return;
        }

        String infoText = getString(R.string.label_loading_data_dynamic, getString(R.string.option_loading_physician_list_insert));
        showProgress(true, infoText);
        loadPhysicianListAsyncTask = new LoadPhysicianListAsyncTask(this);
        loadPhysicianListAsyncTask.execute();
    }

    /**
     * sets the task = null and deactivates showProgess, if all tasks are null
     * @param task
     */
    private synchronized void asyncTaskFinished(AsyncTask task) {
        if (task instanceof LoadPhysicianListAsyncTask) {
            loadPhysicianListAsyncTask = null;
            physicianSelectionFragment.updatePhysicianList();
        } else if (task instanceof SendingEditedCaseAsyncTask) {
            sendingEditedCaseAsyncTask = null;
        }

        if (loadPhysicianListAsyncTask == null && sendingEditedCaseAsyncTask == null) {
            showProgress(false);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class LoadPhysicianListAsyncTask extends AsyncTask<Void, Void, List<Physician>> {
        private EditCaseActivity activity;

        private String outp;

        LoadPhysicianListAsyncTask(EditCaseActivity activity) {
            this.activity = activity;
        }

        @Override
        protected List<Physician> doInBackground(Void... params) {
            Log.d(LOG_TAG, "doInBackground()");

            List<Physician> physicianList = new ArrayList<>();

            ServerInterface sI = ServerInterfaceFactory.getInstance();
            physicianList = sI.getPhysicians();

            if (physicianList != null) {
                Log.d(LOG_TAG, "retrieved physicians: " + physicianList.size());
            } else {
                Log.d(LOG_TAG, "physicianList == null");
            }

            return physicianList;
        }

        @Override
        protected void onPostExecute(final List<Physician> physicianList) {
            Log.d(LOG_TAG,"onPostExecute() physicianList: " + (physicianList != null ? physicianList.size() : null));
            nearbyPhysicians = ParcelableHelper.mapPhysicianListToParc(physicianList);
            asyncTaskFinished(LoadPhysicianListAsyncTask.this);
        }

        @Override
        protected void onCancelled() {
            asyncTaskFinished(LoadPhysicianListAsyncTask.this);
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

            if (params == null || params.length <= 0) {
                Log.d(LOG_TAG, "Insufficient Parameters! ABORT!");
                return false;
            }
            CaseParc caseParcToSend = params[0];

            ServerInterface sI = ServerInterfaceFactory.getInstance();
            // TODO send case, retrieve new case-id
            Case caseToSend = ParcelableHelper.mapToCase(caseParcToSend);
            Case caseResultFromServer = null;
            Long caseId = null;
            // check if it is a new case that should be created or an old case, which data should be sent
            if (newCase) {
                caseResultFromServer = sI.createCase(caseToSend);

                if (caseResultFromServer == null) {
                    Log.d(LOG_TAG, "null returned from server for case");
                    return false;
                } else {
                    Log.d(LOG_TAG, "case returned from server with Id: " + caseResultFromServer.getId());
                    caseId = caseResultFromServer.getId();
                }
            } else {
                caseId = caseToSend.getId(); // "old" cases already have IDs

            }

            // send case-data elements
            List<CaseData> caseDataToSend = ParcelableHelper.mapToCaseDataList(caseParcToSend.getDataElements());
            if (caseDataToSend == null) {
                Log.d(LOG_TAG, "CaseData - List is nulL! ABORT");
                return false;
            }


            boolean successfulSentAllCaseData = true;
            for (CaseData cd : caseDataToSend) {
                CaseData resultFromServerCaseData = sI.addCaseData(cd, caseId); // TODO check result?

                if (resultFromServerCaseData == null) {
                    Log.d(LOG_TAG, "null returned from server for caseData");
                    successfulSentAllCaseData = false;
                } else {
                    Log.d(LOG_TAG, "caseData returned from server with Id: " + resultFromServerCaseData.getId());
                }
            }



            Log.d(LOG_TAG, "end doInBackground()");
            return successfulSentAllCaseData;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            Log.d(LOG_TAG,"onPostExecute() " + success);
            asyncTaskFinished(SendingEditedCaseAsyncTask.this);
            Toast.makeText(getBaseContext(), "Data was sent to the server", Toast.LENGTH_LONG).show();
            finishActivity();

        }

        @Override
        protected void onCancelled() {
            asyncTaskFinished(SendingEditedCaseAsyncTask.this);
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
    public PainIntensity getPainIntensity() {
        CaseDataExtractionHelper<CaseInfoParc> infoExtractor = new CaseDataExtractionHelper<>(CaseInfoParc.class);
        List<CaseInfoParc> infoList = infoExtractor.extractElements(caseItem.getDataElements());
        if (infoList.size() > 0) {
            return infoList.get(0).getPain();
        }
        return PainIntensity.Undefined;
    }
}
