package at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.adapters.CasePictureListAdapter;
import at.tuwien.telemedizin.dermadoc.app.adapters.EmbedableListViewUtility;
import at.tuwien.telemedizin.dermadoc.app.entities.PictureHelperEntity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditPicturesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditPicturesFragment extends Fragment implements PictureReceiver, CasePictureListAdapter.CasePictureClickedHandler {

    public static final String LOG_TAG = EditPicturesFragment.class.getSimpleName();

    private OnTabChangedInFragmentInterface tabChangeInterface;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NEW_CASE = "newCase";
    private PictureHelperEntity activePicture;

    private boolean newCase; // if it is a new case, no symptom information has to be loaded and the layout switches into edit-mode
    private boolean addPicturesHintIsVisible;


    private ImageView addPicturesHelpIcon;
    private TextView addPicturesHelpText;
    private ImageView imageView; // TODO remove - only for testing

    private Button addPictureButton;

    private ListView pictureList;
    private CasePictureListAdapter pictureListAdapter;
    private List<PictureHelperEntity> testPictures; // TODO remove - take the list from the case-object instead

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment EditSymptomsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditPicturesFragment newInstance(boolean newCase) {
        EditPicturesFragment fragment = new EditPicturesFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_NEW_CASE, newCase);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public EditPicturesFragment() {
        // Required empty public constructor
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            newCase = getArguments().getBoolean(ARG_NEW_CASE);
            // TODO remove - START - test data
            testPictures = new ArrayList<>();
            PictureHelperEntity testPicture1 = new PictureHelperEntity();
            testPicture1.setDescription("Test Picture!");
            // TODO remove - END
        }

    }


    /**
     * receive the newly taken/selected picture
     * @param pictureHE
     */
    @Override
    public void receiveNewPicture(PictureHelperEntity pictureHE) {
        imageView.setImageBitmap(pictureHE.getThumbnail());
        // TODO implement
    }

    @Override
    public void modifyDescriptionOfPicture(PictureHelperEntity pictureToBeModified, String description) {
        // replace the description
        int index = testPictures.indexOf(pictureToBeModified);
        testPictures.remove(index);
        pictureToBeModified.setDescription(description);
        testPictures.add(index, pictureToBeModified);
//        testPictures.get(index).setDescription(description);
        pictureListAdapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_pictures_layout, container, false);

        addPicturesHelpIcon = (ImageView) v.findViewById(R.id.edit_pictures_help_icon_view);
        addPicturesHelpText = (TextView) v.findViewById(R.id.edit_pictures_help_hint_text_view);

        addPicturesHelpIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make hint visible or hide it
                toggleAddPicturesHint();

            }
        });

        addPicturesHelpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make hint visible or hide it
                toggleAddPicturesHint();
            }
        });

        addPictureButton = (Button) v.findViewById(R.id.add_picture_button);
        addPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageSource();
            }
        });


        imageView = (ImageView) v.findViewById(R.id.image_view1);

        pictureList = (ListView) v.findViewById(R.id.case_picture_list);
        pictureList.setEmptyView(v.findViewById(android.R.id.empty));



        pictureListAdapter = new CasePictureListAdapter(getContext(), testPictures, this);

        pictureList.setAdapter(pictureListAdapter);
        EmbedableListViewUtility.setListViewHeightBasedOnChildren(pictureList);

        // button to get to the next part
        Button nextButton = (Button) v.findViewById(R.id.next_section_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to the next section
                tabChangeInterface.switchToTheNextTab();
            }
        });

        return v;
    }


    private void toggleAddPicturesHint() {
        if (addPicturesHintIsVisible) {
            addPicturesHelpText.setVisibility(View.GONE);
            addPicturesHelpIcon.setImageResource(R.drawable.ic_action_help_holo_light_18dp);
            addPicturesHintIsVisible = false;
        } else {
            addPicturesHelpText.setVisibility(View.VISIBLE);
            addPicturesHelpIcon.setImageResource(R.drawable.ic_action_help_yellow_dark_18dp);
            addPicturesHintIsVisible = true;
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
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

            if (requestCode == NewCaseActivity.REQUEST_CAMERA) {
                Log.d(LOG_TAG, "Request Picture from Camera");
                // handle results of camera-picture requests
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//
//                // make a thumbnail to display in a list - save
//                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//                File destination = new File(Environment.getExternalStorageDirectory(),
//                        System.currentTimeMillis() + ".jpg");
//                FileOutputStream fo;
//                try {
//                    destination.createNewFile();
//                    fo = new FileOutputStream(destination);
//                    fo.write(bytes.toByteArray());
//                    fo.close();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                PictureHelperEntity newPicture = new PictureHelperEntity();
                newPicture.setThumbnail(thumbnail);
                newPicture.setPicture(thumbnail);
//                imageView.setImageBitmap(thumbnail);
                addNewPicture(newPicture); // add the new PictureEntity to the List-data-set
                // TODO
//                ivImage.setImageBitmap(thumbnail);


            } else if (requestCode == NewCaseActivity.SELECT_FILE) {
                Log.d(LOG_TAG, "Request Picture from Select file");
                // handle results of library-picture requests
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(getContext(), selectedImageUri, projection, null, null,
                        null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);

                PictureHelperEntity newPicture = new PictureHelperEntity();
                newPicture.setThumbnail(bm);
                newPicture.setPicture(bm);
                addNewPicture(newPicture); // add the new PictureEntity to the List-data-set
//                imageView.setImageBitmap(bm);
                // TODO
//                ivImage.setImageBitmap(bm);
            }
        }
    }

    private void addNewPicture(PictureHelperEntity newPicture) {
        testPictures.add(newPicture);
        EmbedableListViewUtility.setListViewHeightBasedOnChildren(pictureList);
        pictureListAdapter.notifyDataSetChanged();

        // open dialog to request picture-description
        showPictureDescriptionDialog(newPicture);

    }

    private void showPictureDescriptionDialog(PictureHelperEntity picture) {
//      activePicture = picture; // for later modification (result of the dialog fragment)
        EditPictureDescriptionFragment newFragment = EditPictureDescriptionFragment.newInstance(picture.getDescription());
        newFragment.setPicture(picture);
        newFragment.show(getFragmentManager(), "dialog");
    }


    @Override
    public void casePictureClicked(PictureHelperEntity picture) {
        // open a dialog for editing the picture description
        showPictureDescriptionDialog(picture);
    }
}
