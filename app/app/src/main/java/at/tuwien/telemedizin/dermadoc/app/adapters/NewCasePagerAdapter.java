package at.tuwien.telemedizin.dermadoc.app.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.activities_fragments.DummyContentFragment;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case.EditAnamnesisFragment;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case.EditLocationFragment;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case.EditPicturesFragment;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case.EditSymptomsFragment;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case.FinishEditingFragment;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case.PhysicianSelectionFragment;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case.PictureReceiver;

/**
 * Created by FAUser on 17.11.2015.
 * Adapter for the ViewPager in NewCase
 *
 * following: https://www.bignerdranch.com/blog/viewpager-without-fragments/
 */
public class NewCasePagerAdapter extends FragmentPagerAdapter {

    private HashMap<NewCasePagerEnum, Integer> categoryToIndexMap; // maps Pager-Enum category to list-index

    private Context context;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    public NewCasePagerAdapter(Context context, FragmentManager manager) {
        super(manager);
        this.context = context;
        this.categoryToIndexMap = new HashMap<>();
    }

//    public PictureReceiver addStandardFragments() {
//        // add fragments according to the NewCasePagerEnum
//        fragmentList.add(EditSymptomsFragment.newInstance(true));
//        titleList.add(context.getString(NewCasePagerEnum.SYMPTOMS.getTitleResId()));
//
//        EditPicturesFragment pictureReceiver = EditPicturesFragment.newInstance(true, false);
//        fragmentList.add(pictureReceiver);
//        titleList.add(context.getString(NewCasePagerEnum.PICTURE.getTitleResId()));
//
//        fragmentList.add(EditLocationFragment.newInstance(true, false));
//        titleList.add(context.getString(NewCasePagerEnum.LOCATION.getTitleResId()));
//
//        fragmentList.add(EditAnamnesisFragment.newInstance());
//        titleList.add(context.getString(NewCasePagerEnum.ANAMNESIS.getTitleResId()));
//
//        fragmentList.add(PhysicianSelectionFragment.newInstance());
//        titleList.add(context.getString(NewCasePagerEnum.PHYSICIAN.getTitleResId()));
//
//        fragmentList.add(FinishEditingFragment.newInstance(true));
//        titleList.add(context.getString(NewCasePagerEnum.FINISH_EDITING.getTitleResId()));
//        return pictureReceiver;
//    }

    public void addFragments(List<Fragment> fragmentList, List<String> titleList) {
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

//    public Fragment getFragment(NewCasePagerEnum category) {
//        switch(category) {
//            case SYMPTOMS:
//            break;
//
//        }
//    }

    public void addFragments(List<Fragment> fragments) {
        for (Fragment f : fragments) {
            fragmentList.add(f);
        }
    }



    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public String getPageTitle(int position) {
        return titleList.get(position);
    }


}
