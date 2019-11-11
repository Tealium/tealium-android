package com.tealium.androidtvsample;

import android.app.Application;
import androidx.lifecycle.Lifecycle;

import com.tealium.library.Tealium;
import com.tealium.lifecycle.LifeCycle;

public class App extends Application {

    Tealium tealium = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Tealium.Config config = Tealium.Config.create(this, "services-james", "lib-mobile", "dev");
        config.setDatasourceId("abc123");
        config.setForceOverrideLogLevel("dev");

        LifeCycle.setupInstance("my_instance", config, true);
        tealium = Tealium.createInstance("my_instance", config);
    }
}
