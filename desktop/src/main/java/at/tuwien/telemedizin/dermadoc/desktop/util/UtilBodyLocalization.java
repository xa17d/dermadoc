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
        localizationMap.put(BodyLocalization.ABDOMEN, LOCALIZATION_MASK_PREFIX + "abdomen.png");
    }

    public Image getMain() {
        return new Image(getClass().getResourceAsStream("bodylocalization/main.png"));
    }

    public Image getMask(BodyLocalization localization) {
        return new Image(getClass().getResourceAsStream(localizationMap.get(localization)));
    }
}
