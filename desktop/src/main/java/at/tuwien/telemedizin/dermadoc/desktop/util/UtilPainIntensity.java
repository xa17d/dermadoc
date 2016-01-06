package at.tuwien.telemedizin.dermadoc.desktop.util;

import at.tuwien.telemedizin.dermadoc.entities.BodyLocalization;
import at.tuwien.telemedizin.dermadoc.entities.PainIntensity;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lucas on 16.12.2015.
 */
public class UtilPainIntensity {

    private static final String PAIN_MASK_PREFIX = "painscale/";

    private Map<PainIntensity,String> painMap = new HashMap<>();

    public UtilPainIntensity() {

        painMap.put(PainIntensity.NoPain, PAIN_MASK_PREFIX + "no_pain.png");
        painMap.put(PainIntensity.Mild, PAIN_MASK_PREFIX + "mild.png");
        painMap.put(PainIntensity.Moderate, PAIN_MASK_PREFIX + "moderate.png");
        painMap.put(PainIntensity.Severe, PAIN_MASK_PREFIX + "severe.png");
        painMap.put(PainIntensity.VerySevere, PAIN_MASK_PREFIX + "very_severe.png");
        painMap.put(PainIntensity.WorstPossible, PAIN_MASK_PREFIX + "worst_pain_possible.png");
    }

    public Image getImage(PainIntensity pain) {
        return new Image(getClass().getResourceAsStream(painMap.get(pain)));
    }
}
