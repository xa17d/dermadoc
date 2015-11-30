package at.tuwien.telemedizin.dermadoc.app.activities_fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import at.tuwien.telemedizin.dermadoc.app.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DummyContentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param param1 is a test param // TODO remove
     * @return A new instance of fragment MyCasesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DummyContentFragment newInstance(String param1) {
        DummyContentFragment fragment = new DummyContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DummyContentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_dummy_content_layout, container, false);

        // TODO remove
        TextView textView = (TextView) root.findViewById(R.id.testTextView);
        textView.setText(mParam1);
        return root;
    }



    @Override
    public void onDetach() {
        super.onDetach();
    }



}
