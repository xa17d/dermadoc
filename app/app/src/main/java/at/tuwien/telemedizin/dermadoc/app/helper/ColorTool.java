package at.tuwien.telemedizin.dermadoc.app.helper;

import android.graphics.Color;
import android.util.Log;

/**
 * Created by FAUser on 28.11.2015.
 *
 * Required in Location-Fragment
 * following: https://blahti.wordpress.com/2012/06/26/images-with-clickable-areas/
 *
 * check if a color is similar to another (pixel-colors can differ from drawable-pixel-colors)
 * Check for matches despite variations
 */
public class ColorTool {
    public static final String LOG_TAG = ColorTool.class.getSimpleName();

    public boolean closeMatch (int color1, int color2, int tolerance) {
        Log.d(LOG_TAG, "closeMatch() color1: " + color1 + ", color2: " + color2);
        Log.d(LOG_TAG, "Color.red: " + Color.red(color1) + " | " + Color.red(color2));
        Log.d(LOG_TAG, "Color.green: " + Color.green(color1) + " | " + Color.green(color2));
        Log.d(LOG_TAG, "Color.blue: " + Color.blue(color1) + " | " + Color.blue(color2));

        if ((int) Math.abs (Color.red(color1) - Color.red (color2)) > tolerance )
            return false;
        if ((int) Math.abs (Color.green (color1) - Color.green (color2)) > tolerance )
            return false;
        if ((int) Math.abs (Color.blue (color1) - Color.blue (color2)) > tolerance )
            return false;
        return true;
    } // end match

}
