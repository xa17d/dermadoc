package at.tuwien.telemedizin.dermadoc.app.activities_fragments.case_specific;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case.EditLocationFragment;
import at.tuwien.telemedizin.dermadoc.app.adapters.PainIntensityMapper;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.CaseParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.NotificationParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.PhysicianParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.CaseInfoParc;
import at.tuwien.telemedizin.dermadoc.app.helper.CaseDataExtractionHelper;
import at.tuwien.telemedizin.dermadoc.app.helper.FormatHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class CaseOverviewFragment extends Fragment{

    public static final String LOG_TAG = CaseOverviewFragment.class.getSimpleName();

    private CaseDataCallbackInterface caseDataCallbackInterface;

    private TextView caseIdTextView;
    private TextView physicianNameTextView;
    private TextView creationDateTextView;
    private TextView caseStatusTextView;

    private TextView caseNameTextView;

    private TextView localizationHeaderTextView;

    private LinearLayout basicDataListLayout;
    private LinearLayout notificationListLayout;

    private CaseParc caseItem;
    private List<NotificationParc> notificationList;

    private Button deleteNotificationsButton;

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

        caseIdTextView = (TextView) v.findViewById(R.id.case_overview_id);
        physicianNameTextView = (TextView) v.findViewById(R.id.case_overview_physician);
        creationDateTextView = (TextView) v.findViewById(R.id.case_overview_date_of_creation);
        caseStatusTextView = (TextView) v.findViewById(R.id.case_overview_status);
        localizationHeaderTextView = (TextView) v.findViewById(R.id.element_header_textView);
        caseNameTextView = (TextView) v.findViewById(R.id.case_name_text_view);

        basicDataListLayout = (LinearLayout) v.findViewById(R.id.basic_data_list_layout);
        notificationListLayout = (LinearLayout) v.findViewById(R.id.notification_list_layout);

        deleteNotificationsButton = (Button) v.findViewById(R.id.notifications_delete_button);
        deleteNotificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                caseDataCallbackInterface.deleteNotifications();
            }
        });

        // get Data and fill views
        updateDataViews();

        return v;
    }

    public void updateDataViews() {

        if (getContext() == null) {
            return;
        }
        caseItem = caseDataCallbackInterface.getCase();
        setUpBasicData();
        notificationList = caseDataCallbackInterface.getNotifications();
        notificationListLayout.removeAllViewsInLayout();
        setUpNotificationList(LayoutInflater.from(getContext()));

        basicDataListLayout.removeAllViewsInLayout();
        setUpBasicDataList(LayoutInflater.from(getContext()));
    }

    private void setUpBasicData() {
        caseIdTextView.setText(caseItem.getId() + "");
        PhysicianParc physician = caseItem.getPhysician();
        physicianNameTextView.setText(physician != null ?
                physician.getName() : getString(R.string.msg_no_physician_info_found));

        creationDateTextView.setText(
                FormatHelper.calendarToDateFormatString(caseItem.getCreated(), getContext()));

        caseStatusTextView.setText(caseItem.getStatus() + "");
        caseNameTextView.setText(caseItem.getName());

    }

    private void setUpNotificationList(LayoutInflater inflater) {
        Log.d(LOG_TAG, "setUpNotificationList() with list.size()=" + notificationList.size());
        // only one label, if empty list
        if (notificationList.size() == 0) {
            // hide the delte-button
            deleteNotificationsButton.setVisibility(View.GONE);

            // add an "is empty" view
            View simpleNotificationView = inflater.inflate(R.layout.overview_basic_data_item, null, false);
            TextView nHeaderTextView = (TextView) simpleNotificationView.findViewById(R.id.element_header_textView);
            ImageView nIconView = (ImageView) simpleNotificationView.findViewById(R.id.icon_view);
            TextView nInfoTextView = (TextView) simpleNotificationView.findViewById(R.id.element_info_textView);

            nHeaderTextView.setVisibility(View.GONE);
            nIconView.setVisibility(View.GONE);
            nInfoTextView.setText(getString(R.string.hint_no_notifications));
            notificationListLayout.addView(simpleNotificationView);
        } else {

            // show button
            // hide the delte-button
            deleteNotificationsButton.setVisibility(View.VISIBLE);

            for (NotificationParc nP : notificationList) {
                View simpleNotificationView = inflater.inflate(R.layout.overview_basic_data_item, null, false);
                TextView nHeaderTextView = (TextView) simpleNotificationView.findViewById(R.id.element_header_textView);
                ImageView nIconView = (ImageView) simpleNotificationView.findViewById(R.id.icon_view);
                TextView nInfoTextView = (TextView) simpleNotificationView.findViewById(R.id.element_info_textView);

                nHeaderTextView.setVisibility(View.GONE);
                nIconView.setImageResource(R.drawable.ic_action_flag_red_light_18dp);
                nInfoTextView.setText(nP.getText());
                notificationListLayout.addView(simpleNotificationView);
            }
        }
    }

    private void setUpBasicDataList(LayoutInflater inflater) {
        // add basic-data elements (pain Intensity, localization)

        CaseDataExtractionHelper<CaseInfoParc> caseInfoExtractor = new CaseDataExtractionHelper<>(CaseInfoParc.class);
        // get all caseInfo-objects
        List<CaseInfoParc> caseInfos = caseInfoExtractor.extractElements(caseItem.getDataElements());
        if (caseInfos.size() == 0) {
            localizationHeaderTextView.setVisibility(View.GONE);
            return;
        } else {
            localizationHeaderTextView.setVisibility(View.VISIBLE);
        }
        CaseInfoParc cI = caseInfos.get(0);

        // symptom description
//        View basicSymptomDescription = inflater.inflate(R.layout.overview_basic_data_item, null, false);
//        TextView dHeaderTextView = (TextView) basicSymptomDescription.findViewById(R.id.element_header_textView);
//        ImageView dIconView = (ImageView) basicSymptomDescription.findViewById(R.id.icon_view);
//        TextView dInfoTextView = (TextView) basicSymptomDescription.findViewById(R.id.element_info_textView);
//
//        dHeaderTextView.setText(getString(R.string.label_symptoms));
//        dIconView.setVisibility(View.GONE);
//        dInfoTextView.setText(cI.getSymptomDescription());
//
//        basicDataListLayout.addView(basicSymptomDescription);

        // Pain intensity
        View basicPainIntensity = inflater.inflate(R.layout.overview_basic_data_item, null, false);
        TextView pHeaderTextView = (TextView) basicPainIntensity.findViewById(R.id.element_header_textView);
        ImageView pIconView = (ImageView) basicPainIntensity.findViewById(R.id.icon_view);
        TextView pInfoTextView = (TextView) basicPainIntensity.findViewById(R.id.element_info_textView);

        pHeaderTextView.setText(getString(R.string.label_pain));
        pIconView.setImageResource(PainIntensityMapper.getIconResource(cI.getPain()));
        pInfoTextView.setText(getString(PainIntensityMapper.getTitleResource(cI.getPain())));

        basicDataListLayout.addView(basicPainIntensity);

        // size
        View basicSize = inflater.inflate(R.layout.overview_basic_data_item, null, false);
        TextView sHeaderTextView = (TextView) basicSize.findViewById(R.id.element_header_textView);
        ImageView sIconView = (ImageView) basicSize.findViewById(R.id.icon_view);
        TextView sInfoTextView = (TextView) basicSize.findViewById(R.id.element_info_textView);

        sHeaderTextView.setText(getString(R.string.label_size));
        sIconView.setVisibility(View.GONE);
        sInfoTextView.setText(cI.getSize() + " " + getString(R.string.label_size_diameter_hint));

        basicDataListLayout.addView(basicSize);

        // Localizations
        // add location fragment in embedded mode
        Fragment locationFragmentEmbedded = EditLocationFragment.newInstance(false, true);
        android.support.v4.app.FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.location_fragment_container, locationFragmentEmbedded).commit();

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
