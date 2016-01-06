package at.tuwien.telemedizin.dermadoc.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import at.tuwien.telemedizin.dermadoc.app.DataGenerator;
import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.CaseParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.PhysicianParc;
import at.tuwien.telemedizin.dermadoc.entities.CaseStatus;
import at.tuwien.telemedizin.dermadoc.entities.Physician;

/**
 * Created by FAUser on 18.11.2015.
 * Adapter to fill the MyCases-List
 *
 * following http://www.vogella.com/tutorials/AndroidListView/article.html
 */
public class CaseListAdapter extends ArrayAdapter<CaseParc> {
    public static final String LOG_TAG = CaseListAdapter.class.getSimpleName();

    private final Context context;
    private final List<CaseParc> values;


    public CaseListAdapter(Context context, List<CaseParc> values) {
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

        CaseParc caseItem = getItem(position);
        // check if the case_item exists
        if (caseItem != null) {
            TextView itemName = (TextView) v.findViewById(R.id.case_item_name);
            TextView itemStatus = (TextView) v.findViewById(R.id.case_item_status);
            TextView itemID = (TextView) v.findViewById(R.id.case_item_id);
            TextView itemPhysician = (TextView) v.findViewById(R.id.case_item_physician);
            TextView itemDateOfCreation = (TextView) v.findViewById(R.id.case_item_date_of_creation);

            ImageView itemNotificationIcon = (ImageView) v.findViewById(R.id.case_item_notification_icon);

            TableRow rowPhysician = (TableRow) v.findViewById(R.id.case_item_physician_row);

            itemName.setText(caseItem.getName());


            CaseStatus status = caseItem.getStatus();

            itemID.setText("" + caseItem.getId());
            itemStatus.setText(status.toString());
            // if the status is "active" or "closed" also show the physician
            if (status == CaseStatus.Active || status == CaseStatus.Closed) {
                rowPhysician.setVisibility(View.VISIBLE);
                PhysicianParc physician = caseItem.getPhysician();
                String physicianInfo = "";

                if(physician != null) {
                    physicianInfo = physician.getName();
                } else {
                    physicianInfo = context.getString(R.string.msg_no_physician_info_found);
                }
                itemPhysician.setText(physicianInfo);

            } else {
                // hide textView
                rowPhysician.setVisibility(View.GONE);
            }

            Calendar dateOfCreation = caseItem.getCreated();
            java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
            if (dateOfCreation != null) {
                String date = dateFormat.format(dateOfCreation.getTime());
                itemDateOfCreation.setText(date);
            }

            // TODO datums feld befüllen

//            itemNotificationIcon.setImageResource(R.drawable.case_item_notification_icon_shape);

            if (status == CaseStatus.Closed) {
                itemNotificationIcon.setImageResource(R.drawable.ic_action_lock_closed_black_18dp);
            } else if (status == CaseStatus.WaitingForAccept) {

                itemNotificationIcon.setImageResource(R.drawable.ic_action_tick_green_18dp);
            }

        }


        return v;
    }

}
