package at.tuwien.telemedizin.dermadoc.app.activities_fragments.view_case_data_element;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.case_specific.CaseActivity;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case.EditLocationFragment;
import at.tuwien.telemedizin.dermadoc.app.adapters.PainIntensityMapper;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.Icd10DiagnosisParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.MedicationParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.PatientParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AdviceParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AnamnesisParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AnamnesisQuestionBoolParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AnamnesisQuestionParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AnamnesisQuestionTextParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.CaseDataParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.CaseInfoParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.DiagnosisParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.PhotoMessageParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.TextMessageParc;
import at.tuwien.telemedizin.dermadoc.app.general_entities.BodyLocalization;
import at.tuwien.telemedizin.dermadoc.app.helper.FormatHelper;

public class CaseDataActivity extends AppCompatActivity implements EditLocationFragment.BodyLocationCallbackInterface {

    public static final String LOG_TAG = CaseDataActivity.class.getSimpleName();

    public static final String CASE_DATA_INTENT_KEY = CaseDataActivity.class.getSimpleName() + "caseData";

    private CaseDataParc data;

    private   LinearLayout locationFragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_data_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setVisibility(View.GONE); // hide until used

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get Intent
        Intent intent = getIntent();
        Parcelable caseDataParcel = intent.getParcelableExtra(CASE_DATA_INTENT_KEY);
        if (caseDataParcel != null) {
            data = (CaseDataParc) caseDataParcel;
        } else {
            Log.d(LOG_TAG, "no CaseDataParc intent");
            finish();
        }

        LinearLayout basicContentLayout = (LinearLayout) findViewById(R.id.message_item_body_linlayout1);
        TextView messageTypeTextView = (TextView) findViewById(R.id.message_type_text_view);
        TextView createdDate = (TextView) findViewById(R.id.creation_text);
        TextView authorText = (TextView) findViewById(R.id.author_text);
        TextView idText = (TextView) findViewById(R.id.id_text);
        RelativeLayout backgroundHolder = (RelativeLayout)findViewById(R.id.background_holder_layout);
        RelativeLayout marginHolder = (RelativeLayout) findViewById(R.id.margin_holder_layout);
        locationFragmentContainer = (LinearLayout) findViewById(R.id.location_fragment_container);

        TextView isObsoleteView = (TextView) findViewById(R.id.message_obsolete_text_view);
        if (data.isObsolete()) {
            isObsoleteView.setVisibility(View.VISIBLE);
        }

        // set Background according to author
        // set Background according to author
        if (data.getAuthor() instanceof PatientParc) {
            int backgroundResId = data.isObsolete() ? R.drawable.message_background_shape_obsolete_2 : R.drawable.message_background_shape_2;
            backgroundHolder.setBackgroundResource(backgroundResId);
//            setLayoutParamsToMatchSide(marginHolder, true);
        } else {
            int backgroundResId = data.isObsolete() ? R.drawable.message_background_shape_obsolete_1 : R.drawable.message_background_shape_1;
            backgroundHolder.setBackgroundResource(backgroundResId);
//            setLayoutParamsToMatchSide(marginHolder, false);
        }

        // now check, which type the caseData is
        View specificV = getSpecificTypeLayout(data, messageTypeTextView);
        Log.d(LOG_TAG, "specificView = null? " + (specificV == null));
        basicContentLayout.addView(specificV);

        // date and time
        String creationDateStr = FormatHelper.calendarToDateFormatString(data.getCreated(), this)
                + " " + FormatHelper.calendarToTimeFormatString(data.getCreated(), this);
        createdDate.setText(creationDateStr);

