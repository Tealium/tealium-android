package com.tealium.example;

import android.app.Application;

import com.tealium.example.helper.TealiumHelper;

public class ExampleApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		TealiumHelper.initialize(this);
	}

}
