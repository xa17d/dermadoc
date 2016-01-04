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
 * Use the {@link FinishEditingFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * Give the user the option to finish the editing process.
 * After finishing, the new/edited case will be saved
 */
public class FinishEditingFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NEW_CASE = "newCase";

    private OnTabChangedInFragmentInterface tabChangeInterface;

    private boolean newCase; // if it is a new case, no symptom information has to be loaded and the layout switches into edit-mode
    private boolean finishCaseHintIsVisible;
    private boolean caseNameHintIsVisible;

    private EditText caseNameEditText;

    private ImageView finishCaseHelpIcon;
    private TextView finishCaseHelpText;

    private ImageView caseNameHelpIcon;
    private TextView caseNameHelpText;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment EditSymptomsFragment.
     */
    public static FinishEditingFragment newInstance(boolean newCase) {
        FinishEditingFragment fragment = new FinishEditingFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_NEW_CASE, newCase);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FinishEditingFragment() {
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
        View v = inflater.inflate(R.layout.fragment_finish_editing_layout, container, false);

        caseNameEditText = (EditText) v.findViewById(R.id.edit_case_name_edit_text);

        finishCaseHelpIcon = (ImageView) v.findViewById(R.id.finish_editing_help_icon_view);
        finishCaseHelpText = (TextView) v.findViewById(R.id.finish_editing_help_hint_text_view);

        finishCaseHelpIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make hint visible or hide it
                toggleFinishEditingHint();

            }
        });

        finishCaseHelpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make hint visible or hide it
                toggleFinishEditingHint();
            }
        });

        caseNameHelpIcon = (ImageView) v.findViewById(R.id.case_name_help_icon_view);
        caseNameHelpText = (TextView) v.findViewById(R.id.case_name_help_hint_text_view);

        caseNameHelpIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make hint visible or hide it
                toggleCaseNameHint();

            }
        });

        caseNameHelpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make hint visible or hide it
                toggleCaseNameHint();

            }
        });

        // button to finish editing
        Button finishButton = (Button) v.findViewById(R.id.finish_editing_button);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((NewCaseActivity)getActivity()).finishEditing(); // TODO better without cast! (onAttach etc.)
            }
        });

        return v;
    }


    private void toggleFinishEditingHint() {
        if (finishCaseHintIsVisible) {
            finishCaseHelpText.setVisibility(View.GONE);
            finishCaseHelpIcon.setImageResource(R.drawable.ic_action_help_holo_light_18dp);
            finishCaseHintIsVisible = false;
        } else {
            finishCaseHelpText.setVisibility(View.VISIBLE);
            finishCaseHelpIcon.setImageResource(R.drawable.ic_action_help_yellow_dark_18dp);
            finishCaseHintIsVisible = true;
        }
    }

    private void toggleCaseNameHint() {
        if (caseNameHintIsVisible) {
            caseNameHelpText.setVisibility(View.GONE);
            caseNameHelpIcon.setImageResource(R.drawable.ic_action_help_holo_light_18dp);
            caseNameHintIsVisible = false;
        } else {
            caseNameHelpText.setVisibility(View.VISIBLE);
            caseNameHelpIcon.setImageResource(R.drawable.ic_action_help_yellow_dark_18dp);
            caseNameHintIsVisible = true;
        }
    }


    public String getCaseName() {
        return caseNameEditText.getText().toString();
    }
}
