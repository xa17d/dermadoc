package at.tuwien.telemedizin.dermadoc.app.adapters;

import java.util.ArrayList;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.entities.BodyLocalizationZoomHelper;
import at.tuwien.telemedizin.dermadoc.entities.BodyLocalization;
import at.tuwien.telemedizin.dermadoc.entities.PainIntensity;

/**
 * Created by FAUser on 22.11.2015.
 *
 * takes the enum provided in Entities and maps icons and text
 *
 */
public class BodyLocalizationMapper {


    public static int[] getImageResource(BodyLocalization localization) {

        int[] resourceIdArray;
        List<Integer> resources = new ArrayList<Integer>();

        switch(localization) {
            case HEAD:
                resources.add(R.drawable.mask_head_front);
                resources.add(R.drawable.mask_head_back);
                break;

            case HAND_RIGHT:
                resources.add(R.drawable.mask_hand_right_front);
                 resources.add(R.drawable.mask_hand_right_back);
                break;

            case FOREARM_RIGHT:
                resources.add(R.drawable.mask_forearm_right_front);
                 resources.add(R.drawable.mask_forearm_right_back);
                break;

            case UPPER_ARM_RIGHT:
                resources.add(R.drawable.mask_upper_arm_right_front);
                 resources.add(R.drawable.mask_upper_arm_right_back);
                break;

            case SHOULDER_RIGHT:
                resources.add(R.drawable.mask_shoulder_right_front);
                resources.add(R.drawable.mask_shoulder_right_back);
                break;

            case HAND_LEFT:
                resources.add(R.drawable.mask_hand_left_front);
                resources.add(R.drawable.mask_hand_left_back);
                break;

            case FOREARM_LEFT:
                resources.add(R.drawable.mask_forearm_left_front);
                resources.add(R.drawable.mask_forearm_left_back);
                break;

            case UPPER_ARM_LEFT:
                resources.add(R.drawable.mask_upper_arm_left_front);
                resources.add(R.drawable.mask_upper_arm_left_back);
                break;

            case SHOULDER_LEFT:
                resources.add(R.drawable.mask_shoulder_left_front);
                resources.add(R.drawable.mask_shoulder_left_back);
                break;

            // ## legs ########################################################
            case FOOT_LEFT:
                resources.add(R.drawable.mask_foot_left_front);
                resources.add(R.drawable.mask_foot_left_back);
                break;

            case LOWER_LEG_LEFT:
                resources.add(R.drawable.mask_lower_leg_left_front);
                resources.add(R.drawable.mask_lower_leg_left_back);
                break;

            case THIGH_LEFT:
                resources.add(R.drawable.mask_thigh_left_front);
                resources.add(R.drawable.mask_thigh_left_back);
                break;

            case FOOT_RIGHT:
                resources.add(R.drawable.mask_foot_right_front);
                resources.add(R.drawable.mask_foot_right_back);
                break;

            case LOWER_LEG_RIGHT:
                resources.add(R.drawable.mask_lower_leg_right_front);
                resources.add(R.drawable.mask_lower_leg_right_back);
                break;

            case THIGH_RIGHT:
                resources.add(R.drawable.mask_thigh_right_front);
                resources.add(R.drawable.mask_thigh_right_back);
                break;

            // ## TORSO ###################
            case NECK_FRONT:
                resources.add(R.drawable.mask_neck_front);
                break;

            case THORAX:
                resources.add(R.drawable.mask_chest);
                break;

            case ABDOMEN:
                resources.add(R.drawable.mask_abdomen);
                break;

            case PELVIS:
                resources.add(R.drawable.mask_pelvis);
                break;

            // ## BACK ############
            case PELVIS_BACK:
                resources.add(R.drawable.mask_pelvis_back);
                break;

            case LOWER_BACK:
                resources.add(R.drawable.mask_lower_back);
                break;

            case UPPER_BACK:
                resources.add(R.drawable.mask_upper_back);
                break;

            case NECK:
                resources.add(R.drawable.mask_neck_back);
                break;



        }

        resourceIdArray = new int[resources.size()];
        for (int i = 0; i < resources.size(); i++) {
            resourceIdArray[i] = resources.get(i);
        }
        return resourceIdArray;
    }

    public static List<BodyLocalization> getBodyLocalizations(BodyLocalizationZoomHelper localization) {

        List<BodyLocalization> list = new ArrayList<BodyLocalization>();

        switch(localization) {
            case HEAD:
                list.add(BodyLocalization.HEAD);
                break;
            case ARM_RIGHT:
                list.add(BodyLocalization.HAND_RIGHT);
                list.add(BodyLocalization.FOREARM_RIGHT);
                list.add(BodyLocalization.UPPER_ARM_RIGHT);
                list.add(BodyLocalization.SHOULDER_RIGHT);
                break;
            case ARM_LEFT:
                list.add(BodyLocalization.HAND_LEFT);
                list.add(BodyLocalization.FOREARM_LEFT);
                list.add(BodyLocalization.UPPER_ARM_LEFT);
                list.add(BodyLocalization.SHOULDER_LEFT);
                break;
            case TORSO:
                list.add(BodyLocalization.THORAX);
                list.add(BodyLocalization.ABDOMEN);
                list.add(BodyLocalization.NECK_FRONT);
                list.add(BodyLocalization.PELVIS);
                break;
            case BACK:
                list.add(BodyLocalization.PELVIS_BACK);
                list.add(BodyLocalization.LOWER_BACK);
                list.add(BodyLocalization.UPPER_BACK);
                list.add(BodyLocalization.NECK);
                break;
            case LEGS:
                list.add(BodyLocalization.FOOT_LEFT);
                list.add(BodyLocalization.LOWER_LEG_LEFT);
                list.add(BodyLocalization.THIGH_LEFT);

                list.add(BodyLocalization.FOOT_RIGHT);
                list.add(BodyLocalization.LOWER_LEG_RIGHT);
                list.add(BodyLocalization.THIGH_RIGHT);
                break;
        }

        return list;
    }
//
//    public static int getTitleResource(PainIntensity intensity) {
//
//        switch(intensity) {
//            case NoPain: return R.string.pain_no_pain;
//
//            case Mild: return R.string.pain_mild;
//
//            case Moderate: return R.string.pain_moderate;
//
//            case Severe: return R.string.pain_severe;
//
//            case VerySevere: return R.string.pain_very_severe;
//
//            case WorstPossible: return R.string.pain_worst_pain_possible;
//
//            default: return R.string.pain_undefined;
//        }
//    }
}
