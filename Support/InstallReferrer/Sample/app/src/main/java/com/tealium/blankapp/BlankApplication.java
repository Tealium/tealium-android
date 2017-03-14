package com.tealium.blankapp;

import com.tealium.blankapp.helper.TealiumHelper;

import android.app.Application;

public final class BlankApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		// Must occur after onCreate();
		TealiumHelper.initialize(this);
	}
}
