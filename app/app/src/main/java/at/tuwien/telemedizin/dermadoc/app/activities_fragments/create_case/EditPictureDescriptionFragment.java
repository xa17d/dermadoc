package at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.edit_case.AddPictureActivity;
import at.tuwien.telemedizin.dermadoc.app.entities.PictureHelperEntity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditPictureDescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * This fragment is used to edit the description of pictures
 */
public class EditPictureDescriptionFragment extends DialogFragment {

    public static final String LOG_TAG = EditPictureDescriptionFragment.class.getSimpleName();

    private EditText descriptionEditText;

    private boolean editDescriptionHintIsVisible;


    private ImageView editDescriptionHelpIcon;
    private TextView editDescriptionHelpText;

    private PictureHelperEntity picture;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment EditPictureDescriptionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditPictureDescriptionFragment newInstance(String description) {
        EditPictureDescriptionFragment fragment = new EditPictureDescriptionFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public EditPictureDescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String imagePath = null;
        if (getArguments() != null) {

//            imagePath = description = getArguments().getString(IMAGE_PATH_ARG);
        }
    }

    public void setPicture(PictureHelperEntity picture) {
        this.picture = picture;
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View v = inflater.inflate(R.layout.fragment_edit_picture_description_layout, container, false);
//
//        getDialog().setTitle(R.string.title_picture_description_dialog);
//
//        descriptionEditText = (EditText) v.findViewById(R.id.description_edit_text);
//        if (description != null) {
//            descriptionEditText.setText(description);
//        }
//
//        editDescriptionHelpIcon = (ImageView) v.findViewById(R.id.edit_picture_description_help_icon_view);
//        editDescriptionHelpText = (TextView) v.findViewById(R.id.picture_description_help_hint_text_view);
//
//        editDescriptionHelpIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // make hint visible or hide it
//                toggleEditDescriptionHint();
//
//            }
//        });
//
//        editDescriptionHelpText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // make hint visible or hide it
//                toggleEditDescriptionHint();
//            }
//        });
//
//        ImageView iV = (ImageView) v.findViewById(R.id.picture_image_view);
//        iV.setImageBitmap(image);
//
//        return v;
//    }

    private void toggleEditDescriptionHint() {
        if (editDescriptionHintIsVisible) {
            editDescriptionHelpText.setVisibility(View.GONE);
            editDescriptionHelpIcon.setImageResource(R.drawable.ic_action_help_holo_light_18dp);
            editDescriptionHintIsVisible = false;
        } else {
            editDescriptionHelpText.setVisibility(View.VISIBLE);
            editDescriptionHelpIcon.setImageResource(R.drawable.ic_action_help_yellow_dark_18dp);
            editDescriptionHintIsVisible = true;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateDialog() ");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_edit_picture_description_layout, null, false);

        descriptionEditText = (EditText) v.findViewById(R.id.description_edit_text);
        Log.d(LOG_TAG, "description: " + picture.getDescription());
        if (picture.getDescription() != null) {
            descriptionEditText.setText(picture.getDescription());
        }

        editDescriptionHelpIcon = (ImageView) v.findViewById(R.id.edit_picture_description_help_icon_view);
        editDescriptionHelpText = (TextView) v.findViewById(R.id.picture_description_help_hint_text_view);

        editDescriptionHelpIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make hint visible or hide it
                toggleEditDescriptionHint();

            }
        });

        editDescriptionHelpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make hint visible or hide it
                toggleEditDescriptionHint();
            }
        });

        Log.d(LOG_TAG, "image: " + (picture.getPicture()!=null));
        ImageView iV = (ImageView) v.findViewById(R.id.picture_image_view);
        iV.setImageBitmap(picture.getPicture());



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
//                .setIcon(R.drawable.alert_dialog_icon)
                .setTitle(R.string.title_picture_description_dialog)
                .setView(v)
                .setPositiveButton(R.string.option_pic_dialog_positive,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                // TODO: find a better way of communication
                                if (getActivity() instanceof NewCaseActivity) {
                                    ((NewCaseActivity)getActivity()).notifyEditPictureDescription(picture,
                                            descriptionEditText.getText().toString());
                                } else if (getActivity() instanceof AddPictureActivity) {
                                    ((AddPictureActivity)getActivity()).notifyEditPictureDescription(picture,
                                            descriptionEditText.getText().toString());
                                }


                            }
                        }
                )
                .setNegativeButton(R.string.option_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
//                                // do nothing
                                // editing description canceled -> do nothing
                            }
                        }
                );

              return builder.create();
    }



}
