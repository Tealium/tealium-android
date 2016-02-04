package com.tealium.sample.tv.helper;

import android.app.Application;

import com.tealium.library.Tealium;

import java.util.Map;

public final class TealiumHelper {

    public static final String TEALIUM_MAIN = "main";

    private TealiumHelper() {
    }

    public static void initialize(Application application) {
        Tealium.createInstance(
                TEALIUM_MAIN,
                Tealium.Config.create(application, "tealiummobile", "demo", "dev"));
    }

    public static void trackView(String title, Map<String, ?> data) {
        final Tealium instance = Tealium.getInstance(TEALIUM_MAIN);

        // Instance can be remotely destroyed by publish settings.
        if(instance != null) {
            instance.trackView(title, data);
        }
    }

    public static void trackEvent(String name, Map<String, ?> data) {
        final Tealium instance = Tealium.getInstance(TEALIUM_MAIN);

        // Instance can be remotely destroyed by publish settings.
        if (instance != null) {
            instance.trackEvent(name, data);
        }
    }
}
