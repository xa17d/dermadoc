package at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.PhysicianParc;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhysicianSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhysicianSelectionFragment extends Fragment {

    public static final String LOG_TAG = PhysicianSelectionFragment.class.getSimpleName();

    private static final String ARG_NEW_CASE = "newCase";
    private boolean newCase;

    private OnTabChangedInFragmentInterface tabChangeInterface;
    private OnCaseDataRequestAndUpdateInterface caseDataInterface;

    private boolean physicianHintIsVisible;

    private ImageView physicianHelpIcon;
    private TextView physicianHelpText;
    private CheckBox nextPhysicianAvailableCheckbox;

    private RadioGroup nearbyPhysicianListLayout;

    private List<PhysicianParc> nearbyPhysicianList;
    private List<RadioButton> nearbyPhysicianRadioButtonList; // view representing the physician


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment EditSymptomsFragment.
     */
    public static PhysicianSelectionFragment newInstance(boolean newCase) {
        PhysicianSelectionFragment fragment = new PhysicianSelectionFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_NEW_CASE, newCase);
        fragment.setArguments(args);
        return fragment;
    }

    public PhysicianSelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            newCase = getArguments().getBoolean(ARG_NEW_CASE);
        }

//        if (newCase) {
//            nearbyPhysicianList = loadPhysicianList();
//        }

        nearbyPhysicianRadioButtonList = new ArrayList<>();
    }


    private List<PhysicianParc> loadPhysicianList() {
        Log.d(LOG_TAG, "loadPhysicianList() ");
        return caseDataInterface.getNearbyPhysicians();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            tabChangeInterface = (OnTabChangedInFragmentInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement " + OnTabChangedInFragmentInterface.class.getSimpleName());
        }


        try {
            caseDataInterface = (OnCaseDataRequestAndUpdateInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement " + OnCaseDataRequestAndUpdateInterface.class.getSimpleName());
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView()");
        // Inflate the layout for this fragment
        View v = null;
        if (newCase) {
            v = inflater.inflate(R.layout.fragment_physician_selection_layout, container, false);
            initializeForSelection(v,inflater);
        } else {
            v = inflater.inflate(R.layout.fragment_physician_display_layout, container, false);
            initializeForDisplay(v, inflater);
        }

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

    /**
     * called by content provider (activity) to update the list
     * has to check, if the fragment was already initialized
     */
    public void updatePhysicianList() {
        Log.d(LOG_TAG, "updatePhysicianList() ");
        if (caseDataInterface != null) { // if called before it is attached/initialized ...prevent
            nearbyPhysicianList = loadPhysicianList();
            setUpPhysicianList(LayoutInflater.from(getContext()), nearbyPhysicianListLayout);

        }


    }

    private void setUpPhysicianList(LayoutInflater inflater, LinearLayout listRoot) {
        Log.d(LOG_TAG, "setUpPhysicianList() ");
        listRoot.removeAllViewsInLayout(); // reset

        for (PhysicianParc p : nearbyPhysicianList) {
            // use a inflater to get utilise the right theme
            RadioButton rBtn = (RadioButton) inflater.inflate(R.layout.physician_list_item_radio_button, null, false);
            rBtn.setText(p.getName());
            nearbyPhysicianRadioButtonList.add(rBtn);
            listRoot.addView(rBtn);
        }
    }

    private View initializeForDisplay(View v, LayoutInflater inflater) {
        TextView physicianTextView = (TextView) v.findViewById(R.id.selected_physician_text_view);
        Log.d(LOG_TAG, "caseDataInterface != null" + (caseDataInterface != null));
        Log.d(LOG_TAG, "case != null" + (caseDataInterface.getCase() != null));
        Log.d(LOG_TAG, "physician != null" + (caseDataInterface.getCase().getPhysician() != null));
        PhysicianParc phP = caseDataInterface.getCase().getPhysician();
        if (phP != null) {
            physicianTextView.setText(phP.getName());
        } else {
            physicianTextView.setText(getString(R.string.msg_no_physician_info_found));
        }

        return v;
    }

    private View initializeForSelection(View v, LayoutInflater inflater) {


        physicianHelpIcon = (ImageView) v.findViewById(R.id.physician_help_icon_view);
        physicianHelpText = (TextView) v.findViewById(R.id.physician_help_hint_text_view);

        physicianHelpIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make hint visible or hide it
                togglePhysicianHint();

            }
        });

        physicianHelpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make hint visible or hide it
                togglePhysicianHint();
            }
        });


        nearbyPhysicianListLayout = (RadioGroup) v.findViewById(R.id.nearby_physician_list_linlayout);
        updatePhysicianList();
//        setUpPhysicianList(inflater, nearbyPhysicianListLayout);


        nextPhysicianAvailableCheckbox = (CheckBox) v.findViewById(R.id.next_available_physician_checkbox);
        nextPhysicianAvailableCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // disable or enable the radio-buttons
                boolean enable = true;
                if(nextPhysicianAvailableCheckbox.isChecked()) {
                    enable = false;
                }
                for (int i = 0; i < nearbyPhysicianListLayout.getChildCount(); i++) {
                    nearbyPhysicianListLayout.getChildAt(i).setEnabled(enable);
                }
            }
        });

        return v;
    }


    private void togglePhysicianHint() {
        if (physicianHintIsVisible) {
            physicianHelpText.setVisibility(View.GONE);
            physicianHelpIcon.setImageResource(R.drawable.ic_action_help_holo_light_18dp);
            physicianHintIsVisible = false;
        } else {
            physicianHelpText.setVisibility(View.VISIBLE);
            physicianHelpIcon.setImageResource(R.drawable.ic_action_help_yellow_dark_18dp);
            physicianHintIsVisible = true;
        }
    }

    public PhysicianParc getSelectedPhysician() {
        // is the option "next available physician" checked?
        if (nextPhysicianAvailableCheckbox.isChecked()) {
            return null;
        }

        // iterate the radio-buttons and get the selected physician
        for (int i = 0; i < nearbyPhysicianRadioButtonList.size(); i++) {
            if (nearbyPhysicianRadioButtonList.get(i).isChecked()) {
                return nearbyPhysicianList.get(i);
            }
        }

        // no physician was selected and the checkbox was not selected
        // -> handle as if the checkbox is selected
        return null;
    }

}
