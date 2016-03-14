package com.tealium.example.helper;

import android.app.Application;
import android.os.Build;
import android.util.Log;
import android.webkit.WebView;

import com.tealium.example.BuildConfig;
import com.tealium.internal.data.Dispatch;
import com.tealium.library.DataSources;
import com.tealium.library.DispatchValidator;
import com.tealium.library.Tealium;
import com.tealium.lifecycle.LifeCycle;

import java.util.Map;

/**
 * This class abstracts interaction with the Tealium library and simplifies comprehension
 */
public final class TealiumHelper {

    private final static String TAG = "TealiumHelper";

    // Identifier for the main Tealium instance
    public static final String TEALIUM_MAIN = "main";

    // Not instantiatable.
    private TealiumHelper() {
    }

    public static void initialize(Application application) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        final Tealium.Config config = Tealium.Config.create(application, "tealiummobile", "demo", "dev");

        if (BuildConfig.DEBUG) {
            // Log when LifeCycle events occur
            config.getDispatchValidators().add(createLifeCycleListener());
        }

        LifeCycle.setupInstance(TEALIUM_MAIN, config, true);
        Tealium.createInstance(TEALIUM_MAIN, config);
    }

    private static DispatchValidator createLifeCycleListener() {
        return new DispatchValidator() {
            @Override
            protected boolean shouldDrop(Dispatch dispatch) {

                final String lifeCycleType = dispatch.getString(DataSources.Key.LIFECYCLE_TYPE);

                if (lifeCycleType != null) {
                    Log.i(TAG, "LifeCycle: " + lifeCycleType);
                }

                return false;
            }
        };
    }

    public static void trackView(String viewName, Map<String, ?> data) {
        final Tealium instance = Tealium.getInstance(TEALIUM_MAIN);

        // Instance can be remotely destroyed through publish settings
        if (instance != null) {
            instance.trackView(viewName, data);
        }
    }

    public static void trackEvent(String eventName, Map<String, ?> data) {
        final Tealium instance = Tealium.getInstance(TEALIUM_MAIN);

        // Instance can be remotely destroyed through publish settings
        if (instance != null) {
            instance.trackEvent(eventName, data);
        }
    }
}
