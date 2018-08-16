package com.tealium.consentmanagerdemo;

import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TealiumHelper.initialize(this);
    }
}
