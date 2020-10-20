package com.tealium.consentmanagerdemo;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.webkit.WebView;

import com.tealium.internal.data.Dispatch;
import com.tealium.library.ConsentManager;
import com.tealium.library.DispatchValidator;
import com.tealium.library.Tealium;
import com.tealium.internal.listeners.WebViewLoadedListener;
import com.tealium.remotecommands.RemoteCommand;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class TealiumHelper {

    static {
        Log.i("TagBridge", " --- START --- ");
    }

    private final static String KEY_TEALIUM_INIT_COUNT = "tealium_init_count";
    private final static String KEY_TEALIUM_INITIALIZED = "tealium_initialized";
    private final static String TAG = "TealiumHelper";

    // Identifier for the main Tealium instance
    public static final String TEALIUM_MAIN = "main";
    public static ConsentModel mConsentModel;

    // Not instantiatable.
    private TealiumHelper() {
    }

    public static void initialize(Application application) {

        Log.i(TAG, "initialize(" + application.getClass().getSimpleName() + ")");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        mConsentModel = new ConsentModel(application.getApplicationContext());

        final Tealium.Config config = Tealium.Config.create(application, "tealiummobile", "android", "dev");

        // (OPTIONAL) Enable Consent Manager
        config.enableConsentManager(TEALIUM_MAIN);
        config.setForceOverrideLogLevel("dev");

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

    public static ConsentManager getConsentManager() {
        return Tealium.getInstance(TEALIUM_MAIN).getConsentManager();
    }

    public static ConsentModel getConsentModel() {
        return mConsentModel;
    }

    public static void updateConsentManager() {
        Set<String> categorySet = new HashSet<>();
        if (TealiumHelper.getConsentModel().isAffiliates()) {
            categorySet.add(ConsentManager.ConsentCategory.AFFILIATES);
        }
        if (TealiumHelper.getConsentModel().isAnalytics()) {
            categorySet.add(ConsentManager.ConsentCategory.ANALYTICS);
        }
        if (TealiumHelper.getConsentModel().isBigData()) {
            categorySet.add(ConsentManager.ConsentCategory.BIG_DATA);
        }
        if (TealiumHelper.getConsentModel().isCdp()) {
            categorySet.add(ConsentManager.ConsentCategory.CDP);
        }
        if (TealiumHelper.getConsentModel().isCookiematch()) {
            categorySet.add(ConsentManager.ConsentCategory.COOKIEMATCH);
        }
        if (TealiumHelper.getConsentModel().isCrm()) {
            categorySet.add(ConsentManager.ConsentCategory.CRM);
        }
        if (TealiumHelper.getConsentModel().isDisplayAds()) {
            categorySet.add(ConsentManager.ConsentCategory.DISPLAY_ADS);
        }
        if (TealiumHelper.getConsentModel().isEmail()) {
            categorySet.add(ConsentManager.ConsentCategory.EMAIL);
        }
        if (TealiumHelper.getConsentModel().isEngagement()) {
            categorySet.add(ConsentManager.ConsentCategory.ENGAGEMENT);
        }
        if (TealiumHelper.getConsentModel().isMobile()) {
            categorySet.add(ConsentManager.ConsentCategory.MOBILE);
        }
        if (TealiumHelper.getConsentModel().isMonitoring()) {
            categorySet.add(ConsentManager.ConsentCategory.MONITORING);
        }
        if (TealiumHelper.getConsentModel().isPersonalization()) {
            categorySet.add(ConsentManager.ConsentCategory.PERSONALIZATION);
        }
        if (TealiumHelper.getConsentModel().isSearch()) {
            categorySet.add(ConsentManager.ConsentCategory.SEARCH);
        }
        if (TealiumHelper.getConsentModel().isSocial()) {
            categorySet.add(ConsentManager.ConsentCategory.SOCIAL);
        }
        if (TealiumHelper.getConsentModel().isMisc()) {
            categorySet.add(ConsentManager.ConsentCategory.MISC);
        }
        if (categorySet.size() == 0) {
            TealiumHelper.getConsentManager().setUserConsentStatus(ConsentManager.ConsentStatus.NOT_CONSENTED);
        } else {
            TealiumHelper.getConsentManager().setUserConsentCategories(setToArray(categorySet));
        }
    }

    public static String getConsentCategoryString() {
        final String[] consentCategories = TealiumHelper.getConsentManager().getUserConsentCategories();
        String categoryString = "";
        for (int i = 0; i < consentCategories.length; i++) {
            categoryString += consentCategories[i];
            if (i < consentCategories.length-1) {
                categoryString += ", ";
            }
        }
        return categoryString;
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

    private static String[] setToArray(Set<String> set) {
        if (set == null) {
            return new String[]{};
        }

        return set.toArray(new String[0]);
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
