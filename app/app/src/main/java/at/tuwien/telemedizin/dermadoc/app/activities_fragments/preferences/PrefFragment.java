package at.tuwien.telemedizin.dermadoc.app.activities_fragments.preferences;


import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceFragment;

import at.tuwien.telemedizin.dermadoc.app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrefFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrefFragment extends PreferenceFragment {



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment PreferenceFragment.
     */
    public static PrefFragment newInstance() {
        PrefFragment fragment = new PrefFragment();

        return fragment;
    }

    public PrefFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }



}
