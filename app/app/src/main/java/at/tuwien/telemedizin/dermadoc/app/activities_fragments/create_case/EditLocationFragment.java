package at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.adapters.BodyLocalizationMapper;
import at.tuwien.telemedizin.dermadoc.app.entities.BodyLocalizationZoomHelper;
import at.tuwien.telemedizin.dermadoc.app.helper.ColorTool;
import at.tuwien.telemedizin.dermadoc.entities.BodyLocalization;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditLocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * implementation of clickable areas following: https://blahti.wordpress.com/2012/06/26/images-with-clickable-areas/
 */
public class EditLocationFragment extends Fragment implements View.OnTouchListener {

    public static final String LOG_TAG = EditLocationFragment.class.getSimpleName();

    private OnTabChangedInFragmentInterface tabChangeInterface;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NEW_CASE = "newCase";

    private boolean newCase; // if it is a new case, no symptom information has to be loaded and the layout switches into edit-mode
    private boolean addPicturesHintIsVisible;

    private FrameLayout locationImagesFrameLayout;
    private FrameLayout zoomedLocationImagesFrameLayout;

    private ImageView addLocationsHelpIcon;
    private TextView addLocationsHelpText;

    private List<BodyLocalization> selectedBodyParts;

    private Map<BodyLocalization, List<ImageView>> localizationToImageMap;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment EditSymptomsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditLocationFragment newInstance(boolean newCase) {
        EditLocationFragment fragment = new EditLocationFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_NEW_CASE, newCase);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public EditLocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            newCase = getArguments().getBoolean(ARG_NEW_CASE);
        }
        selectedBodyParts = new ArrayList<>(); // TODO fill with values, when this is not within a new case
        localizationToImageMap = new HashMap<>();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_location_layout, container, false);

        addLocationsHelpIcon = (ImageView) v.findViewById(R.id.edit_location_help_icon_view);
        addLocationsHelpText = (TextView) v.findViewById(R.id.edit_location_help_hint_text_view);

        addLocationsHelpIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make hint visible or hide it
                toggleAddLocationsHint();

            }
        });

        addLocationsHelpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make hint visible or hide it
                toggleAddLocationsHint();
            }
        });

        locationImagesFrameLayout = (FrameLayout) v.findViewById(R.id.location_image_frame_layout);
        ImageView mainLocationFrontImage = (ImageView) v.findViewById(R.id.main_location_picture_image_view);
        mainLocationFrontImage.setOnTouchListener(this);

        zoomedLocationImagesFrameLayout = (FrameLayout) v.findViewById(R.id.location_zoomed_frame_layout);

        // button to get to the next part
        Button nextButton = (Button) v.findViewById(R.id.next_section_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to the next section
                tabChangeInterface.switchToTheNextTab();
            }
        });

        // set up selected body parts
        if (selectedBodyParts.size() > 0) {
            // show the masks for the selected parts
            for (BodyLocalization bL : selectedBodyParts) {
                addImageViewsForSelectedBodyPart(bL);
            }

        }

        return v;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            tabChangeInterface = (OnTabChangedInFragmentInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement " + OnTabChangedInFragmentInterface.class.getSimpleName());
        }

    }


    private void toggleAddLocationsHint() {
        if (addPicturesHintIsVisible) {
            addLocationsHelpText.setVisibility(View.GONE);
            addLocationsHelpIcon.setImageResource(R.drawable.ic_action_help_holo_light_18dp);
            addPicturesHintIsVisible = false;
        } else {
            addLocationsHelpText.setVisibility(View.VISIBLE);
            addLocationsHelpIcon.setImageResource(R.drawable.ic_action_help_yellow_dark_18dp);
            addPicturesHintIsVisible = true;
        }
    }

    private void addBodyPartToSelection(BodyLocalization localization) {
        Log.d(LOG_TAG, "addBodyPartToSelection: " + localization);

        if (isBodyPartSelected(localization)) {
            Log.d(LOG_TAG, "this localization is already in the list");
            return;
        }
        selectedBodyParts.add(localization);
        addImageViewsForSelectedBodyPart(localization);

    }

    /**
     * adds images highlighting selected areas to the layout and
     * stores the newly created views in the map for later reference
     * @param localization
     */
    private void addImageViewsForSelectedBodyPart(BodyLocalization localization) {
        int[] imageMaskResources = BodyLocalizationMapper.getImageResource(localization);
        // multiple images (front + back) can be associated with one localization
        List<ImageView> imageMaskList = new ArrayList<>();
        for (int i = 0; i < imageMaskResources.length; i++) {
            // add image-view with the image-resource
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(imageMaskResources[i]);


            imageMaskList.add(imageView);
            locationImagesFrameLayout.addView(imageView);
        }
        localizationToImageMap.put(localization, imageMaskList); // for further access (modify visibility etc)
    }

    private void removeBodyPartFromSelection(BodyLocalization localization) {
        Log.d(LOG_TAG, "removeBodyPartFromSelection: " + localization);

        if (!isBodyPartSelected(localization)) {
            Log.d(LOG_TAG, "this localization is not in the list");
            return;
        }

        selectedBodyParts.remove(localization);
        List<ImageView> imageMaskList = localizationToImageMap.get(localization);
        // multiple images (front + back) can be associated with one localization

        for (ImageView iV : imageMaskList) {
            locationImagesFrameLayout.removeView(iV);
        }
        localizationToImageMap.remove(localization);
    }

    private boolean isBodyPartSelected(BodyLocalization localization) {
        return selectedBodyParts.contains(localization);
    }

    /**
     * checks for the localization, if it is already selected or not
     * if it is selected, the localization is deselected and removed from the list of selected Body-Localizations
     * if it is not selected yet, the localization is selected and added to the list of selected Body-Localizations
     *
     * @param localization
     */
    private void handleClickOnBodyLocalization(BodyLocalization localization) {
        // checks if the localization is already selected; if not -> select it now
        if (isBodyPartSelected(localization)) {
            removeBodyPartFromSelection(localization);
        } else {
            addBodyPartToSelection(localization);
        }
    }


    /**
     * shows a selection-dialog to the user
     * The user selects where he wants to take the picture from: Camera or gallery
     *
     * see: http://www.theappguruz.com/blog/android-take-photo-camera-gallery-code-sample
     */
    private void selectImageSource() {

        final String optionTakePhoto = getString(R.string.option_take_photo);
        final String optionChooseFromLibrary = getString(R.string.option_choose_from_library);
        final String optionCancel = getString(R.string.option_cancel);
        final String[] textItems = new String[]{
                optionTakePhoto,
                optionChooseFromLibrary,
                optionCancel
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_choose_photo_source_dialog);
        builder.setItems(textItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (textItems[which].equals(optionTakePhoto)) {
                    // open camera app
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // ensure, that some app can handle this intent - this app would crash otherwise
                    if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                        startActivityForResult(intent, NewCaseActivity.REQUEST_CAMERA);
                    }
                } else if (textItems[which].equals(optionChooseFromLibrary)) {
                    // open galery
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            NewCaseActivity.SELECT_FILE);
                } else if (textItems[which].equals(optionCancel)) {
                    // cancel operation
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(LOG_TAG, "onActivityResult() requestCode: " + requestCode + ", resultCode: " + resultCode);

        if (resultCode == NewCaseActivity.RESULT_OK) {

        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(LOG_TAG, "onTouch() " + v.toString());

        final int action = event.getAction();
        // (1)
        final int evX = (int) event.getX();
        final int evY = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN :

                break;
            case MotionEvent.ACTION_UP :
                Log.d(LOG_TAG, "MotionEvent=ACTION_UP" );

                handleBodyAreaTouchActionUP(evX, evY);

                break;
        } // end switch

        return true;
    }

    private void handleBodyAreaTouchActionUP(int evX, int evY) {
        // On the UP, we do the click action.
        // The hidden image (image_areas) has 4 different hotspots on it.
        // The colors are blue, green, yellow, orange, red
        // Use image_areas to determine which region the user touched.
        // (2)
        int touchColor = getHotspotColor (R.id.location_helper_image_view, evX, evY);
        // Compare the touchColor to the expected values.
        // Switch to a different image, depending on what color was touched.
        // Note that we use a Color Tool object to test whether the
        // observed color is close enough to the real color to
        // count as a match. We do this because colors on the screen do
        // not match the map exactly because of scaling and
        // varying pixel density.
        ColorTool ct = new ColorTool ();
        int tolerance = 25;

        BodyLocalizationZoomHelper touchedBodyArea = null;

        // (3)
        if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_head), touchColor, tolerance)) { // TODO
            // Do the action associated with the head-area
//            Toast.makeText(getContext(), "HEAD CLICKED", Toast.LENGTH_LONG).show(); // TODO replace with real fragment/function
            touchedBodyArea = BodyLocalizationZoomHelper.HEAD;

        } else if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_legs), touchColor, tolerance)) { // TODO
            // Do the action associated with the leg-area
//            Toast.makeText(getContext(), "LEGS CLICKED", Toast.LENGTH_LONG).show(); // TODO replace with real fragment/function
            touchedBodyArea = BodyLocalizationZoomHelper.LEGS;

        } else if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_arm_left), touchColor, tolerance)) { // TODO
            // Do the action associated with the arm-left-area
//            Toast.makeText(getContext(), "ARM LEFT CLICKED", Toast.LENGTH_LONG).show(); // TODO replace with real fragment/function
            touchedBodyArea = BodyLocalizationZoomHelper.ARM_LEFT;

        } else if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_arm_right), touchColor, tolerance)) { // TODO
            // Do the action associated with the arm-right-area
//            Toast.makeText(getContext(), "ARM RIGHT CLICKED", Toast.LENGTH_LONG).show(); // TODO replace with real fragment/function
            touchedBodyArea = BodyLocalizationZoomHelper.ARM_RIGHT;

        } else if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_torso), touchColor, tolerance)) { // TODO
            // Do the action associated with the torso-area
//            Toast.makeText(getContext(), "TORSO CLICKED", Toast.LENGTH_LONG).show(); // TODO replace with real fragment/function
            touchedBodyArea = BodyLocalizationZoomHelper.TORSO;

        } else if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_back), touchColor, tolerance)) { // TODO
            // Do the action associated with the back-area
//            Toast.makeText(getContext(), "BACK CLICKED", Toast.LENGTH_LONG).show(); // TODO replace with real fragment/function
            touchedBodyArea = BodyLocalizationZoomHelper.BACK;

        }else {
            //...
            Log.d(LOG_TAG, "ColorTool: No match" );
        }

        // TODO: show a zoomed picture of the selected areas

        // TEST: select body-parts from the main view
        // TODO remove Start
        if (touchedBodyArea != null) {
            List<BodyLocalization> selectedBodyLocations =
                    BodyLocalizationMapper.getBodyLocalizations(touchedBodyArea);
            for (BodyLocalization bL : selectedBodyLocations) {
                handleClickOnBodyLocalization(bL);
            }
        }
        // TODO remove END
    }

    public int getHotspotColor (int hotspotId, int x, int y) {
        ImageView img = (ImageView) locationImagesFrameLayout.findViewById(hotspotId);
        img.setDrawingCacheEnabled(true);
        Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
        img.setDrawingCacheEnabled(false);
        return hotspots.getPixel(x, y);
    }
}
