package sathishapp.json;

import android.app.Application;
import android.content.Context;

/**
 * Created by Sathish Mun on 05-07-2015.
 */
public class MyApplication extends Application {

    private static MyApplication sInstance;
   // public static final String API_KEY_STOREWALK = ;




    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static MyApplication getInstance()
    {
        return sInstance;
    }

    public static Context getAppContext()
    {
        return  sInstance.getApplicationContext();
    }
}
