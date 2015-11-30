package at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

    private boolean newCase; // if it is a new case, no symptom information has to be loaded and the layout switches into edit-mode
    private boolean symptomsHintIsVisible;
    private boolean painAssessmentHintIsVisible;

    private EditText symptomDescriptionEditText;

    private ImageView symptomsHelpIcon;
    private TextView symptomsHelpText;

    private ImageView painAssessmentHelpIcon;
    private TextView painAssessmentHelpText;


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

        // create pain-assessement grid
        // Grid view does not really work well ...
//        EmbedableSGridView painGridView = (EmbedableSGridView) v.findViewById(R.id.pain_assessment_grid);
//        painGridView.setExpanded(true);
//        painGridView.setAdapter(new PainAssessmentGridAdapter(getContext()));

        Spinner painSpinner = (Spinner) v.findViewById(R.id.pain_scale_spinner);
        List<PainIntensity> spinnerValues = new ArrayList<PainIntensity>(Arrays.asList(PainIntensity.values()));
        PainAssessmentArrayAdapter painArrayAdapter = new PainAssessmentArrayAdapter(getContext(), R.layout.pain_assessment_element, spinnerValues);
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


}
