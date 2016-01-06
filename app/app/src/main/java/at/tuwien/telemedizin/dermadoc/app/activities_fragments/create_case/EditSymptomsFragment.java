package at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.adapters.PainAssessmentArrayAdapter;
import at.tuwien.telemedizin.dermadoc.entities.PainIntensity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditSymptomsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditSymptomsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NEW_CASE = "newCase";

    private OnTabChangedInFragmentInterface tabChangeInterface;
    private SymptomsSourceInterface symptomsSourceInterface;

    private boolean newCase; // if it is a new case, no symptom information has to be loaded and the layout switches into edit-mode
    private boolean symptomsHintIsVisible;
    private boolean painAssessmentHintIsVisible;
    private boolean sizeAssessmentHintIsVisible;

    private EditText symptomDescriptionEditText;

    private ImageView symptomsHelpIcon;
    private TextView symptomsHelpText;

    private ImageView painAssessmentHelpIcon;
    private TextView painAssessmentHelpText;

    private ImageView sizeAssessmentHelpIcon;
    private TextView sizeAssessmentHelpText;

    private TextView sizeTexView;
    private double currentSelectedSize = 0;

    private PainAssessmentArrayAdapter painArrayAdapter;
    private Spinner painSpinner;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment EditSymptomsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditSymptomsFragment newInstance(boolean newCase) {
        EditSymptomsFragment fragment = new EditSymptomsFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_NEW_CASE, newCase);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public EditSymptomsFragment() {
        // Required empty public constructor
    }

    public void showSizePickerDialog() {
// 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

// 2. Chain together various setter methods to set the dialog characteristics
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogV = inflater.inflate(R.layout.pick_size_dialog_layout, null);
        final NumberPicker nbP1 = (NumberPicker) dialogV.findViewById(R.id.number_picker_1);
        nbP1.setMinValue(0);
        nbP1.setMaxValue(100);
        int currentSizeAsInt = (int)currentSelectedSize;
        nbP1.setValue(currentSizeAsInt);
        final NumberPicker nbP2 = (NumberPicker) dialogV.findViewById(R.id.number_picker_2);
        nbP2.setMaxValue(9);
        nbP2.setMinValue(0);
        nbP2.setValue((int) ((currentSelectedSize - currentSizeAsInt)*10));

        builder.setTitle(R.string.header_size);
        builder.setView(dialogV);

        builder.setPositiveButton(R.string.option_pic_dialog_positive, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                // read values and send them to the textView / size
                int i1 = nbP1.getValue();
                int i2 = nbP2.getValue();

                double s = i1 + i2/10.0;
                currentSelectedSize = s;
                updateSizeTextView();
            }
        });

// 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateSizeTextView() {
        sizeTexView.setText(currentSelectedSize + " " + getString(R.string.size_type));
//        sizeTexView.invalidate();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            newCase = getArguments().getBoolean(ARG_NEW_CASE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            tabChangeInterface = (OnTabChangedInFragmentInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement " + OnTabChangedInFragmentInterface.class.getSimpleName());
        }

        boolean pNewCase = getArguments().getBoolean(ARG_NEW_CASE);
        if (!pNewCase) {
            try {
                symptomsSourceInterface = (SymptomsSourceInterface) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString() + " must implement " + SymptomsSourceInterface.class.getSimpleName());
            }
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_symptoms_layout, container, false);

        symptomDescriptionEditText = (EditText) v.findViewById(R.id.edit_symptoms_edit_text);

        symptomsHelpIcon = (ImageView) v.findViewById(R.id.symptoms_help_icon_view);
        symptomsHelpText = (TextView) v.findViewById(R.id.symptoms_help_hint_text_view);

        symptomsHelpIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make hint visible or hide it
                toggleSymptomsHint();

            }
        });

        symptomsHelpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make hint visible or hide it
                toggleSymptomsHint();
            }
        });

        painAssessmentHelpIcon = (ImageView) v.findViewById(R.id.pain_assessment_help_icon_view);
        painAssessmentHelpText = (TextView) v.findViewById(R.id.pain_assessment_help_hint_text_view);

        painAssessmentHelpIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make hint visible or hide it
                togglePainAssessmentHint();

            }
        });

        painAssessmentHelpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make hint visible or hide it
                togglePainAssessmentHint();

            }
        });

        sizeAssessmentHelpIcon = (ImageView) v.findViewById(R.id.size_help_icon_view);
        sizeAssessmentHelpText = (TextView) v.findViewById(R.id.size_help_hint_text_view);

        sizeAssessmentHelpIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make hint visible or hide it
                toggleSizeHint();

            }
        });

        sizeAssessmentHelpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make hint visible or hide it
                toggleSizeHint();

            }
        });

        sizeTexView = (TextView) v.findViewById(R.id.size_text_view);
        sizeTexView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show number picker dialog
                showSizePickerDialog();
            }
        });

        // create pain-assessement grid
        // Grid view does not really work well ...