        authorText.setText(data.getAuthor().getName());
        idText.setText(data.getId() + "");

    }

    private View getSpecificTypeLayout(CaseDataParc caseData, TextView messageTypeTextView) {
        Log.d(LOG_TAG, "getSpecificTypeLayout()");

        String messageTypeText = getString(R.string.message_type_message);
        String newTitle = getString(R.string.message_type_message);
        View specificView = null;

        if (caseData == null) {
            Log.d(LOG_TAG, "caseData is null");
            return null;
        }

        if (caseData instanceof DiagnosisParc) {

            messageTypeText = getString(R.string.message_type_diagnosis);
            newTitle = getString(R.string.header_type_diagnosis);

            specificView = getDiagnosisLayout((DiagnosisParc)caseData);
        } else if (caseData instanceof PhotoMessageParc) {
            messageTypeText = getString(R.string.message_type_photo);
            newTitle = getString(R.string.header_type_photo);
            specificView = getPhotoMessageLayout((PhotoMessageParc)caseData);
        } else if (caseData instanceof TextMessageParc) {
            messageTypeText = getString(R.string.message_type_text);
            newTitle = getString(R.string.header_type_text);
            specificView = getTextMessageLayout((TextMessageParc) caseData);
        } else if (caseData instanceof CaseInfoParc) {
            newTitle = getString(R.string.header_type_case_info);
            messageTypeText = getString(R.string.message_type_case_info);
            specificView = getCaseinfoLayout((CaseInfoParc) caseData);

        } else if (caseData instanceof AdviceParc) {
            newTitle = getString(R.string.header_type_advice);
            messageTypeText = getString(R.string.message_type_advice);
            specificView = getAdviceLayout((AdviceParc)caseData);

        } else if (caseData instanceof AnamnesisParc) {
            newTitle = getString(R.string.header_type_anamnesis);
            messageTypeText = getString(R.string.message_type_anamnesis);
            specificView = getAnamnesisLayout((AnamnesisParc) caseData);
        }

        // TODO other types

        messageTypeTextView.setText(messageTypeText);
        setTitle(newTitle);

        return specificView;
    }

    private View getDiagnosisLayout(DiagnosisParc diagnosis) {
        Log.d(LOG_TAG, "getDiagnosisLayout()");
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.advanced_message_list_item, null);

        // check if the case_item exists
        if (diagnosis != null) {
            TextView itemMessage = (TextView) v.findViewById(R.id.message_text_view);
            LinearLayout itemICD10List = (LinearLayout) v.findViewById(R.id.additional_info_list_layout);
            itemICD10List.removeAllViewsInLayout();
            TextView listHeaderTextView = (TextView) v.findViewById(R.id.list_header_text_view);
            listHeaderTextView.setVisibility(View.GONE);

            itemMessage.setText(diagnosis.getMessage());

            // icd10 list
            List<Icd10DiagnosisParc> icd10List = diagnosis.getDiagnosisList();
            if (icd10List != null && icd10List.size() > 0) {
                Log.d(LOG_TAG, "icd10List is > 0");
                listHeaderTextView.setVisibility(View.VISIBLE);
                listHeaderTextView.setText(R.string.list_header_icd10);


                for (Icd10DiagnosisParc d10 : icd10List) {
                    // add elements to the list
                    LayoutInflater inflater2 = LayoutInflater.from(this);
                    View d10RootView = inflater2.inflate(R.layout.simple_text_list_item, null);
                    TextView diagnosisText = (TextView) d10RootView.findViewById(R.id.msg_text);
                    diagnosisText.setText(d10.toString());
                    itemICD10List.addView(d10RootView);
                }
            }

        }
        return v;
    }

    private View getPhotoMessageLayout(PhotoMessageParc message) {
        Log.d(LOG_TAG, "getPhotoMessageLayout()");
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.photo_message_list_item, null);

        // check if the case_item exists
        if (message != null) {
            TextView itemMessage = (TextView) v.findViewById(R.id.message_text_view);
            ImageView photoImageView = (ImageView) v.findViewById(R.id.picture_image_view);


            itemMessage.setText(message.getMime());

            // get bitmap from byteArray
            Bitmap bitmap = BitmapFactory.decodeByteArray(message.getPhotoData(), 0, message.getPhotoData().length);
            photoImageView.setImageBitmap(bitmap);

        }
        return v;
    }

    private View getTextMessageLayout(TextMessageParc message) {
        Log.d(LOG_TAG, "getTextMessageLayout()");
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.text_message_list_item, null);

        // check if the case_item exists
        if (message != null) {
            TextView itemMessage = (TextView) v.findViewById(R.id.message_text_view);
            itemMessage.setText(message.getMessage());
        }
        return v;
    }

    private View getAdviceLayout(AdviceParc message) {
        Log.d(LOG_TAG, "getAdviceLayout()");
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.advanced_message_list_item, null);

        // check if the case_item exists
        if (message != null) {
            TextView itemMessage = (TextView) v.findViewById(R.id.message_text_view);
            LinearLayout medicationListView = (LinearLayout) v.findViewById(R.id.additional_info_list_layout);
            medicationListView.removeAllViewsInLayout();
            TextView listHeaderTextView = (TextView) v.findViewById(R.id.list_header_text_view);
            listHeaderTextView.setVisibility(View.GONE);

            itemMessage.setText(message.getMessage());

            // icd10 list
            List<MedicationParc> medicationList = message.getMedications();
            if (medicationList != null && medicationList.size() > 0) {
                Log.d(LOG_TAG, "medicationList is > 0");

                listHeaderTextView.setVisibility(View.VISIBLE);
                listHeaderTextView.setText(R.string.list_header_medication);

                for (MedicationParc med : medicationList) {
                    // add elements to the list
                    LayoutInflater inflater2 = LayoutInflater.from(this);
                    View medRootView = inflater2.inflate(R.layout.simple_text_list_item, null);

                    TextView diagnosisText = (TextView) medRootView.findViewById(R.id.msg_text);
                    String medText = med.getName();
                    if (med.getDosis() != null && med.getDosis().trim().length() > 0) {
                        medText += "\n" + getString(R.string.label_medication_dosis)
                                + " " + med.getDosis();
                    }
                    diagnosisText.setText(medText);
                    medicationListView.addView(medRootView);
                }
            }

        }
        return v;
    }

    private View getCaseinfoLayout(CaseInfoParc message) {
        Log.d(LOG_TAG, "getCaseInfoLayout()");
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.advanced_message_list_item, null);

        // check if the case_item exists
        if (message != null) {
            TextView itemMessage = (TextView) v.findViewById(R.id.message_text_view);
            LinearLayout infoListView = (LinearLayout) v.findViewById(R.id.additional_info_list_layout);
            infoListView.removeAllViewsInLayout();
            TextView listHeaderTextView = (TextView) v.findViewById(R.id.list_header_text_view);
            listHeaderTextView.setVisibility(View.GONE);


            itemMessage.setText(getString(R.string.hint_case_info_message));

            // info-list
            List<String> infos = new ArrayList<>();

            infos.add(getString(R.string.label_size) + " " + message.getSize()
                    + getString(R.string.size_type));

            infos.add( getString(PainIntensityMapper.getTitleResource(message.getPain())));
//            infos.add( "Symptom Description: \n" + message.getSymptomDescription());
            infos.add( "Body Localizations: " + message.getLocalizations().size());

            if (infos.size() > 0) {
                Log.d(LOG_TAG, "infos is > 0");

                for (String info : infos) {
                    // add elements to the list
                    LayoutInflater inflater2 = LayoutInflater.from(this);
                    View infoRootView = inflater2.inflate(R.layout.simple_text_list_item, null);

                    TextView diagnosisText = (TextView) infoRootView.findViewById(R.id.msg_text);
                    diagnosisText.setText(info);
                    infoListView.addView(infoRootView);
                }
            }

            Fragment fragment = EditLocationFragment.newInstance(false, true);

            if (fragment != null) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.location_fragment_container,fragment);
                fragmentTransaction.commit();
                locationFragmentContainer.setVisibility(View.VISIBLE);
            }


        }
        return v;
    }

    private View getAnamnesisLayout(AnamnesisParc message) {
        Log.d(LOG_TAG, "getAnamnesisLayout()");
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.advanced_message_list_item, null);

        // check if the case_item exists
        if (message != null) {
            TextView itemMessage = (TextView) v.findViewById(R.id.message_text_view);
            LinearLayout questionListView = (LinearLayout) v.findViewById(R.id.additional_info_list_layout);
            questionListView.removeAllViewsInLayout();
            TextView listHeaderTextView = (TextView) v.findViewById(R.id.list_header_text_view);
            listHeaderTextView.setVisibility(View.GONE);

            itemMessage.setText(message.getMessage());

            // question list
            List<AnamnesisQuestionParc> questionList = message.getQuestions();
            if (questionList != null && questionList.size() > 0) {
                Log.d(LOG_TAG, "questionList is > 0");

                listHeaderTextView.setVisibility(View.VISIBLE);
                listHeaderTextView.setText(R.string.list_header_question);

                for (AnamnesisQuestionParc q : questionList) {
                    LayoutInflater inflater2 = LayoutInflater.from(this);
                    View qRootView = qRootView = inflater2.inflate(R.layout.anamnesis_question_answered_item, null);

                    TextView questionTextView = (TextView) qRootView.findViewById(R.id.question_text_view);
                    TextView answerTextView = (TextView) qRootView.findViewById(R.id.answer_text_view);

                    questionTextView.setText(q.getQuestion());

                    // add elements to the list
                    if (q instanceof AnamnesisQuestionBoolParc) {
                        AnamnesisQuestionBoolParc qB = (AnamnesisQuestionBoolParc)q;
                        Boolean bAnswer = qB.getAnswer();
                        String bAnswerAsString = "";
                        if (bAnswer == null) {
                            bAnswerAsString = getString(R.string.label_question_not_answered);
                        } else {
                            bAnswerAsString = (bAnswer ?
                                    getString(R.string.label_question_positive_answer) :
                                    getString(R.string.label_question_negative_answer));
                        }
                        answerTextView.setText(bAnswerAsString);
                    } else {
                        AnamnesisQuestionTextParc qT = (AnamnesisQuestionTextParc)q;
                        answerTextView.setText(qT.getAnswer());
                    }

                    questionListView.addView(qRootView);
                }
            }
        }
        return v;
    }

    @Override
    public List<BodyLocalization> getBodyLocations() {
        Log.d(LOG_TAG, "getBodyLocations()");
        List<BodyLocalization> localizations = new ArrayList<>();

        // first check, if the caseData contains location-data

        if (this.data != null && this.data instanceof CaseInfoParc) {
            CaseInfoParc cI = (CaseInfoParc)this.data;
            if (cI.getLocalizations() != null) {
                localizations = cI.getLocalizations();
            }
        }
        return localizations;
    }
}
