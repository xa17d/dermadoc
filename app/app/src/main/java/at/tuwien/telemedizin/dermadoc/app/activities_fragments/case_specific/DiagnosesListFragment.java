package at.tuwien.telemedizin.dermadoc.app.activities_fragments.case_specific;


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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case.OnCaseDataRequestAndUpdateInterface;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case.OnTabChangedInFragmentInterface;
import at.tuwien.telemedizin.dermadoc.app.adapters.DiagnosisListAdapter;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.PhysicianParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.DiagnosisParc;
import at.tuwien.telemedizin.dermadoc.app.helper.CaseDataExtractionHelper;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiagnosesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiagnosesListFragment extends Fragment {

    public static final String LOG_TAG = DiagnosesListFragment.class.getSimpleName();

    private CaseDataCallbackInterface caseDataInterface;

    private ListView diagnosesListView;
    private List<DiagnosisParc> listValues;

    private DiagnosisListAdapter adapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment EditSymptomsFragment.
     */
    public static DiagnosesListFragment newInstance() {
        DiagnosesListFragment fragment = new DiagnosesListFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public DiagnosesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            caseDataInterface = (CaseDataCallbackInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement " + CaseDataCallbackInterface.class.getSimpleName());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView()");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_diagnoses_list_layout, container, false);

        diagnosesListView = (ListView) v.findViewById(R.id.diagnoses_list_view);

        CaseDataExtractionHelper<DiagnosisParc> diagnosisExtractor =
                new CaseDataExtractionHelper<>(DiagnosisParc.class);


        listValues = diagnosisExtractor.extractElements(caseDataInterface.getCase().getDataElements());
        Log.d(LOG_TAG, "listValues.size()=" + listValues.size());

        adapter = new DiagnosisListAdapter(getContext(), listValues);
        diagnosesListView.setAdapter(adapter);

        return v;
    }


}
