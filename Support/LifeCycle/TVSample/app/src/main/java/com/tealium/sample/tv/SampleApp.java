package com.tealium.sample.tv;

import android.app.Application;

import com.tealium.sample.tv.helper.TealiumHelper;

public final class SampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        TealiumHelper.initialize(this);
    }
}
