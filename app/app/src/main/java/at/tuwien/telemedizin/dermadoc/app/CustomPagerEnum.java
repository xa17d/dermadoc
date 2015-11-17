package at.tuwien.telemedizin.dermadoc.app;

/**
 * Created by FAUser on 17.11.2015.
 *
 * Enum for the ViewPager in MyCases
 */
public enum CustomPagerEnum {

    CURRENT(R.string.tab_header_current, R.layout.case_list_layout),
    OLD(R.string.tab_header_old, R.layout.case_list_layout);

    private int mTitleResId;
    private int mLayoutResId;

    CustomPagerEnum(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}
