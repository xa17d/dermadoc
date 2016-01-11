package at.tuwien.telemedizin.dermadoc.app.general_entities;

/**
 * Identifies a specific location on the body
 */
public enum BodyLocalization {

    HEAD,
    // arms
    HAND_LEFT, // manus
    HAND_RIGHT,
    FOREARM_LEFT, // antebrachium
    FOREARM_RIGHT,
    UPPER_ARM_LEFT, // brachium
    UPPER_ARM_RIGHT,
    SHOULDER_LEFT,
    SHOULDER_RIGHT,
    // torso
    THORAX,
    ABDOMEN,
    NECK_FRONT,
    PELVIS, // genital-area?
    //back
    PELVIS_BACK, // ...
    LOWER_BACK,
    UPPER_BACK, // ...
    NECK,
    // legs
    THIGH_LEFT, //
    THIGH_RIGHT,
    LOWER_LEG_LEFT,
    LOWER_LEG_RIGHT,
    FOOT_LEFT,
    FOOT_RIGHT;

}
