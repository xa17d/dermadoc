package at.tuwien.telemedizin.dermadoc.app.adapters;

import at.tuwien.telemedizin.dermadoc.app.R;

/**
 * Created by FAUser on 17.11.2015.
 *
 * Enum for the ViewPager in MyCases
 */
public enum MyCasesPagerEnum {

    CURRENT(R.string.tab_header_current, R.layout.case_list_layout,0l),
    OLD(R.string.tab_header_old, R.layout.case_list_layout, 1l);

    private int mTitleResId;
    private int mLayoutResId;
    private long mKey;

    MyCasesPagerEnum(int titleResId, int layoutResId, long key) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
        mKey = key;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

    public long getKey() { return mKey; }

}
