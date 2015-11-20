package at.tuwien.telemedizin.dermadoc.app.activites_fragments.case_specific;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.helper.FormatHelper;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import at.tuwien.telemedizin.dermadoc.entities.Physician;

/**
 * A simple {@link Fragment} subclass.
 */
public class CaseOverviewFragment extends Fragment {

    public static final String LOG_TAG = CaseOverviewFragment.class.getSimpleName();

    private CaseDataCallbackInterface caseDataCallbackInterface;

    private TextView patientNameTextView;
    private TextView physicianNameTextView;
    private TextView creationDateTextView;
    private TextView caseStatusTextView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment MyCasesFragment.
     */
    public static CaseOverviewFragment newInstance() {
        CaseOverviewFragment fragment = new CaseOverviewFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public CaseOverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_case_overview_layout, container, false);

        patientNameTextView = (TextView) v.findViewById(R.id.case_overview_patient);
        physicianNameTextView = (TextView) v.findViewById(R.id.case_overview_physician);
        creationDateTextView = (TextView) v.findViewById(R.id.case_overview_date_of_creation);
        caseStatusTextView = (TextView) v.findViewById(R.id.case_overview_status);

        // load caseData
        Case caseItem = caseDataCallbackInterface.getCase();
        Patient patient = caseItem.getPatient();
        patientNameTextView.setText(patient != null ? patient.getName() : "No Info");
        Physician physician = caseItem.getPhysician();
        physicianNameTextView.setText(physician != null ?
                physician.getName() : getString(R.string.msg_no_physician_info_found));

        creationDateTextView.setText(
                FormatHelper.calendarToDateFormatString(caseItem.getCreated(), getContext()));

        caseStatusTextView.setText(caseItem.getStatus() + "");


        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            caseDataCallbackInterface = (CaseDataCallbackInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement " + CaseDataCallbackInterface.class.getSimpleName());
        }

    }


}
