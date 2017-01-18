package com.tealium.sampledebug;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.StackView;

import com.tealium.sampledebug.adapter.StackViewAdapter;
import com.tealium.sampledebug.helper.TealiumHelper;

/**
 * Created by karentamayo on 1/12/17.
 */

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
