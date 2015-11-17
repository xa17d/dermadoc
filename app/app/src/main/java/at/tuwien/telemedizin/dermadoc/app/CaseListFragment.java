package at.tuwien.telemedizin.dermadoc.app;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CaseListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CaseListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1"; // TODO remove
    String param1;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param param1 is a param for testing purpose // TODO remove
     *
     * @return A new instance of fragment CaseListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CaseListFragment newInstance(String param1) {
        CaseListFragment fragment = new CaseListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public CaseListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            param1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.case_list_layout, container, false);
        TextView testTextView = (TextView) root.findViewById(R.id.testTextView_A); // TODO remove
        testTextView.setText(param1);

        return root;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


}
