package com.tealium.androidtvsample;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;

import com.tealium.library.Tealium;
import com.tealium.lifecycle.LifeCycle;

public class App extends Application {

    Tealium tealium = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Tealium.Config config = Tealium.Config.create(this, "tealiummobile", "android-tv", "dev");
        config.setDatasourceId("abc123");
        config.setForceOverrideLogLevel("dev");

        LifeCycle.setupInstance("1", config, true);
        tealium = Tealium.createInstance("1", config);
    }
}
