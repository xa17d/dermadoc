package at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.adapters.NewCasePagerAdapter;
import at.tuwien.telemedizin.dermadoc.app.adapters.NewCasePagerEnum;
import at.tuwien.telemedizin.dermadoc.app.entities.PictureHelperEntity;
import at.tuwien.telemedizin.dermadoc.app.helper.ToStringHelper;
import at.tuwien.telemedizin.dermadoc.app.persistence.ContentProvider;
import at.tuwien.telemedizin.dermadoc.app.persistence.ContentProviderFactory;
import at.tuwien.telemedizin.dermadoc.entities.BodyLocalization;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.PainIntensity;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.User;
import at.tuwien.telemedizin.dermadoc.entities.casedata.Anamnesis;

public class NewCaseActivity extends AppCompatActivity implements OnCaseDataRequestAndUpdateInterface, OnTabChangedInFragmentInterface {

    public static final String LOG_TAG = NewCaseActivity.class.getSimpleName();

    public static final int REQUEST_CAMERA = 1;
    public static final int SELECT_FILE = 102;


    private Case caseItem;
    private Anamnesis defaultAnamnesis;

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
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new NewCasePagerAdapter(this, getSupportFragmentManager());
        addFragmentsToAdapter(mSectionsPagerAdapter);

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

        setTitle(R.string.title_activity_create_case);

        // TODO change to this user
        setUpData();
    }

    private void addFragmentsToAdapter(NewCasePagerAdapter adapter) {

        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();

        // add fragments according to the NewCasePagerEnum
        symptomsFragment = EditSymptomsFragment.newInstance(true);
        fragmentList.add(symptomsFragment);
        titleList.add(getString(NewCasePagerEnum.SYMPTOMS.getTitleResId()));

        picturesFragment = EditPicturesFragment.newInstance(true);
        fragmentList.add(picturesFragment);
        titleList.add(getString(NewCasePagerEnum.PICTURE.getTitleResId()));

        locationFragment = EditLocationFragment.newInstance(true);
        fragmentList.add(locationFragment);
        titleList.add(getString(NewCasePagerEnum.LOCATION.getTitleResId()));

        anamnesisFragmen = EditAnamnesisFragment.newInstance();
        fragmentList.add(anamnesisFragmen);
        titleList.add(getString(NewCasePagerEnum.ANAMNESIS.getTitleResId()));

        physicianSelectionFragment = PhysicianSelectionFragment.newInstance();
        fragmentList.add(physicianSelectionFragment);
        titleList.add(getString(NewCasePagerEnum.PHYSICIAN.getTitleResId()));

        finishFragment = FinishEditingFragment.newInstance(true);
        fragmentList.add(finishFragment);
        titleList.add(getString(NewCasePagerEnum.FINISH_EDITING.getTitleResId()));

        adapter.addFragments(fragmentList, titleList);
    }

    private void setUpData() {
        ContentProvider cP = ContentProviderFactory.getContentProvider();

        defaultAnamnesis = cP.getAnamnesisForm();
        Patient user = cP.getCurrentUser();
        Calendar cal = Calendar.getInstance();

        caseItem = new Case(-1, user, cal);
    }

    /**
     * setup the tab-layout: add tabs
     * @param tabLayout
     */
    private void setupTabLayout(TabLayout tabLayout) {
////        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

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
    public Anamnesis getAnamnesisForm() {

        return defaultAnamnesis;
    }

    @Override
    public void updateAnamnesis(Anamnesis anamnesis) {
        // TODO
    }

    @Override
    public Case getCase() {
        return caseItem;
    }

    public void collectCaseData() {

        Log.d(LOG_TAG, "collectCaseData()");

        // get data from symptom-fragment
        String symptomDescription = symptomsFragment.getSymptomDescription();
        PainIntensity painIntensity = symptomsFragment.getPainIntensity();

        // get data from picture-fragment
        List<PictureHelperEntity> pictures = picturesFragment.getPictures();

        // get data from location-fragment
        List<BodyLocalization> localizations = locationFragment.getSelectedBodyLocalizations();

        // anamnesis data
        Anamnesis anamnesisForm = anamnesisFragmen.getFilledAnamnesis();

        // physician fragment
        Physician physicianSelection = physicianSelectionFragment.getSelectedPhysician();

        // finish fragment
        String caseName = finishFragment.getCaseName();

        caseItem.setName(caseName);
        caseItem.setPhysician(physicianSelection);

        Log.d(LOG_TAG, ToStringHelper.toString(caseItem));
        Log.d(LOG_TAG, ToStringHelper.toString(anamnesisForm));
        Log.d(LOG_TAG, "symptoms: " + symptomDescription + ", Pain: " + painIntensity);
        Log.d(LOG_TAG, ToStringHelper.toStringPics(pictures));
        Log.d(LOG_TAG, ToStringHelper.toStringLoc(localizations));

    }
}
