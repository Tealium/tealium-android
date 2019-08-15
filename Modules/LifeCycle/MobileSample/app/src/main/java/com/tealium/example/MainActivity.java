package com.tealium.example;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.tealium.example.helper.TealiumHelper;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		TealiumHelper.trackView("RootView", null);
	}
}
