package at.tuwien.telemedizin.dermadoc.app;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 *
 * View Pager: nested Fragments implementation following: https://github.com/commonsguy/cw-omnibus/blob/master/ViewPager/Nested/src/com/commonsware/android/pagernested/PagerFragment.java
 */
public class MyCasesFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private TabLayout tabLayout;
    private ViewPager viewPager;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment MyCasesFragment.
     */
    public static MyCasesFragment newInstance() {
        MyCasesFragment fragment = new MyCasesFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public MyCasesFragment() {
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
        View root = inflater.inflate(R.layout.fragment_my_cases_layout, container, false);

        // tabLayout - setup
        tabLayout = (TabLayout) root.findViewById(R.id.tabs);
        setupTabLayout(tabLayout);

        // view Pager for TabLayout
        viewPager = (ViewPager) root.findViewById(R.id.case_list_view_pager);
        setupViewPager(viewPager);

        // connect viewPager and tabLayout
        tabLayout.setupWithViewPager(viewPager);

        return root;
    }



    /**
     * setup the tab-layout: add tabs
     * @param tabLayout
     */
    private void setupTabLayout(TabLayout tabLayout) {
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_header_current));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_header_old));
    }

    /**
     * setup view Pager - tabs, etc.
     * @param viewPager the viewPager layout element
     */
    private void setupViewPager(ViewPager viewPager) {
        viewPager.setAdapter(new MyCasesPagerAdapter(getContext(), getChildFragmentManager()));
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

}
