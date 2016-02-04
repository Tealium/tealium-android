package com.tealium.example;

import com.tealium.example.helper.TealiumHelper;

import android.app.Application;

// TODO: move entire project into android-library repo
public class ExampleApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		TealiumHelper.initialize(this);
	}

}
