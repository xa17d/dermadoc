package at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    public static PhysicianSelectionFragment newInstance() {
        PhysicianSelectionFragment fragment = new PhysicianSelectionFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public PhysicianSelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

        nearbyPhysicianList = getPhysicianList();
        nearbyPhysicianRadioButtonList = new ArrayList<>();
    }


    private List<PhysicianParc> getPhysicianList() {
//        PhysicianParc a = new PhysicianParc(); // TODO remove
//        a.setId(0);
//        a.setName("a");
//
//        PhysicianParc b = new PhysicianParc();
//        b.setId(1);
//        b.setName("b");
//
//        PhysicianParc c = new PhysicianParc();
//        c.setId(2);
//        c.setName("c");
//
//        List<PhysicianParc> list = new ArrayList<PhysicianParc>();
//        list.add(a);
//        list.add(b);
//        list.add(c);
//
//        for(int i = 0; i < 5; i++) {
//            PhysicianParc p = new PhysicianParc();
//            int aAsChar = (int) 'd';
//            p.setName("" + (char) (aAsChar + i));
//            list.add(p);
//        }

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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_physician_selection_layout, container, false);

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



        // button to get to the next part
        Button nextButton = (Button) v.findViewById(R.id.next_section_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to the next section
                tabChangeInterface.switchToTheNextTab();
            }
        });

        nearbyPhysicianListLayout = (RadioGroup) v.findViewById(R.id.nearby_physician_list_linlayout);
        setUpPhysicianList(inflater, nearbyPhysicianListLayout);


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

    private void setUpPhysicianList(LayoutInflater inflater, LinearLayout listRoot) {
        for (PhysicianParc p : nearbyPhysicianList) {
            // use a inflater to get utilise the right theme
            RadioButton rBtn = (RadioButton) inflater.inflate(R.layout.physician_list_item_radio_button, null, false);
            rBtn.setText(p.getName());
            nearbyPhysicianRadioButtonList.add(rBtn);
            listRoot.addView(rBtn);
        }
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
