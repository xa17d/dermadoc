package at.tuwien.telemedizin.dermadoc.app.activities_fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.GeoLocationParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.PatientParc;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Gender;
import at.tuwien.telemedizin.dermadoc.app.helper.FormatHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDataOverviewFragment extends Fragment {

    public static final String LOG_TAG = UserDataOverviewFragment.class.getSimpleName();

    private UserDataCallbackInterface userDataCallbackInterface;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment MyCasesFragment.
     */
    public static UserDataOverviewFragment newInstance() {
        UserDataOverviewFragment fragment = new UserDataOverviewFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public UserDataOverviewFragment() {
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
        View v = inflater.inflate(R.layout.fragment_user_data_overview_layout, container, false);

        LinearLayout mainListLayout = (LinearLayout) v.findViewById(R.id.user_data_list_layout);

        // get user Data
        PatientParc patient = userDataCallbackInterface.getUser();

        // fill with user data
        setUpDataList(patient, mainListLayout, inflater);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            userDataCallbackInterface = (UserDataCallbackInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement " + UserDataCallbackInterface.class.getSimpleName());
        }

    }


    private void setUpDataList(PatientParc patient, LinearLayout root, LayoutInflater inflater) {

        if (patient == null) {
            return;
        }
        // id
        addDataViewToList(getString(R.string.label_user_id), patient.getId() + "", root, inflater);

        // name
        addDataViewToList(getString(R.string.label_user_name), patient.getName(), root, inflater);

        // svnr
        addDataViewToList(getString(R.string.label_user_svnr), patient.getSvnr(), root, inflater);

        // gender
        Gender gender = patient.getGender();
        addDataViewToList(getString(R.string.label_user_gender),
                gender != null ? gender.toString() : null , root, inflater);

        // mail
        addDataViewToList(getString(R.string.label_user_mail), patient.getMail(), root, inflater);

        // birthday
        addDataViewToList(getString(R.string.label_user_date_of_birth),
                FormatHelper.calendarToDateFormatString(patient.getBirthTime(),
                        getContext()), root, inflater);

        // location
        GeoLocationParc location = patient.getLocation();
        if (location != null) {
            addDataViewToList(getString(R.string.label_user_location), location.toString(), root, inflater);
        }


    }

    private void addDataViewToList(String headerText, String infoText, LinearLayout root, LayoutInflater inflater) {
        View userDataView = inflater.inflate(R.layout.user_data_list_item, null, false);
        // set textElements
        TextView headerTextView = (TextView) userDataView.findViewById(R.id.element_header_textView);
        TextView infoTextView = (TextView) userDataView.findViewById(R.id.element_info_textView);

        headerTextView.setText(headerText);
        infoTextView.setText(infoText);

        root.addView(userDataView);
    }


}
