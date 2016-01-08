package at.tuwien.telemedizin.dermadoc.app.activities_fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.activities_fragments.case_specific.CaseActivity;
import at.tuwien.telemedizin.dermadoc.app.adapters.CaseListAdapter;
import at.tuwien.telemedizin.dermadoc.app.comparators.CaseListItemComparator;
import at.tuwien.telemedizin.dermadoc.app.comparators.CaseSortCategory;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.CaseListItem;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.CaseParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.NotificationParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.PatientParc;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CaseListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CaseListFragment extends Fragment {

    public static final String LOG_TAG = CaseListFragment.class.getSimpleName();


    private static final String ARG_LIST_KEY = "listKey";
    private long listKey;

    private List<CaseListItem> listValues;

    private ListView listView;
    private CaseListAdapter adapter;

    private OnCaseListEventListener mListener;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CaseListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CaseListFragment newInstance(long listKey) {
        CaseListFragment fragment = new CaseListFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_LIST_KEY, listKey);
        fragment.setArguments(args);
        return fragment;
    }

    public CaseListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            listKey = getArguments().getLong(ARG_LIST_KEY);
        }

        Log.d(LOG_TAG, "listKey = " + listKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView()");
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.case_list_layout, container, false);
//        TextView testTextView = (TextView) root.findViewById(R.id.testTextView_A); // TODO remove
//        testTextView.setText(param1);

        TextView emptyView = (TextView) root.findViewById(R.id.empty_view);

        listView = (ListView) root.findViewById(R.id.caseListView);
        listView.setEmptyView(emptyView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // start the CaseActivity and open the selected case

                Intent intent = new Intent(getContext(), CaseActivity.class);

                intent.putExtra(CaseListItem.INTENT_KEY, adapter.getItem(position));

                // TODO change
                PatientParc currentUser = ((MainActivity) getActivity()).getUser();
                intent.putExtra(CaseActivity.USER_INTENT_KEY, currentUser);
                startActivity(intent);
            }
        });


        setUpListAndAdapter();

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnCaseListEventListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement " + OnCaseListEventListener.class.getSimpleName());
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sort_id) {
            item.setChecked(true);
            sortCaseList(CaseSortCategory.ID);
            return true;
        } else if (id == R.id.action_sort_name) {
            item.setChecked(true);
            sortCaseList(CaseSortCategory.NAME);
            return true;
        } else if (id == R.id.action_sort_date_of_creation) {
            item.setChecked(true);
            sortCaseList(CaseSortCategory.DATE_OF_CREATION);
            return true;
//        } else if (id == R.id.action_sort_last_modified) {
//            item.setChecked(true);
//            return true;
        } else if (id == R.id.action_sort_status) {
            item.setChecked(true);
            sortCaseList(CaseSortCategory.STATUS);
            return true;
        }

        return false;
    }

    private void checkSortingCategory() {
        CaseSortCategory sortCategory = mListener.onCaseSortCategoryRequest();
        sortCaseList(sortCategory);
    }

    private void setUpListAndAdapter() {
        List<CaseParc> caseList = mListener.onListRequest(listKey, this);
        List<NotificationParc> notificationList = mListener.onNotificationsRequest();
        Log.d(LOG_TAG, "caseList -size = " + caseList.size());
        Log.d(LOG_TAG, "notificationList -size = " + notificationList.size());
        listValues = caseListToItemList(caseList, notificationList);
        adapter = new CaseListAdapter(getContext(), listValues);
        listView.setAdapter(adapter);
        checkSortingCategory();
    }

    public void informCaseListChanged() {
//        ((OnCaseListEventListener)getActivity()).onListRequest(listKey, this);
        setUpListAndAdapter();
    }

    public void informNotificationListChanged() {
//        setUpListAndAdapter(); // TODO
    }

    private List<CaseListItem> caseListToItemList(List<CaseParc> caseList, List<NotificationParc> notificationList) {

        // map the notifications to the caseParc-objects
        List<CaseListItem> itemList = new ArrayList<>();
        if (caseList == null) {
            return itemList;
        }

        List<NotificationParc> notifications = new ArrayList<>();
        if (notificationList != null) {
            notifications = notificationList;
        }

        for (CaseParc c : caseList) {
            List<NotificationParc> caseNotifications = new ArrayList<>();
            // iterate through notifications and filter the according notifications
            for (NotificationParc nP : notifications) {
                if (nP.getCaseId() == c.getId()) {
                    caseNotifications.add(nP);
                }
            }
            itemList.add(new CaseListItem(c, caseNotifications));
        }
        return itemList;
    }


    private void sortCaseList(CaseSortCategory sortCategory) {
        Log.d(LOG_TAG, "Fragment " + listKey + " sortCaseList(" + sortCategory + ")");

        CaseListItemComparator caseComparator = new CaseListItemComparator();
        if (sortCategory != null) {
            caseComparator.setActiveCategory(sortCategory);
        }

        Log.d(LOG_TAG, "adapter sort" + (adapter!=null));
        if (adapter != null) {
            adapter.sort(caseComparator);
            adapter.notifyDataSetChanged();
        }

        // tell the activity
        ((OnCaseListEventListener)getActivity()).onSettingNewCaseSortCategory(sortCategory);
    }

    /**
     * this method should be called by the viewPager and is therefore used to
     * check if this fragment is now visible (selected)
     * When it is selected, it will check the sorting order to ensure the
     * list is sorted correctly
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        Log.d(LOG_TAG, "CalledVisibilityHint in " + listKey + "isVisible: " + this.isVisible() + " | isVisibleTouser: " + isVisibleToUser);
        // This view is visible
        if (this.isVisible()) {
            // If this view is becoming visible to the user, then...
            if (isVisibleToUser) {
                checkSortingCategory();
            }
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


    // interface for callbacks to activity (get list etc)
    public interface OnCaseListEventListener {

        /**
         * the fragment requests the case-list it should show in its listView.
         * Therefore the listKey is indicates which list (current, old) the fragment will get
         *
         * @param listKey listKey from the MyCasesPagerEnum
         * @return
         */
        public List<CaseParc> onListRequest(long listKey, CaseListFragment fragment);

        /**
         * the fragment requests the notification list (not filtered)
         *
         * @return
         */
        public List<NotificationParc> onNotificationsRequest();


        /**
         * the fragment requests the active case-sort-category
         * @return
         */
        public CaseSortCategory onCaseSortCategoryRequest();

        /**
         * the fragment handles a menu-item selection (sort) and sets the
         * sort-category in the activity to store the active category even when the
         * fragment is destroyed
         * @return
         */
        public void onSettingNewCaseSortCategory(CaseSortCategory caseSortCategory);
    }
}
