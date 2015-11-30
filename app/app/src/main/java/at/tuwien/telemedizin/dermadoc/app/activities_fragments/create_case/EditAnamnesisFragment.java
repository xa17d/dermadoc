package at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


    private OnTabChangedInFragmentInterface tabChangeInterface;

    private boolean anamnesisHintIsVisible;

    private ImageView anamnesisHelpIcon;
    private TextView anamnesisHelpText;

    private LinearLayout anamnesisQuestionListLayout;
    private Anamnesis anamnesisItem;
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

        this.anamnesisItem = mockAnamnesis(); // TODO
        this.questionViews = new ArrayList<>();
    }

    // TODO remove
    private Anamnesis mockAnamnesis() {

        AnamnesisQuestion q1 = new AnamnesisQuestionBool();
        q1.setQuestion("Has your cat show similar symptoms?");
        AnamnesisQuestion q2 = new AnamnesisQuestionText();
        q2.setQuestion("What's the name of your cat?");
        AnamnesisQuestion q3 = new AnamnesisQuestionText();
        q3.setQuestion("Why didn't you name her \"Samtpfote\"?");
        AnamnesisQuestion q4 = new AnamnesisQuestionBool();
        q4.setQuestion("I just need another question for this test-list. You can ignore it and do not have to bother answering");
        List<AnamnesisQuestion> qList = new ArrayList<>();
        qList.add(q1);
        qList.add(q2);
        qList.add(q3);
        qList.add(q4);

        return new Anamnesis(0, Calendar.getInstance(), new Physician(), "what message", qList);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            tabChangeInterface = (OnTabChangedInFragmentInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement " + OnTabChangedInFragmentInterface.class.getSimpleName());
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
        List<AnamnesisQuestion> questions = anamnesisItem.getQuestions();
        for (AnamnesisQuestion q : questions) {
            // add views to the linearLayout, each displaying one question-item + answer-view
            // according to question type
            View questionItemView;
            if (q instanceof AnamnesisQuestionBool) {
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
