package com.tealium.blankapp.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;

import com.tealium.blankapp.BuildConfig;
import com.tealium.internal.listeners.WebViewCreatedListener;
import com.tealium.library.Tealium;
import com.tealium.adidentifier.TealiumAdIdentifier;

import java.util.Map;

public final class TealiumHelper {

    // Identifier for the main instance
    public static final String TEALIUM_MAIN = "main";

    @SuppressLint("NewApi")
    public static void initialize(Application application) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && BuildConfig.DEBUG) {
            // To connect to WebView if Tag Management is enabled
            WebView.setWebContentsDebuggingEnabled(true);
        }

        Tealium.Config config = Tealium.Config.create(application, "tealiummobile", "demo", "dev");
        Tealium.createInstance(TEALIUM_MAIN, config);
        TealiumAdIdentifier.setIdPersistent(TEALIUM_MAIN, application.getApplicationContext());
        config.getEventListeners().add(createCookieEnablerListener());
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

                Log.d("Teal", "WebView " + webView + " created and cookies enabled.");
            }
        };
    }

    public static void onResume(Activity activity, Map<String, String> data) {
        // Considering "onResume" to be views

        final Tealium instance = Tealium.getInstance(TEALIUM_MAIN);

        // instance can be remotely destroyed by publish settings
        if(instance != null) {
            instance.trackView(activity.getClass().getSimpleName(), data);
        }
    }

    public static void trackEvent(String eventName, Map<String, String> data) {
        final Tealium instance = Tealium.getInstance(TEALIUM_MAIN);

        // instance can be remotely destroyed by publish settings
        if(instance != null) {
            instance .trackEvent(eventName, data);
        }
    }
}
