package at.tuwien.telemedizin.dermadoc.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.general_entities.PainIntensity;

/**
 * Created by FAUser on 22.11.2015.
 */
public class PainAssessmentArrayAdapter extends ArrayAdapter<PainIntensity> {

    private Context context;
    private List<PainIntensity> intensities;

    public PainAssessmentArrayAdapter(Context context, int resource, List<PainIntensity> values ) {
        super(context, resource, values);
        this.context = context;
        intensities = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = convertView;

        // inflate new view or recycle the old one
        if (convertView == null) {
            root = inflater.inflate(R.layout.pain_assessment_element, parent, false);
        }

        TextView titleTextView = (TextView) root.findViewById(R.id.pain_element_title);
        ImageView iconImageView = (ImageView) root.findViewById(R.id.pain_element_icon);

        PainIntensity intensity = intensities.get(position);
//
//        Log.d("GridAdapter val:", position + ", " + intensity.name() + ", " + PainIntensityFix.Moderate + ", " + PainIntensityFix.values()[0].toString() + ", " + PainIntensityFix.values().length);
        titleTextView.setText(PainIntensityMapper.getTitleResource(intensity));
        iconImageView.setImageResource(PainIntensityMapper.getIconResource(intensity));

        return root;
    }

    public View getDropDownView(int position, View convertView,ViewGroup parent)
    {
        return getView(position, convertView, parent);
    }



}
