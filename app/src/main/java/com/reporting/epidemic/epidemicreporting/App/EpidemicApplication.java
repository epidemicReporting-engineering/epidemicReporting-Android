package com.reporting.epidemic.epidemicreporting.App;

import android.app.Application;
import android.content.Context;

/**
 * Created by eleven on 2018/10/2.
 */

public class EpidemicApplication extends Application {

    private static EpidemicApplication mEpidemicApplication;
    private static Context mContext;

    public synchronized static EpidemicApplication getInstance() {
        if (mEpidemicApplication ==  null) {
            mEpidemicApplication  = new EpidemicApplication();
        }
        return mEpidemicApplication;
    }

    public static Context getAppContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
}
