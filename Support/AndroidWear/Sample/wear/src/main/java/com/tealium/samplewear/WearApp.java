package com.tealium.samplewear;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import com.google.android.gms.wearable.DataMap;
import com.tealium.wear.TealiumWear;

public class WearApp extends Application {

    public static final String TEALIUM_MAIN = "main";

    @Override
    public void onCreate() {
        super.onCreate();

        TealiumWear.createInstance(
                TEALIUM_MAIN,
                TealiumWear.Config.create(this)
                        .setLogLevel(Log.VERBOSE));

        registerActivityLifecycleCallbacks(createActivityLifecycleCallbacks());
    }

    private static ActivityLifecycleCallbacks createActivityLifecycleCallbacks() {

        final long launchTime = SystemClock.elapsedRealtime();

        return new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

                final DataMap data = new DataMap();
                data.putLong("ms_since_launch", launchTime - SystemClock.elapsedRealtime());
                data.putStringArray("categories", new String[]{"alpha", "beta", "gamma"});

                final String viewName = "Wear_" + activity.getClass().getSimpleName();

                TealiumWear.getInstance(TEALIUM_MAIN).trackView(viewName, data);
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        };
    }
}
