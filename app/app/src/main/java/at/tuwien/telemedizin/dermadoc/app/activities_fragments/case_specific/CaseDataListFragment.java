package at.tuwien.telemedizin.dermadoc.app.activities_fragments.case_specific;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.view_case_data_element.CaseDataActivity;
import at.tuwien.telemedizin.dermadoc.app.adapters.CaseDataListAdapter;
import at.tuwien.telemedizin.dermadoc.app.comparators.CaseDataDateOfCreationComparator;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.CaseParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AdviceParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AnamnesisParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.CaseDataParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.CaseInfoParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.DiagnosisParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.PhotoMessageParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.TextMessageParc;
import at.tuwien.telemedizin.dermadoc.app.helper.CaseDataExtractionHelper;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CaseDataListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CaseDataListFragment extends Fragment  {

    public static final String LOG_TAG = CaseDataListFragment.class.getSimpleName();

    private CaseDataCallbackInterface caseDataInterface;

    private CaseParc caseItem;

    private ListView listView;
    private List<CaseDataParc> listValues;

    private CaseDataListAdapter adapter;

    private TextView filterTextMsg;
    private TextView filterPhotoMsg;
    private TextView filterDiagnosisMsg;
    private TextView filterAdviceMsg;
    private TextView filterCaseInfoMsg;
    private TextView filterAnamnesisMsg;

    private RelativeLayout createMessageRootLayout;
    private LinearLayout createMessageButtonLayout;
    private EditText createMessageEditText;

    private Space spaceForHeightAdjustment;

    boolean showTextMsg = true;
    boolean showPhotoMsg = true;
    boolean showdiagnosisMsg = true;
    boolean showAdviceMsg = true;
    boolean showCaseInfoMsg = true;
    boolean showAnamnesisMsg = true;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment EditSymptomsFragment.
     */
    public static CaseDataListFragment newInstance() {
        CaseDataListFragment fragment = new CaseDataListFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public CaseDataListFragment() {
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
        View v = inflater.inflate(R.layout.fragment_case_data_list_layout, container, false);

        listView = (ListView) v.findViewById(R.id.diagnoses_list_view);

        CaseDataExtractionHelper<DiagnosisParc> diagnosisExtractor =
                new CaseDataExtractionHelper<>(DiagnosisParc.class);


        caseItem = caseDataInterface.getCase();

        listValues = new LinkedList<>(caseItem.getDataElements());
        Log.d(LOG_TAG, "listValues.size()=" + listValues.size());

        adapter = new CaseDataListAdapter(getContext(), getActivity(), listValues);
        adapter.sort(new CaseDataDateOfCreationComparator());
        listView.setAdapter(adapter);

        // scroll to the bottom
        scrollToBottomOfList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // start the CaseActivity and open the selected case

                Intent intent = new Intent(getContext(), CaseDataActivity.class);

                intent.putExtra(CaseDataActivity.CASE_DATA_INTENT_KEY, adapter.getItem(position));
                startActivity(intent);
            }
        });

        filterTextMsg = (TextView) v.findViewById(R.id.message_type_text_icon_view);
        filterPhotoMsg = (TextView) v.findViewById(R.id.message_type_photo_icon_view);
        filterDiagnosisMsg = (TextView) v.findViewById(R.id.message_type_diagnosis_icon_view);
        filterAdviceMsg = (TextView) v.findViewById(R.id.message_type_advice_icon_view);
        filterCaseInfoMsg = (TextView) v.findViewById(R.id.message_type_case_info_icon_view);
        filterAnamnesisMsg = (TextView) v.findViewById(R.id.message_type_anamnesis_icon_view);

        TypeFilterViewClickListener onClickListener = new TypeFilterViewClickListener();
        TypeFilterViewLongClickListener onLongClickListener = new TypeFilterViewLongClickListener();
        filterTextMsg.setOnClickListener(onClickListener);
        filterTextMsg.setOnLongClickListener(onLongClickListener);
        filterPhotoMsg.setOnClickListener(onClickListener);
        filterPhotoMsg.setOnLongClickListener(onLongClickListener);
        filterDiagnosisMsg.setOnClickListener(onClickListener);
        filterDiagnosisMsg.setOnLongClickListener(onLongClickListener);
        filterAdviceMsg.setOnClickListener(onClickListener);
        filterAdviceMsg.setOnLongClickListener(onLongClickListener);
        filterCaseInfoMsg.setOnClickListener(onClickListener);
        filterCaseInfoMsg.setOnLongClickListener(onLongClickListener);
        filterAnamnesisMsg.setOnClickListener(onClickListener);
        filterAnamnesisMsg.setOnLongClickListener(onLongClickListener);

        createMessageRootLayout = (RelativeLayout) v.findViewById(R.id.create_message_root_layout);

        createMessageButtonLayout = (LinearLayout) v.findViewById(R.id.button_layout);
        spaceForHeightAdjustment = (Space) v.findViewById(R.id.space);

        createMessageEditText = (EditText) v.findViewById(R.id.edit_text);
        createMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // check if the text is empty
                if (s.length() > 0) {
                    adjustHeight(true);
                } else {
                    adjustHeight(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // button listener
        ImageButton sendButton = (ImageButton) v.findViewById(R.id.send_button);
//        ImageButton attachButton = (ImageButton) v.findViewById(R.id.attach_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create new text message
                String text = createMessageEditText.getText().toString().trim();
                if (text.length() > 0) {
                    ((CaseActivity)getActivity()).sendTextMessage(text);

                    // empty editText
                    createMessageEditText.setText("");
                }
            }
        });

        return v;
    }

    private void scrollToBottomOfList() {
        listView.setSelection(adapter.getCount() - 1);
    }

    private void adjustHeight(boolean maxHeight) {

        if (maxHeight) {
            spaceForHeightAdjustment.setVisibility(View.VISIBLE);
        } else {
            spaceForHeightAdjustment.setVisibility(View.GONE);
        }


    }

    /**
     * should be called by the activity, when the dataset changes
     */
    public void updateMessageList() {
        if (caseDataInterface == null || getContext() == null) {
            return; // prevent exceptions, when this fragment has not been initialized or is not active
        }
        caseItem = caseDataInterface.getCase();
        applyFilterToList(); // activate the list through activating the filter
        adapter.notifyDataSetChanged();
        scrollToBottomOfList();

    }

    /**
     * switch boolean values and set background resource of the views
     * @param view
     * @param longTouch
     */
    private void handleTouchedFilterElement(View view, boolean longTouch) {

        boolean allActive = false;
        // check if all filters are active
        allActive = showTextMsg && showPhotoMsg && showdiagnosisMsg
                && showAdviceMsg && showCaseInfoMsg && showAnamnesisMsg;

        // if all are active -> disable all, so that only the selected one is active afterwards
        // if not all are active -> activate all

        if (longTouch) {
            // all are disabled, only one will be active
            showTextMsg = !allActive;
            showPhotoMsg = !allActive;
            showdiagnosisMsg = !allActive;
            showAdviceMsg = !allActive;
            showCaseInfoMsg = !allActive;
            showAnamnesisMsg = !allActive;
        }

        // switch the boolean values
        if (view.getId() == filterTextMsg.getId()) {
            showTextMsg = !showTextMsg || (longTouch && !allActive); // activate all, if longTouch and all should be activated
            filterTextMsg.setBackgroundResource(showTextMsg ?
                    R.drawable.message_type_selected_background_shape : R.drawable.message_type_background_shape);

        } else if (view.getId() == filterPhotoMsg.getId()) {
            showPhotoMsg = !showPhotoMsg || (longTouch && !allActive); // activate all, if longTouch and all should be activated
            filterPhotoMsg.setBackgroundResource(showPhotoMsg ?
                    R.drawable.message_type_selected_background_shape : R.drawable.message_type_background_shape);

        } else if (view.getId() == filterDiagnosisMsg.getId()) {
            showdiagnosisMsg = !showdiagnosisMsg || (longTouch && !allActive); // activate all, if longTouch and all should be activated
            filterDiagnosisMsg.setBackgroundResource(showdiagnosisMsg ?
                    R.drawable.message_type_selected_background_shape : R.drawable.message_type_background_shape);

        } else if (view.getId() == filterAdviceMsg.getId()) {
            showAdviceMsg = !showAdviceMsg || (longTouch && !allActive); // activate all, if longTouch and all should be activated
            filterAdviceMsg.setBackgroundResource(showAdviceMsg ?
                    R.drawable.message_type_selected_background_shape : R.drawable.message_type_background_shape);

        } else if (view.getId() == filterCaseInfoMsg.getId()) {
            showCaseInfoMsg = !showCaseInfoMsg || (longTouch && !allActive); // activate all, if longTouch and all should be activated
            filterCaseInfoMsg.setBackgroundResource(showCaseInfoMsg ?
                    R.drawable.message_type_selected_background_shape : R.drawable.message_type_background_shape);

        } else if (view.getId() == filterAnamnesisMsg.getId()) {
            showAnamnesisMsg = !showAnamnesisMsg || (longTouch && !allActive); // activate all, if longTouch and all should be activated
            filterAnamnesisMsg.setBackgroundResource(showAnamnesisMsg ?
                    R.drawable.message_type_selected_background_shape : R.drawable.message_type_background_shape);

        }

    }

    /**
     * switch the background resources according to boolean values
     */
    private void applyFilterToBackground() {
        // switch the background-images

        filterTextMsg.setBackgroundResource(showTextMsg ?
                R.drawable.message_type_selected_background_shape : R.drawable.message_type_background_shape);

        filterPhotoMsg.setBackgroundResource(showPhotoMsg ?
                R.drawable.message_type_selected_background_shape : R.drawable.message_type_background_shape);

        filterDiagnosisMsg.setBackgroundResource(showdiagnosisMsg ?
                R.drawable.message_type_selected_background_shape : R.drawable.message_type_background_shape);

        filterAdviceMsg.setBackgroundResource(showAdviceMsg ?
                R.drawable.message_type_selected_background_shape : R.drawable.message_type_background_shape);

        filterCaseInfoMsg.setBackgroundResource(showCaseInfoMsg ?
                R.drawable.message_type_selected_background_shape : R.drawable.message_type_background_shape);

        filterAnamnesisMsg.setBackgroundResource(showAnamnesisMsg ?
                R.drawable.message_type_selected_background_shape : R.drawable.message_type_background_shape);



    }

    private void applyFilterToList() {
        Log.d(LOG_TAG, "applyFilterToList()");
        listValues.clear();

        if (showTextMsg) {
            CaseDataExtractionHelper<TextMessageParc> textMsgExtractor =
                    new CaseDataExtractionHelper<>(TextMessageParc.class);
            listValues.addAll(textMsgExtractor.extractElements(caseItem.getDataElements()));
            Log.d(LOG_TAG, "extractedTMsg: " + textMsgExtractor.extractElements(caseItem.getDataElements()).size());
        }

        Log.d(LOG_TAG, "after textMsg, list size: " + listValues.size());


        if (showPhotoMsg) {
            CaseDataExtractionHelper<PhotoMessageParc> msgExtractor =
                    new CaseDataExtractionHelper<>(PhotoMessageParc.class);
            listValues.addAll(msgExtractor.extractElements(caseItem.getDataElements()));
        }

        if (showdiagnosisMsg) {
            CaseDataExtractionHelper<DiagnosisParc> msgExtractor =
                    new CaseDataExtractionHelper<>(DiagnosisParc.class);
            listValues.addAll(msgExtractor.extractElements(caseItem.getDataElements()));
        }

        if (showAdviceMsg) {
            CaseDataExtractionHelper<AdviceParc> msgExtractor =
                    new CaseDataExtractionHelper<>(AdviceParc.class);
            listValues.addAll(msgExtractor.extractElements(caseItem.getDataElements()));
        }

        if (showCaseInfoMsg) {
            CaseDataExtractionHelper<CaseInfoParc> msgExtractor =
                    new CaseDataExtractionHelper<>(CaseInfoParc.class);
            listValues.addAll(msgExtractor.extractElements(caseItem.getDataElements()));
        }

        if (showAnamnesisMsg) {
            CaseDataExtractionHelper<AnamnesisParc> msgExtractor =
                    new CaseDataExtractionHelper<>(AnamnesisParc.class);
            listValues.addAll(msgExtractor.extractElements(caseItem.getDataElements()));
        }

        Log.d(LOG_TAG, "after filter, list size: " + listValues.size());
        adapter.sort(new CaseDataDateOfCreationComparator());
        adapter.notifyDataSetChanged();
    }



    private class TypeFilterViewClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            handleTouchedFilterElement(v, false);
            applyFilterToBackground();
            applyFilterToList();
        }
    }

    private class TypeFilterViewLongClickListener implements View.OnLongClickListener {


        @Override
        public boolean onLongClick(View v) {
            handleTouchedFilterElement(v, true);
            applyFilterToBackground();
            applyFilterToList();
            return true;
        }
    }

}
