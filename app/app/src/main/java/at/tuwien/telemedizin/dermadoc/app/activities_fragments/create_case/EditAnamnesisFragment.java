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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AnamnesisParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AnamnesisQuestionBoolParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AnamnesisQuestionParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AnamnesisQuestionTextParc;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.casedata.Anamnesis;
import at.tuwien.telemedizin.dermadoc.entities.casedata.AnamnesisQuestion;
import at.tuwien.telemedizin.dermadoc.entities.casedata.AnamnesisQuestionBool;
import at.tuwien.telemedizin.dermadoc.entities.casedata.AnamnesisQuestionText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditAnamnesisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditAnamnesisFragment extends Fragment {

    public static final String LOG_TAG = EditAnamnesisFragment.class.getSimpleName();
    private OnTabChangedInFragmentInterface tabChangeInterface;
    private OnCaseDataRequestAndUpdateInterface caseDataInterface;

    private boolean anamnesisHintIsVisible;

    private ImageView anamnesisHelpIcon;
    private TextView anamnesisHelpText;

    private LinearLayout anamnesisQuestionListLayout;
    private AnamnesisParc anamnesisItem;
    private List<View> questionViews; //list containing the views displaying each question - the indexes of question and question-view are equal

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment EditSymptomsFragment.
     */
    public static EditAnamnesisFragment newInstance() {
        EditAnamnesisFragment fragment = new EditAnamnesisFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public EditAnamnesisFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

        this.anamnesisItem = caseDataInterface.getAnamnesisForm();
        this.questionViews = new ArrayList<>();
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
        View v = inflater.inflate(R.layout.fragment_edit_anamnesis_layout, container, false);

        anamnesisHelpIcon = (ImageView) v.findViewById(R.id.anamnesis_help_icon_view);
        anamnesisHelpText = (TextView) v.findViewById(R.id.anamnesis_help_hint_text_view);

        anamnesisHelpIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make hint visible or hide it
                toggleAnamnesisHint();

            }
        });

        anamnesisHelpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make hint visible or hide it
                toggleAnamnesisHint();
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

        anamnesisQuestionListLayout = (LinearLayout) v.findViewById(R.id.anamnesis_question_list_linlayout);
        setUpQuestionList(inflater, anamnesisQuestionListLayout);


        return v;
    }

    private void setUpQuestionList(LayoutInflater inflater, LinearLayout listRoot) {
        List<AnamnesisQuestionParc> questions = anamnesisItem.getQuestions();
        for (AnamnesisQuestionParc q : questions) {
            // add views to the linearLayout, each displaying one question-item + answer-view
            // according to question type
            View questionItemView;
            if (q instanceof AnamnesisQuestionBoolParc) {
                questionItemView = inflater.inflate(R.layout.anamnesis_question_bool_item, null, false);

                // get Radio-Buttons and set option-text
//                RadioButton answerButton1 = (RadioButton) questionItemView.findViewById(R.id.question_answer_1);
//                RadioButton answerButton2 = (RadioButton) questionItemView.findViewById(R.id.question_answer_2);
            } else {
                questionItemView = inflater.inflate(R.layout.anamnesis_question_text_item, null, false);
            }

            // set question text
            TextView questionText = (TextView) questionItemView.findViewById(R.id.question_text_view);
            questionText.setText(q.getQuestion());

            // add the view to the layout and to the list for getting the answers later
            questionViews.add(questionItemView);
            listRoot.addView(questionItemView);
        }
    }

    public AnamnesisParc getFilledAnamnesis() {
        // get the data from the views
        List<AnamnesisQuestionParc> questions = anamnesisItem.getQuestions();
        for(int i = 0; i < questions.size(); i++) {
            AnamnesisQuestionParc q = questions.get(i);
            View qItemLayout = questionViews.get(i);
            if (q instanceof AnamnesisQuestionBoolParc) {
                boolean firstOptionChecked = ((RadioButton)qItemLayout.findViewById(R.id.question_answer_1)).isChecked();
                ((AnamnesisQuestionBoolParc)q).setAnswer(firstOptionChecked);
                Log.d(LOG_TAG, "answer " + i + " " + ((AnamnesisQuestionBoolParc) anamnesisItem.getQuestions().get(i)).getAnswer());
            } else {
                String answerString = ((TextView)qItemLayout.findViewById(R.id.question_answer_text)).getText().toString();
                ((AnamnesisQuestionTextParc)q).setAnswer(answerString);
                Log.d(LOG_TAG, "answer " + i + " " + ((AnamnesisQuestionTextParc) anamnesisItem.getQuestions().get(i)).getAnswer());
            }
            // TODO do i have to remove and add the question-object to the list?

        }
        return anamnesisItem;
    }


    private void toggleAnamnesisHint() {
        if (anamnesisHintIsVisible) {
            anamnesisHelpText.setVisibility(View.GONE);
            anamnesisHelpIcon.setImageResource(R.drawable.ic_action_help_holo_light_18dp);
            anamnesisHintIsVisible = false;
        } else {
            anamnesisHelpText.setVisibility(View.VISIBLE);
            anamnesisHelpIcon.setImageResource(R.drawable.ic_action_help_yellow_dark_18dp);
            anamnesisHintIsVisible = true;
        }
    }

}
