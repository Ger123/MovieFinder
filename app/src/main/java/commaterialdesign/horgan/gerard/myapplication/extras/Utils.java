package commaterialdesign.horgan.gerard.myapplication.extras;

/**
 * Created by rebeccamoore on 10/09/2015.
 */

import android.os.Build;




public class Utils {
    public static boolean isLollipopOrGreater() {
        return Build.VERSION.SDK_INT >= 21 ? true : false;
    }
    public static boolean isJellyBeanOrGreater(){
        return Build.VERSION.SDK_INT>=16?true:false;
    }
}
