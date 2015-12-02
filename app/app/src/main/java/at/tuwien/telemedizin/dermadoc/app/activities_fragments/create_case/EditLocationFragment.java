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
public class EditLocationFragment extends Fragment {

    public static final String LOG_TAG = EditLocationFragment.class.getSimpleName();

    private OnTabChangedInFragmentInterface tabChangeInterface;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NEW_CASE = "newCase";

    private boolean newCase; // if it is a new case, no symptom information has to be loaded and the layout switches into edit-mode
    private boolean addPicturesHintIsVisible;

    private FrameLayout locationImagesFrameLayout;

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

        LocationImageTouchListener mainListener =
                new LocationImageTouchListener(R.id.location_helper_image_view, R.id.location_details_helper_image_view);
        mainLocationFrontImage.setOnTouchListener(mainListener);

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
        int[] imageMaskResources = BodyLocalizationMapper.getImageMasksResource(localization);
        // multiple images (front + back) can be associated with one localization
        List<ImageView> imageMaskList = new ArrayList<>();
        for (int i = 0; i < imageMaskResources.length; i++) {
            // add image-view with the image-resource for the full-body-image
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


    /**
     * this touch-listener uses two helper-images to detect which body-localization was selected
     *
     * The coordinated of the touch on the main-picture are used to retreive the pixel-colors
     * of the underneath helper-images. The first helper image splits the image into the main
     * Areas (6 at most because of color variations). The detailed-helper-image splits each of the
     * main areas into more detailed ares, using the above mentioned 6 colors.
     *
     * First, the color of the pixel given by the touch-event on the this main-helper-image
     * is retrieved. Second, the color of the pixel on the detailed-helper-image are retrieved.
     * Third, the body-localization is calculated by using the combination of both colors.
     *
     * e.g.: Color for main-area "right-arm" is blue, colors for the detailed areas are:
     * "hand" - green, "forearm" - red, etc.
     * Touch -> first color is blue, second color is green -> must be the right hand
     *
     *
     *
     */
    private class LocationImageTouchListener implements View.OnTouchListener {

        private int locationHelperImageResourceId; // the image with the colored-areas
        private int locationHelperDetailedImageResourceId; // the image with the colored-areas for detailed detection

        private BodyLocalizationZoomHelper bodyArea; // if null: full body

        /**
         *
         * @param locationHelperImageResourceId the id of the image-helper resource in this layout (the image with the colored areas)
         * @param locationHelperDetailedImageResourceId the id of the image helper resource for detailed detection
         *
         */
        public LocationImageTouchListener(int locationHelperImageResourceId,
                                          int locationHelperDetailedImageResourceId) {
            this.locationHelperImageResourceId = locationHelperImageResourceId;
            this.locationHelperDetailedImageResourceId = locationHelperDetailedImageResourceId;
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
            int touchColor = getHotspotColor (locationHelperImageResourceId, evX, evY);
            int touchColorDetails = getHotspotColor (locationHelperDetailedImageResourceId, evX, evY);
            // Compare the touchColor to the expected values.
            // Switch to a different image, depending on what color was touched.
            // Note that we use a Color Tool object to test whether the
            // observed color is close enough to the real color to
            // count as a match. We do this because colors on the screen do
            // not match the map exactly because of scaling and
            // varying pixel density.
            ColorTool ct = new ColorTool ();
            int tolerance = 25;
            // full body
            BodyLocalizationZoomHelper mainBodyLocation = handleFullBodyArea(touchColor, tolerance, ct);

            if (mainBodyLocation != null) {
                // now get the detailed body-location
                BodyLocalization bodyLocalization = null;

                switch (mainBodyLocation) {
                    case HEAD:
                        bodyLocalization = handleHeadBodyArea(touchColorDetails, tolerance, ct);
                        break;
                    case TORSO:
                        bodyLocalization = handleTorsoBodyArea(touchColorDetails, tolerance, ct);
                        break;
                    case ARM_LEFT:
                        bodyLocalization = handleArmBodyArea(touchColorDetails, tolerance, ct, true);
                        break;
                    case ARM_RIGHT:
                        bodyLocalization = handleArmBodyArea(touchColorDetails, tolerance, ct, false);
                        break;
                    case BACK:
                        bodyLocalization = handleBackBodyArea(touchColorDetails, tolerance, ct);
                        break;
                    case LEGS:
                        bodyLocalization = handleLegBodyArea(touchColorDetails, tolerance, ct);
                        break;
                }

                // check if something was selected
                if (bodyLocalization != null) {
                    handleClickOnBodyLocalization(bodyLocalization);
                } else {
                    Log.d(LOG_TAG, "No body localization found");
                }

            } else {
                // nothing selected - touch was somewhere else
                return;
            }


        }

        private BodyLocalizationZoomHelper handleFullBodyArea(int touchColor, int tolerance, ColorTool ct) {
            BodyLocalizationZoomHelper touchedBodyArea = null;

            // (3)
            if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_1), touchColor, tolerance)) {
                // Do the action associated with the head-area
//            Toast.makeText(getContext(), "HEAD CLICKED", Toast.LENGTH_LONG).show();
                touchedBodyArea = BodyLocalizationZoomHelper.HEAD;

            } else if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_5), touchColor, tolerance)) {
                // Do the action associated with the leg-area
//            Toast.makeText(getContext(), "LEGS CLICKED", Toast.LENGTH_LONG).show();
                touchedBodyArea = BodyLocalizationZoomHelper.LEGS;

            } else if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_3), touchColor, tolerance)) {
                // Do the action associated with the arm-left-area
//            Toast.makeText(getContext(), "ARM LEFT CLICKED", Toast.LENGTH_LONG).show();
                touchedBodyArea = BodyLocalizationZoomHelper.ARM_LEFT;

            } else if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_4), touchColor, tolerance)) {
                // Do the action associated with the arm-right-area
//            Toast.makeText(getContext(), "ARM RIGHT CLICKED", Toast.LENGTH_LONG).show();
                touchedBodyArea = BodyLocalizationZoomHelper.ARM_RIGHT;

            } else if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_2), touchColor, tolerance)) {
                // Do the action associated with the torso-area
//            Toast.makeText(getContext(), "TORSO CLICKED", Toast.LENGTH_LONG).show();
                touchedBodyArea = BodyLocalizationZoomHelper.TORSO;

            } else if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_6), touchColor, tolerance)) {
                // Do the action associated with the back-area
//            Toast.makeText(getContext(), "BACK CLICKED", Toast.LENGTH_LONG).show();
                touchedBodyArea = BodyLocalizationZoomHelper.BACK;

            } else {
                //...
                Log.d(LOG_TAG, "ColorTool: No match" );
            }

            // TODO: show a zoomed picture of the selected areas

            return touchedBodyArea;
        }

        private BodyLocalization handleHeadBodyArea(int touchColor, int tolerance, ColorTool ct) {
            BodyLocalization touchedBodyArea = null;

           // head does only provide one area
            touchedBodyArea = BodyLocalization.HEAD;

            return touchedBodyArea;
        }

        private BodyLocalization handleTorsoBodyArea(int touchColor, int tolerance, ColorTool ct) {
            BodyLocalization touchedBodyArea = null;

            if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_2), touchColor, tolerance)) {
                // front neck
                touchedBodyArea = BodyLocalization.NECK_FRONT;

            } else if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_1), touchColor, tolerance)) {
                // chest
                touchedBodyArea = BodyLocalization.THORAX;

            } else if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_4), touchColor, tolerance)) {
                // abdomen
                touchedBodyArea = BodyLocalization.ABDOMEN;

            } else if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_3), touchColor, tolerance)) {
                // pelivis
                touchedBodyArea = BodyLocalization.PELVIS;

            } else {
                //...
                Log.d(LOG_TAG, "ColorTool: No match" );
            }

            return touchedBodyArea;
        }

        private BodyLocalization handleBackBodyArea(int touchColor, int tolerance, ColorTool ct) {
            BodyLocalization touchedBodyArea = null;

            if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_6), touchColor, tolerance)) {
                // front neck
                touchedBodyArea = BodyLocalization.NECK;

            } else if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_5), touchColor, tolerance)) {
                // chest
                touchedBodyArea = BodyLocalization.UPPER_BACK;

            } else if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_1), touchColor, tolerance)) {
                // abdomen
                touchedBodyArea = BodyLocalization.LOWER_BACK;

            } else if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_2), touchColor, tolerance)) {
                // pelivis
                touchedBodyArea = BodyLocalization.PELVIS_BACK;

            } else {
                //...
                Log.d(LOG_TAG, "ColorTool: No match" );
            }

            return touchedBodyArea;
        }

        /**
         *
         * @param touchColor
         * @param tolerance
         * @param ct
         * @param left - is it the left arm? if false it is automatically assumed to be the right arm
         * @return
         */
        private BodyLocalization handleArmBodyArea(int touchColor, int tolerance, ColorTool ct, boolean left) {
            BodyLocalization touchedBodyArea = null;

            if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_4), touchColor, tolerance)) {
                if (left) {
                    touchedBodyArea = BodyLocalization.SHOULDER_LEFT;
                } else {
                    touchedBodyArea = BodyLocalization.SHOULDER_RIGHT;
                }


            } else if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_3), touchColor, tolerance)) {
                if (left) {
                    touchedBodyArea = BodyLocalization.UPPER_ARM_LEFT;
                } else {
                    touchedBodyArea = BodyLocalization.UPPER_ARM_RIGHT;
                }

            } else if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_5), touchColor, tolerance)) {
                if (left) {
                    touchedBodyArea = BodyLocalization.FOREARM_LEFT;
                } else {
                    touchedBodyArea = BodyLocalization.FOREARM_RIGHT;
                }

            } else if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_1), touchColor, tolerance)) {
                if (left) {
                    touchedBodyArea = BodyLocalization.HAND_LEFT;
                } else {
                    touchedBodyArea = BodyLocalization.HAND_RIGHT;
                }

            } else {
                //...
                Log.d(LOG_TAG, "ColorTool: No match" );
            }

            return touchedBodyArea;
        }

        /**
         *
         * @param touchColor
         * @param tolerance
         * @param ct
         * @return
         */
        private BodyLocalization handleLegBodyArea(int touchColor, int tolerance, ColorTool ct) {
            BodyLocalization touchedBodyArea = null;

            if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_5), touchColor, tolerance)) {
                touchedBodyArea = BodyLocalization.THIGH_RIGHT;


            } else if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_4), touchColor, tolerance)) {
                touchedBodyArea = BodyLocalization.LOWER_LEG_RIGHT;

            } else if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_2), touchColor, tolerance)) {
                touchedBodyArea = BodyLocalization.FOOT_RIGHT;

            } else if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_1), touchColor, tolerance)) {
                touchedBodyArea = BodyLocalization.THIGH_LEFT;

            } else if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_3), touchColor, tolerance)) {
                touchedBodyArea = BodyLocalization.LOWER_LEG_LEFT;

            } else if (ct.closeMatch (ContextCompat.getColor(getContext(), R.color.location_area_6), touchColor, tolerance)) {
                touchedBodyArea = BodyLocalization.FOOT_LEFT;

            } else {
                //...
                Log.d(LOG_TAG, "ColorTool: No match" );
            }

            return touchedBodyArea;
        }

        public int getHotspotColor (int hotspotId, int x, int y) {
            ImageView img = (ImageView) locationImagesFrameLayout.findViewById(hotspotId);
            img.setDrawingCacheEnabled(true);
            Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
            img.setDrawingCacheEnabled(false);
            return hotspots.getPixel(x, y);
        }
    }
}
