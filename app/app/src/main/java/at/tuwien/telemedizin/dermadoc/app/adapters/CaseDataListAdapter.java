package at.tuwien.telemedizin.dermadoc.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Collection;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.case_specific.CaseActivity;
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
import at.tuwien.telemedizin.dermadoc.app.helper.FormatHelper;
import at.tuwien.telemedizin.dermadoc.entities.casedata.AnamnesisQuestion;

/**
 * Created by FAUser on 18.11.2015.
 * Adapter to fill the MyCases-List
 *
 * following http://www.vogella.com/tutorials/AndroidListView/article.html
 */
public class CaseDataListAdapter extends ArrayAdapter<CaseDataParc> {
    public static final String LOG_TAG = CaseDataListAdapter.class.getSimpleName();


    private final Context context;
    private final Activity activity;
    private final List<CaseDataParc> values;


    public CaseDataListAdapter(Context context, Activity activity, List<CaseDataParc> values) {
        super(context, -1, values); //
        this.context = context;
        this.values = values;
        this.activity = activity;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        // save resources: only inflate when == null
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.message_basic_list_item, parent, false);

        } else {
            // reset the specific-content
            LinearLayout basicContentLayout = (LinearLayout) v.findViewById(R.id.message_item_body_linlayout1);
            basicContentLayout.removeAllViewsInLayout();
        }

        LinearLayout basicContentLayout = (LinearLayout) v.findViewById(R.id.message_item_body_linlayout1);
        TextView messageTypeTextView = (TextView) v.findViewById(R.id.message_type_text_view);
        TextView createdDate = (TextView) v.findViewById(R.id.creation_text);
        RelativeLayout backgroundHolder = (RelativeLayout) v.findViewById(R.id.background_holder_layout);
        RelativeLayout marginHolder = (RelativeLayout) v.findViewById(R.id.margin_holder_layout);

        CaseDataParc caseData = getItem(position);

        // set Background according to author
        if (caseData.getAuthor() instanceof PatientParc) {
            backgroundHolder.setBackgroundResource(R.drawable.message_background_shape_2);
            setLayoutParamsToMatchSide(marginHolder, true);
        } else {
            backgroundHolder.setBackgroundResource(R.drawable.message_background_shape_1);
            setLayoutParamsToMatchSide(marginHolder, false);
        }

        // now check, which type the caseData is
        View specificV = getSpecificTypeLayout(caseData, messageTypeTextView);
        Log.d(LOG_TAG, "specificView = null? " + (specificV == null));
        basicContentLayout.addView(specificV);

        // date and time
        String creationDateStr = FormatHelper.calendarToDateFormatString(caseData.getCreated(), getContext())
                + " " + FormatHelper.calendarToTimeFormatString(caseData.getCreated(), getContext());
        createdDate.setText(creationDateStr);

        return v;
    }

    private View getSpecificTypeLayout(CaseDataParc caseData, TextView messageTypeTextView) {
        Log.d(LOG_TAG, "getSpecificTypeLayout()");

        String messageTypeText = context.getString(R.string.message_type_message);
        View specificView = null;

        if (caseData == null) {
            Log.d(LOG_TAG, "caseData is null");
            return null;
        }

        if (caseData instanceof DiagnosisParc) {

            messageTypeText = context.getString(R.string.message_type_diagnosis);

            specificView = getDiagnosisLayout((DiagnosisParc)caseData);
        } else if (caseData instanceof PhotoMessageParc) {
            messageTypeText = context.getString(R.string.message_type_photo);
            specificView = getPhotoMessageLayout((PhotoMessageParc)caseData);
        } else if (caseData instanceof TextMessageParc) {
            messageTypeText = context.getString(R.string.message_type_text);
            specificView = getTextMessageLayout((TextMessageParc) caseData);
        } else if (caseData instanceof CaseInfoParc) {
            messageTypeText = context.getString(R.string.message_type_case_info);

            // show as text message with link to overview
            TextMessageParc infoMsg = new TextMessageParc(caseData.getId(), caseData.getCreated(),
                    caseData.getAuthor(), context.getString(R.string.hint_case_info_message));
            specificView = getTextMessageLayout(infoMsg);
            // TODO listener to link to the overview
            specificView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (activity instanceof CaseActivity) {
                        Log.d(LOG_TAG, "activity is CaseActivity - apply listener");
                        ((CaseActivity)activity).switchToOverview();
                    }
                }
            });

        } else if (caseData instanceof AdviceParc) {
            messageTypeText = context.getString(R.string.message_type_advice);
            specificView = getAdviceLayout((AdviceParc)caseData);

        } else if (caseData instanceof AnamnesisParc) {
            messageTypeText = context.getString(R.string.message_type_anamnesis);
            specificView = getAnamnesisLayout((AnamnesisParc) caseData);
        }

        // TODO other types

        messageTypeTextView.setText(messageTypeText);

        return specificView;
    }

    /**
     * makes the message-indent (right or left)
     * @param rl
     * @param left
     */
    private void setLayoutParamsToMatchSide(RelativeLayout rl, boolean left) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        int end = 10;
        int start = 10;
        int top_bottom = 10;
        int indent = 50;

        // should the message be indented on the right or left side?
        if (left) {
            end = indent;
        } else {
            start = indent;
        }

        // convert into dp instead of px
        int endDP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, end, context.getResources().getDisplayMetrics());
        int startDP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, start, context.getResources().getDisplayMetrics());
        int top_bottomDP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, top_bottom, context.getResources().getDisplayMetrics());

