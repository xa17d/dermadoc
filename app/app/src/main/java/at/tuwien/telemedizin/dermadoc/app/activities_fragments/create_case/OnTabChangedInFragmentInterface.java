package at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case;

/**
 * Created by FAUser on 22.11.2015.
 *
 * this interface is utilised, so that fragments in tab/viewPager layouts can tell the activity to
 * switch to another tab
 */
public interface OnTabChangedInFragmentInterface {

    public void switchToTheNextTab();

    public void switchToTab(int index);

}
