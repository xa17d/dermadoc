package at.tuwien.telemedizin.dermadoc.desktop.util;

import at.tuwien.telemedizin.dermadoc.entities.BodyLocalization;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lucas on 16.12.2015.
 */
public class UtilBodyLocalization {

    private static final String LOCALIZATION_MASK_PREFIX = "bodylocalization/masks/mask_";

    private Map<BodyLocalization,String> localizationMap = new HashMap<>();

    public UtilBodyLocalization() {

        localizationMap.put(BodyLocalization.HEAD, LOCALIZATION_MASK_PREFIX + "head.png");
        localizationMap.put(BodyLocalization.HAND_LEFT, LOCALIZATION_MASK_PREFIX + "hand_left.png");
        localizationMap.put(BodyLocalization.HAND_RIGHT, LOCALIZATION_MASK_PREFIX + "hand_right.png");
        localizationMap.put(BodyLocalization.FOREARM_LEFT, LOCALIZATION_MASK_PREFIX + "forearm_left.png");
        localizationMap.put(BodyLocalization.FOREARM_RIGHT, LOCALIZATION_MASK_PREFIX + "forearm_right.png");
        localizationMap.put(BodyLocalization.UPPER_ARM_LEFT, LOCALIZATION_MASK_PREFIX + "upper_arm_left.png");
        localizationMap.put(BodyLocalization.UPPER_ARM_RIGHT, LOCALIZATION_MASK_PREFIX + "upper_arm_right.png");
        localizationMap.put(BodyLocalization.SHOULDER_LEFT, LOCALIZATION_MASK_PREFIX + "shoulder_left.png");
        localizationMap.put(BodyLocalization.SHOULDER_RIGHT, LOCALIZATION_MASK_PREFIX + "shoulder_right.png");
        localizationMap.put(BodyLocalization.THORAX, LOCALIZATION_MASK_PREFIX + "thorax.png");
        localizationMap.put(BodyLocalization.ABDOMEN, LOCALIZATION_MASK_PREFIX + "abdomen.png");
        localizationMap.put(BodyLocalization.NECK_FRONT, LOCALIZATION_MASK_PREFIX + "neck.png");
        localizationMap.put(BodyLocalization.PELVIS, LOCALIZATION_MASK_PREFIX + "pelvis.png");
        localizationMap.put(BodyLocalization.PELVIS_BACK, LOCALIZATION_MASK_PREFIX + "pelvis_back.png");
        localizationMap.put(BodyLocalization.LOWER_BACK, LOCALIZATION_MASK_PREFIX + "lower_back.png");
        localizationMap.put(BodyLocalization.UPPER_BACK, LOCALIZATION_MASK_PREFIX + "upper_back.png");
        localizationMap.put(BodyLocalization.NECK, LOCALIZATION_MASK_PREFIX + "neck_back.png");
        localizationMap.put(BodyLocalization.THIGH_LEFT, LOCALIZATION_MASK_PREFIX + "thigh_left.png");
        localizationMap.put(BodyLocalization.THIGH_RIGHT, LOCALIZATION_MASK_PREFIX + "thigh_right.png");
        localizationMap.put(BodyLocalization.LOWER_LEG_LEFT, LOCALIZATION_MASK_PREFIX + "lower_leg_left.png");
        localizationMap.put(BodyLocalization.LOWER_LEG_RIGHT, LOCALIZATION_MASK_PREFIX + "lower_leg_right.png");
        localizationMap.put(BodyLocalization.FOOT_LEFT, LOCALIZATION_MASK_PREFIX + "foot_left.png");
        localizationMap.put(BodyLocalization.FOOT_RIGHT, LOCALIZATION_MASK_PREFIX + "foot_right.png");
    }

    public Image getMain() {
        return new Image(getClass().getResourceAsStream("bodylocalization/main.png"));
    }

    public Image getMask(BodyLocalization localization) {
        return new Image(getClass().getResourceAsStream(localizationMap.get(localization)));
    }
}