//        EmbedableSGridView painGridView = (EmbedableSGridView) v.findViewById(R.id.pain_assessment_grid);
//        painGridView.setExpanded(true);
//        painGridView.setAdapter(new PainAssessmentGridAdapter(getContext()));

        painSpinner = (Spinner) v.findViewById(R.id.pain_scale_spinner);
        List<PainIntensity> spinnerValues = new ArrayList<PainIntensity>(Arrays.asList(PainIntensity.values()));
        painArrayAdapter = new PainAssessmentArrayAdapter(getContext(), R.layout.pain_assessment_element, spinnerValues);
        painSpinner.setAdapter(painArrayAdapter);

        // button to get to the next part
        Button nextButton = (Button) v.findViewById(R.id.next_section_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to the next section
                tabChangeInterface.switchToTheNextTab();
            }
        });

        // load data if it exists
        if (!newCase) {
            currentSelectedSize = symptomsSourceInterface.getSize();
            updateSizeTextView();

            symptomDescriptionEditText.setText(symptomsSourceInterface.getSymptomDescription());

            int indexOfPI = spinnerValues.indexOf(symptomsSourceInterface.getPainIntensity());
            if (indexOfPI >= 0) {
                painSpinner.setSelection(indexOfPI);
            }

        }

        return v;
    }


    private void toggleSymptomsHint() {
        if (symptomsHintIsVisible) {
            symptomsHelpText.setVisibility(View.GONE);
            symptomsHelpIcon.setImageResource(R.drawable.ic_action_help_holo_light_18dp);
            symptomsHintIsVisible = false;
        } else {
            symptomsHelpText.setVisibility(View.VISIBLE);
            symptomsHelpIcon.setImageResource(R.drawable.ic_action_help_yellow_dark_18dp);
            symptomsHintIsVisible = true;
        }
    }

    private void togglePainAssessmentHint() {
        if (painAssessmentHintIsVisible) {
            painAssessmentHelpText.setVisibility(View.GONE);
            painAssessmentHelpIcon.setImageResource(R.drawable.ic_action_help_holo_light_18dp);
            painAssessmentHintIsVisible = false;
        } else {
            painAssessmentHelpText.setVisibility(View.VISIBLE);
            painAssessmentHelpIcon.setImageResource(R.drawable.ic_action_help_yellow_dark_18dp);
            painAssessmentHintIsVisible = true;
        }
    }

    private void toggleSizeHint() {
        if (sizeAssessmentHintIsVisible) {
            sizeAssessmentHelpText.setVisibility(View.GONE);
            sizeAssessmentHelpIcon.setImageResource(R.drawable.ic_action_help_holo_light_18dp);
            sizeAssessmentHintIsVisible = false;
        } else {
            sizeAssessmentHelpText.setVisibility(View.VISIBLE);
            sizeAssessmentHelpIcon.setImageResource(R.drawable.ic_action_help_yellow_dark_18dp);
            sizeAssessmentHintIsVisible = true;
        }
    }

    public String getSymptomDescription() {
        return symptomDescriptionEditText.getText().toString();
    }

    public PainIntensity getPainIntensity() {
        return painArrayAdapter.getItem(painSpinner.getSelectedItemPosition());
    }

    public double getSize() {
        return currentSelectedSize;
    }


    public interface SymptomsSourceInterface {
        public double getSize();

        public String getSymptomDescription();

        public PainIntensity getPainIntensity();
    }
}
