package com.tealium.example;

import com.tealium.example.adapter.GridViewAdapter;
import com.tealium.example.helper.TealiumHelper;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class ControlGridViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gridview);
		
		((GridView)findViewById(R.id.example_gridview))
			.setAdapter(new GridViewAdapter(this));
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		TealiumHelper.trackView("ControlGrid", null);
	}
}
