package commaterialdesign.horgan.gerard.myapplication.logging;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by rebeccamoore on 10/09/2015.
 */
public class L {
    public static void m(String message) {
        Log.d("hoggy", "" + message);
    }

    public static void t(Context context, String message) {
        Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show();
    }
    public static void T(Context context, String message) {
        Toast.makeText(context, message + "", Toast.LENGTH_LONG).show();
    }
}