package at.tuwien.telemedizin.dermadoc.app.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.CaseParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.Icd10DiagnosisParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.PhysicianParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.DiagnosisParc;
import at.tuwien.telemedizin.dermadoc.app.helper.FormatHelper;
import at.tuwien.telemedizin.dermadoc.entities.CaseStatus;

/**
 * Created by FAUser on 18.11.2015.
 * Adapter to fill the MyCases-List
 *
 * following http://www.vogella.com/tutorials/AndroidListView/article.html
 */
public class DiagnosisListAdapter extends ArrayAdapter<DiagnosisParc> {
    public static final String LOG_TAG = DiagnosisListAdapter.class.getSimpleName();

    private final Context context;
    private final List<DiagnosisParc> values;


    public DiagnosisListAdapter(Context context, List<DiagnosisParc> values) {
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
            v = inflater.inflate(R.layout.diagnoses_list_item, null);

        } else {
            // reset the icd10-list
            LinearLayout itemICD10List = (LinearLayout) v.findViewById(R.id.diagnsis_icd10_list_layout);
            itemICD10List.removeAllViewsInLayout();
        }

        DiagnosisParc diagnosis = getItem(position);
        // check if the case_item exists
        if (diagnosis != null) {
            TextView itemMessage = (TextView) v.findViewById(R.id.message_text_view);
            LinearLayout itemICD10List = (LinearLayout) v.findViewById(R.id.diagnsis_icd10_list_layout);
            TextView createdDate = (TextView) v.findViewById(R.id.creation_text);

            itemMessage.setText(diagnosis.getMessage());

            createdDate.setText(FormatHelper.calendarToDateFormatString(diagnosis.getCreated(), getContext()));

            // icd10 list
            List<Icd10DiagnosisParc> icd10List = diagnosis.getDiagnosisList();
            if (icd10List != null && icd10List.size() > 0) {
                Log.d(LOG_TAG, "icd10List is > 0");
                for (Icd10DiagnosisParc d10 : icd10List) {
                    // add elements to the list
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    View d10RootView = inflater.inflate(R.layout.diagnosis_icd10_list_item, null);

                    TextView diagnosisText = (TextView) d10RootView.findViewById(R.id.diagnosis_text);
                    diagnosisText.setText(d10.toString());
                    itemICD10List.addView(d10RootView);
                }
            }

        }


        return v;
    }

}
