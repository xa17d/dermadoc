package at.tuwien.telemedizin.dermadoc.app.adapters;

import android.content.Context;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.entities.PainIntensity;

/**
 * Created by FAUser on 22.11.2015.
 *
 * takes the enum provided in Entities and maps icons and text
 *
 */
public class PainIntensityMapper {

    public static PainIntensity[] getPainIntensitiesForGUI() {
        PainIntensity[] intensities = new PainIntensity[PainIntensity.values().length -1]; // Element "undefined" will not be shown
        for(int i = 0; i < PainIntensity.values().length - 1; i++) {
            intensities[i] = PainIntensity.values()[i + 1];
        }
        return intensities;
    }

    public static int getIconResource(PainIntensity intensity) {

        switch(intensity) {
            case NoPain: return R.drawable.pain_no_pain;

            case Mild: return R.drawable.pain_mild;

            case Moderate: return R.drawable.pain_moderate;

            case Severe: return R.drawable.pain_severe;

            case VerySevere: return R.drawable.pain_very_severe;

            case WorstPossible: return R.drawable.pain_worst_pain_possible;

            default: return R.drawable.ic_help_grey_48dp;
        }
    }

    public static int getTitleResource(PainIntensity intensity) {

        switch(intensity) {
            case NoPain: return R.string.pain_no_pain;

            case Mild: return R.string.pain_mild;

            case Moderate: return R.string.pain_moderate;

            case Severe: return R.string.pain_severe;

            case VerySevere: return R.string.pain_very_severe;

            case WorstPossible: return R.string.pain_worst_pain_possible;

            default: return R.string.pain_undefined;
        }
    }
}
