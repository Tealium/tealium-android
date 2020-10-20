package com.example.tealiumlocationdemoapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.WebView;

import com.tealium.internal.data.Dispatch;
import com.tealium.internal.listeners.WebViewLoadedListener;
import com.tealium.library.DispatchValidator;
import com.tealium.library.Tealium;
import com.tealium.remotecommands.RemoteCommand;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * This class abstracts interaction with the Tealium library and simplifies comprehension
 */
public final class TealiumHelper {

    static {
        Log.i("TagBridge", " --- START --- ");
    }

    private final static String KEY_TEALIUM_INIT_COUNT = "tealium_init_count";
    private final static String KEY_TEALIUM_INITIALIZED = "tealium_initialized";
    private final static String TAG = "TealiumHelper";
    private static Tealium.Config sConfig;

    // Identifier for the main Tealium instance
    public static final String TEALIUM_MAIN = "main";

    // Not instantiatable.
    private TealiumHelper() {
    }

    public static void initialize(Application application) {

        Log.i(TAG, "initialize(" + application.getClass().getSimpleName() + ")");

        WebView.setWebContentsDebuggingEnabled(true);

        final Tealium.Config config = Tealium.Config.create(application, "tealiummobile", "location", "dev");

        // (OPTIONAL) Update console logs verbosity
        config.setForceOverrideLogLevel("dev");

        // (OPTIONAL) Get the WebView with UTag loaded
        config.getEventListeners().add(createWebViewLoadedListener());

        // (OPTIONAL) Control how the library treats views/links
        config.getDispatchValidators().add(createDispatchValidator());

        sConfig = config;

        final Tealium instance = Tealium.createInstance(TEALIUM_MAIN, sConfig);

        // (OPTIONAL) Enhanced integrations
        instance.addRemoteCommand(createLoggerRemoteCommand());

        // (OPTIONAL) Use tealium.getDataSources().getPersistentDataSources() to set/modify lifetime values
        SharedPreferences sp = instance.getDataSources().getPersistentDataSources();
        sp.edit().putInt(KEY_TEALIUM_INIT_COUNT, sp.getInt(KEY_TEALIUM_INIT_COUNT, 0) + 1).commit();

        // (OPTIONAL) Use tealium.getDataSources().getVolatileDataSources() to set/modify runtime only values
        instance.getDataSources().getVolatileDataSources()
                .put(KEY_TEALIUM_INITIALIZED, System.currentTimeMillis());

        // (OPTIONAL) tracking initialization
        final Map<String, Object> data = new HashMap<>(2);
        data.put("logged_in", false);
        data.put("visitor_status", new String[]{"new_user", "unregistered"});
        TealiumHelper.trackEvent("initialization", data);
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

    private static WebViewLoadedListener createWebViewLoadedListener() {
        return new WebViewLoadedListener() {
            @Override
            public void onWebViewLoad(WebView webView, boolean success) {
                Log.d(TAG, "WebView " + webView +
                        (success ? " loaded successfully" : "failed to load"));
            }

            @Override
            public String toString() {
                return "LoggingWebViewLoadListener";
            }
        };
    }

    private static DispatchValidator createDispatchValidator() {
        return new DispatchValidator() {
            @Override
            protected boolean shouldDrop(Dispatch dispatch) {

                // Drop any desired dispatches here by returning true. (Never queued nor sent)
                return super.shouldDrop(dispatch);
            }

            @Override
            protected boolean shouldQueue(Dispatch dispatch, boolean shouldQueue) {

                Log.d(TAG, String.format(
                        Locale.ROOT,
                        "%s dispatch: %s",
                        (shouldQueue ? "Queueing" : "Sending"),
                        dispatch));

                return super.shouldQueue(dispatch, shouldQueue);
            }

            @Override
            public String toString() {
                return "CustomDispatchValidator";
            }
        };
    }

    private static RemoteCommand createLoggerRemoteCommand() {
        return new RemoteCommand("logger", "Logs dispatches") {
            @Override
            protected void onInvoke(Response response) throws Exception {
                final String message = response.getRequestPayload()
                        .optString("message", "no_message");
                Log.i(TAG, "RemoteCommand Message: " + message);
            }

            @Override
            public String toString() {
                return "LoggerRemoteCommand";
            }
        };
    }

    public static Tealium.Config getConfig() {
        return sConfig;
    }

    public static void updatePushToken(String token) {

        if (Tealium.getInstance(TealiumHelper.TEALIUM_MAIN) == null || token == null) {
            return;
        }

        Tealium.getInstance(TealiumHelper.TEALIUM_MAIN).getDataSources().getPersistentDataSources().edit().putString("push_token", token).apply();
    }

}