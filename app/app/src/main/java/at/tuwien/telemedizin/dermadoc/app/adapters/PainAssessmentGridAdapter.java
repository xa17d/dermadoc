package at.tuwien.telemedizin.dermadoc.app.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.entities.PainIntensityFix;
import at.tuwien.telemedizin.dermadoc.entities.PainIntensity;

/**
 * Created by FAUser on 22.11.2015.
 *
 * Needed to create the Pain-Assessment-Grid layout
 */
public class PainAssessmentGridAdapter extends BaseAdapter {

    private Context context;
    private PainIntensity[] gridValues = PainIntensityMapper.getPainIntensitiesForGUI();

    public PainAssessmentGridAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return gridValues.length;
    }

    @Override
    public Object getItem(int position) {
        return gridValues[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root;

        root = inflater.inflate(R.layout.pain_assessment_element, parent, false);
        TextView titleTextView = (TextView) root.findViewById(R.id.pain_element_title);
        ImageView iconImageView = (ImageView) root.findViewById(R.id.pain_element_icon);

        PainIntensity intensity = gridValues[position];
//
//        Log.d("GridAdapter val:", position + ", " + intensity.name() + ", " + PainIntensityFix.Moderate + ", " + PainIntensityFix.values()[0].toString() + ", " + PainIntensityFix.values().length);
        titleTextView.setText(PainIntensityMapper.getTitleResource(intensity));
        iconImageView.setImageResource(PainIntensityMapper.getIconResource(intensity));

        return root;
    }
}
