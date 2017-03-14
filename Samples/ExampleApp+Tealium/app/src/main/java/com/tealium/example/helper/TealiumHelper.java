package com.tealium.example.helper;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;

import com.tealium.example.BuildConfig;
import com.tealium.internal.data.Dispatch;
import com.tealium.internal.listeners.WebViewCreatedListener;
import com.tealium.internal.listeners.WebViewLoadedListener;
import com.tealium.internal.tagbridge.RemoteCommand;
import com.tealium.library.DispatchValidator;
import com.tealium.library.Tealium;

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

    // Identifier for the main Tealium instance
    public static final String TEALIUM_MAIN = "main";

    // Not instantiatable.
    private TealiumHelper() {
    }

    public static void initialize(Application application) {

        Log.i(TAG, "initialize(" + application.getClass().getSimpleName() + ")");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        final Tealium.Config config = Tealium.Config.create(application, "tealiummobile", "demo", "dev");

        // (OPTIONAL) Get the WebView with UTag loaded
        config.getEventListeners().add(createWebViewLoadedListener());

        // (OPTIONAL) Control how the library treats views/links
        config.getDispatchValidators().add(createDispatchValidator());

        final Tealium instance = Tealium.createInstance(TEALIUM_MAIN, config);

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

    private static WebViewCreatedListener createCookieEnablerListener() {
        return new WebViewCreatedListener() {
            @Override
            public void onWebViewCreated(WebView webView) {
                final CookieManager mgr = CookieManager.getInstance();

                // Accept all cookies
                mgr.setAcceptCookie(true);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mgr.setAcceptThirdPartyCookies(webView, true);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                    CookieManager.setAcceptFileSchemeCookies(true);
                }

                Log.d(TAG, "WebView " + webView + " created and cookies enabled.");
            }

            @Override
            public String toString() {
                return "EnableCookieWebViewCreatedListener";
            }
        };
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
}
