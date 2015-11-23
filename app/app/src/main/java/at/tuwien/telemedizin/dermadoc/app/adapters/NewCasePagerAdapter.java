package at.tuwien.telemedizin.dermadoc.app.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.activities_fragments.CaseListFragment;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.DummyContentFragment;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case.EditPicturesFragment;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case.EditSymptomsFragment;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case.PictureReceiver;

/**
 * Created by FAUser on 17.11.2015.
 * Adapter for the ViewPager in NewCase
 *
 * following: https://www.bignerdranch.com/blog/viewpager-without-fragments/
 */
public class NewCasePagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    public NewCasePagerAdapter(Context context, FragmentManager manager) {
        super(manager);
        this.context = context;
    }

    public PictureReceiver addStandardFragments() {
        // add fragments according to the NewCasePagerEnum
        fragmentList.add(EditSymptomsFragment.newInstance(true));
        titleList.add(context.getString(NewCasePagerEnum.SYMPTOMS.getTitleResId()));

        EditPicturesFragment pictureReceiver = EditPicturesFragment.newInstance(true);
        fragmentList.add(pictureReceiver);
        titleList.add(context.getString(NewCasePagerEnum.PICTURE.getTitleResId()));
        fragmentList.add(DummyContentFragment.newInstance(NewCasePagerEnum.HISTORY.name()));
        titleList.add(context.getString(NewCasePagerEnum.HISTORY.getTitleResId()));
        fragmentList.add(DummyContentFragment.newInstance(NewCasePagerEnum.PHYSICIAN.name()));
        titleList.add(context.getString(NewCasePagerEnum.PHYSICIAN.getTitleResId()));

        return pictureReceiver;
    }

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
