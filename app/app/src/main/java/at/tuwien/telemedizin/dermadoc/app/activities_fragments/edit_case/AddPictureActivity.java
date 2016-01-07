package at.tuwien.telemedizin.dermadoc.app.activities_fragments.edit_case;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.case_specific.CaseActivity;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case.EditPicturesFragment;
import at.tuwien.telemedizin.dermadoc.app.entities.PictureHelperEntity;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.PatientParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.CaseDataParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.PhotoMessageParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.TextMessageParc;


public class AddPictureActivity extends AppCompatActivity {

    public static final String LOG_TAG = AddPictureActivity.class.getSimpleName();

    public static final String PICTURE_LIST_INTENT = AddPictureActivity.class.getSimpleName() + "pictureList";
    public static final int PICTURES_RESULT_INTENT_KEY = 1;

    private EditPicturesFragment picturesFragment;

    private LinearLayout mainContentLayout;
    private RelativeLayout loadingProgressLayout;
    private TextView loadingProgressInfoTextView;

    private FloatingActionButton fab;

    private PatientParc currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_picture_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();
        Parcelable currentUserParcel = intent.getParcelableExtra(CaseActivity.USER_INTENT_KEY);

        if (currentUserParcel != null) {
//            Log.d(LOG_TAG, "case-parcelable in intent != null -> casting");
            currentUser = (PatientParc) currentUserParcel;
        }


        // default content is case-list
        picturesFragment = EditPicturesFragment.newInstance(true, true);
        String title = getString(R.string.nav_title_add_pictures);

        mainContentLayout = (LinearLayout) findViewById(R.id.contentFrame);

        if (picturesFragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.contentFrame, picturesFragment);
            fragmentTransaction.commit();
            setTitle(title);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(LOG_TAG, "onActivityResult() requestCode: " + requestCode + ", resultCode: " + resultCode);


    }


    /**
     * @param pictureToBeModified the picture-object which should be modified and replaced in the list
     *                            Sent for identification-purpose
     * @param description         the new description
     */
    public void notifyEditPictureDescription(PictureHelperEntity pictureToBeModified, String description) {
        Log.d(LOG_TAG, "notifyEditPictureDescription()");
        if (picturesFragment != null) {
            picturesFragment.modifyDescriptionOfPicture(pictureToBeModified, description);
        }
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    public void collectPictureDataAndFinish(){
        List<PictureHelperEntity> pictures = picturesFragment.getPictures();

        // make PhotoMessages
        ArrayList<CaseDataParc> photoRelatedMessages = new ArrayList<CaseDataParc>();

        for (PictureHelperEntity pH : pictures) {

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            pH.getThumbnail().compress(Bitmap.CompressFormat.JPEG, 100, stream); // TODO full size image instead of thumbnail
            byte[] byteArray = stream.toByteArray();
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            PhotoMessageParc pM = new PhotoMessageParc(-1, Calendar.getInstance(), currentUser, "JPEG", byteArray);
            photoRelatedMessages.add(pM);

            String picDescriptionStr = pH.getDescription();
            if (picDescriptionStr.trim().length() > 0) {
                picDescriptionStr = "Picture: \n" + picDescriptionStr;
                TextMessageParc pDescription = new TextMessageParc(-1, Calendar.getInstance(), currentUser,picDescriptionStr);
                photoRelatedMessages.add(pDescription);
            }




        }

        Intent resultData = new Intent();
        resultData.putParcelableArrayListExtra(PICTURE_LIST_INTENT, photoRelatedMessages);
        setResult(RESULT_OK, resultData);
        finish();
    }


}
