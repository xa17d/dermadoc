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
    LOCATION(R.string.tab_header_location, 2l),
    ANAMNESIS(R.string.tab_header_anamnesis, 3l),
    PHYSICIAN(R.string.tab_header_physician, 4l),
    FINISH_EDITING(R.string.tab_header_finish_editing, 5l);

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
