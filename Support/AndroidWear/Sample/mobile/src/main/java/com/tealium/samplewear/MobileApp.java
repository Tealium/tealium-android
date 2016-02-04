package com.tealium.samplewear;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.SystemClock;

import com.tealium.library.Tealium;

import java.util.HashMap;
import java.util.Map;

public class MobileApp extends Application {

    public static final String TEALIUM_MAIN = "main";

    @Override
    public void onCreate() {
        super.onCreate();

        Tealium.createInstance(
                TEALIUM_MAIN,
                Tealium.Config.create(this, "tealiummobile", "demo", "dev"));

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

                final Map<String, Object> data = new HashMap<>(2);
                data.put("ms_since_launch", String.valueOf(SystemClock.elapsedRealtime() - launchTime));
                data.put("categories", new String[]{"alpha", "beta", "gamma"});

                final String viewName = "Mobile_" + activity.getClass().getSimpleName();

                final Tealium instance = Tealium.getInstance(TEALIUM_MAIN);

                // Instance is killable remotely
                if (instance != null) {
                    Tealium.getInstance(TEALIUM_MAIN).trackView(viewName, data);
                }
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
