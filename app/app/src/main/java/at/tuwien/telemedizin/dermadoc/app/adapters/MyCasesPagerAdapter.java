package at.tuwien.telemedizin.dermadoc.app.adapters;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import at.tuwien.telemedizin.dermadoc.app.activites_fragments.CaseListFragment;

/**
 * Created by FAUser on 17.11.2015.
 * Adapter for the ViewPager in MyCases
 *
 * following: https://www.bignerdranch.com/blog/viewpager-without-fragments/
 */
public class MyCasesPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public MyCasesPagerAdapter(Context context, FragmentManager manager) {
        super(manager);
        this.context = context;
    }

    @Override
    public int getCount() {
        return MyCasesPagerEnum.values().length;
    }

    @Override
    public Fragment getItem(int position) {
        CaseListFragment caseListFragment;

        // old list or current list
        if (position == 0) {
            caseListFragment = CaseListFragment.newInstance("CURRENT", MyCasesPagerEnum.CURRENT.getKey()); // TODO set List
        } else {
            caseListFragment = CaseListFragment.newInstance("OLD", MyCasesPagerEnum.OLD.getKey()); // TODO set List
        }

        return caseListFragment;
    }

    @Override
    public String getPageTitle(int position) {
        return context.getString(MyCasesPagerEnum.values()[position].getTitleResId());
    }


//    public MyCasesPagerAdapter(Context context) {
//        this.context = context;
//    }
//
//    @Override
//    public Object instantiateItem(ViewGroup collection, int position) {
//        CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];
//        LayoutInflater inflater = LayoutInflater.from(context);
//        ViewGroup layout = (ViewGroup) inflater.inflate(customPagerEnum.getLayoutResId(), collection, false);
//        collection.addView(layout);
//        return layout;
//    }
//
//    @Override
//    public int getCount() {
//        return CustomPagerEnum.values().length;
//    }
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view == object;
//    }
//
//    @Override
//    public void destroyItem(ViewGroup collection, int position, Object view) {
//        collection.removeView((View) view);
//    }

}
