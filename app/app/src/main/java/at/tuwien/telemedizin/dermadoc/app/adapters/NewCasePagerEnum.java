package at.tuwien.telemedizin.dermadoc.app.adapters;

import at.tuwien.telemedizin.dermadoc.app.R;

/**
 * Created by FAUser on 17.11.2015.
 *
 * Enum for the tabs = steps when creating a new case
 */
public enum NewCasePagerEnum {

    SYMPTOMS(R.string.tab_header_symptoms, 0l),
    PICTURE(R.string.tab_header_pictures, 1l),
    HISTORY(R.string.tab_header_history, 2l),
    PHYSICIAN(R.string.tab_header_physician, 3l);

    private int mTitleResId;
    private long mKey;

    NewCasePagerEnum(int titleResId, long key) {
        mTitleResId = titleResId;
        mKey = key;
    }

    public int getTitleResId() {
        return mTitleResId;
    }


    public long getKey() { return mKey; }

}
