package at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.adapters.NewCasePagerAdapter;
import at.tuwien.telemedizin.dermadoc.app.adapters.NewCasePagerEnum;
import at.tuwien.telemedizin.dermadoc.app.entities.PictureHelperEntity;

public class NewCaseActivity extends AppCompatActivity implements OnTabChangedInFragmentInterface {

    public static final String LOG_TAG = NewCaseActivity.class.getSimpleName();

    public static final int REQUEST_CAMERA = 1;
    public static final int SELECT_FILE = 102;

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

    private PictureReceiver pictureReceiver;

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
        pictureReceiver = mSectionsPagerAdapter.addStandardFragments();

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
        getMenuInflater().inflate(R.menu.menu_create_case, menu);
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

    private void notifyPictureReceiver(PictureHelperEntity picture) {
        Log.d(LOG_TAG, "notifyPictureReceiver()");
        if (pictureReceiver != null) {
            pictureReceiver.receiveNewPicture(picture);
        }
    }

}
