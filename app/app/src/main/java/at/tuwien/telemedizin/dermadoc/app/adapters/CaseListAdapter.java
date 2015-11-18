package at.tuwien.telemedizin.dermadoc.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import at.tuwien.telemedizin.dermadoc.app.DataGenerator;
import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.CaseStatus;
import at.tuwien.telemedizin.dermadoc.entities.Physician;

/**
 * Created by FAUser on 18.11.2015.
 * Adapter to fill the MyCases-List
 *
 * following http://www.vogella.com/tutorials/AndroidListView/article.html
 */
public class CaseListAdapter extends ArrayAdapter<Case> {
    public static final String LOG_TAG = CaseListAdapter.class.getSimpleName();

    private final Context context;
    private final List<Case> values;


    public CaseListAdapter(Context context, List<Case> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        // save resources: only inflate when == null
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.case_list_item, null);

        }

        Case caseItem = getItem(position);
        // check if the case_item exists
        if (caseItem != null) {
            TextView itemName = (TextView) v.findViewById(R.id.case_item_name);
            TextView itemStatus = (TextView) v.findViewById(R.id.case_item_status);
            TextView itemID = (TextView) v.findViewById(R.id.case_item_id);
            TextView itemPhysician = (TextView) v.findViewById(R.id.case_item_physician);

            ImageView itemNotificationIcon = (ImageView) v.findViewById(R.id.case_item_notification_icon);

//            itemName.setText("" + caseItem.getName()); // TODO
            itemName.setText("Test Case - I experience severe pain"); // TODO remove

            CaseStatus status = caseItem.getStatus();

            itemID.setText("" + caseItem.getId());
            itemStatus.setText(status.toString());
            // if the status is "active" or "closed" also show the physician
            if (status == CaseStatus.Active || status == CaseStatus.Closed) {
                itemPhysician.setVisibility(View.VISIBLE);
                Physician physician = caseItem.getPhysician();
                String physicianInfo = "";

                if(physician != null) {
                    physicianInfo = physician.getName();
                } else {
                    physicianInfo = context.getString(R.string.msg_no_physician_info_found);
                }
                itemPhysician.setText(physicianInfo);

            } else {
                // hide textView
                itemPhysician.setVisibility(View.INVISIBLE);
            }

//            itemNotificationIcon.setImageResource(R.drawable.case_item_notification_icon_shape);

            if (status == CaseStatus.Closed) {
                itemNotificationIcon.setImageResource(R.drawable.ic_done_black_48dp); // TODO remove
            }
        }


        return v;
    }

}
