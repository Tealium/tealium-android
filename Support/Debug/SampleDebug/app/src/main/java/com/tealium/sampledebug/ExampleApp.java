package com.tealium.sampledebug;

import android.app.Application;

import com.tealium.sampledebug.helper.TealiumHelper;

/**
 * Created by karentamayo on 1/12/17.
 */

public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TealiumHelper.initialize(this);
    }
}
