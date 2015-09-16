package commaterialdesign.horgan.gerard.myapplication;

import android.app.Application;
import android.content.Context;

/**
 * Created by rebeccamoore on 04/09/2015.
 */
public class MyApplication extends Application {
        public static final String API_KEY="ac219060c35f4a5f5d7bc6ac406f0dfb";
        private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
    }

    public static MyApplication getInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }
}
