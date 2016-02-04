package com.tealium.example;

import com.tealium.example.adapter.StackViewAdapter;
import com.tealium.example.helper.TealiumHelper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.StackView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ControlStackViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stackview);
		
		((StackView) findViewById(R.id.example_stackview))
			.setAdapter(new StackViewAdapter());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		TealiumHelper.trackView("StackView", null);
	}
}