//        layoutParams.setMarginEnd(end);
//        layoutParams.setMarginStart(start);
        layoutParams.setMargins(startDP, top_bottomDP, endDP, top_bottomDP);
        rl.setLayoutParams(layoutParams);
    }


    private View getDiagnosisLayout(DiagnosisParc diagnosis) {
        Log.d(LOG_TAG, "getDiagnosisLayout()");
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.advanced_message_list_item, null);

        // check if the case_item exists
        if (diagnosis != null) {
            TextView itemMessage = (TextView) v.findViewById(R.id.message_text_view);
            LinearLayout itemICD10List = (LinearLayout) v.findViewById(R.id.additional_info_list_layout);
            itemICD10List.removeAllViewsInLayout();

            itemMessage.setText(diagnosis.getMessage());

            // icd10 list
            List<Icd10DiagnosisParc> icd10List = diagnosis.getDiagnosisList();
            if (icd10List != null && icd10List.size() > 0) {
                Log.d(LOG_TAG, "icd10List is > 0");
                for (Icd10DiagnosisParc d10 : icd10List) {
                    // add elements to the list
                    LayoutInflater inflater2 = LayoutInflater.from(getContext());
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
        LayoutInflater inflater = LayoutInflater.from(getContext());
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
        LayoutInflater inflater = LayoutInflater.from(getContext());
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
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.advanced_message_list_item, null);

        // check if the case_item exists
        if (message != null) {
            TextView itemMessage = (TextView) v.findViewById(R.id.message_text_view);
            LinearLayout medicationListView = (LinearLayout) v.findViewById(R.id.additional_info_list_layout);
            medicationListView.removeAllViewsInLayout();

            itemMessage.setText(message.getMessage());

            // icd10 list
            List<MedicationParc> medicationList = message.getMedications();
            if (medicationList != null && medicationList.size() > 0) {
                Log.d(LOG_TAG, "medicationList is > 0");
                for (MedicationParc med : medicationList) {
                    // add elements to the list
                    LayoutInflater inflater2 = LayoutInflater.from(getContext());
                    View medRootView = inflater2.inflate(R.layout.simple_text_list_item, null);

                    TextView diagnosisText = (TextView) medRootView.findViewById(R.id.msg_text);
                    diagnosisText.setText(med.getName());
                    medicationListView.addView(medRootView);
                }
            }

        }
        return v;
    }

    private View getAnamnesisLayout(AnamnesisParc message) {
        Log.d(LOG_TAG, "getAnamnesisLayout()");
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.advanced_message_list_item, null);

        // check if the case_item exists
        if (message != null) {
            TextView itemMessage = (TextView) v.findViewById(R.id.message_text_view);
            LinearLayout questionListView = (LinearLayout) v.findViewById(R.id.additional_info_list_layout);
            questionListView.removeAllViewsInLayout();

            itemMessage.setText(message.getMessage());

            // question list
            List<AnamnesisQuestionParc> questionList = message.getQuestions();
            if (questionList != null && questionList.size() > 0) {
                Log.d(LOG_TAG, "questionList is > 0");
                for (AnamnesisQuestionParc q : questionList) {
                    LayoutInflater inflater2 = LayoutInflater.from(getContext());
                    View qRootView = qRootView = inflater2.inflate(R.layout.anamnesis_question_answered_item, null);

                    TextView questionTextView = (TextView) qRootView.findViewById(R.id.question_text_view);
                    TextView answerTextView = (TextView) qRootView.findViewById(R.id.answer_text_view);

                    questionTextView.setText(q.getQuestion());

                    // add elements to the list
                    if (q instanceof AnamnesisQuestionBoolParc) {
                        AnamnesisQuestionBoolParc qB = (AnamnesisQuestionBoolParc)q;
                        answerTextView.setText(qB.getAnswer() + "");
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
}
