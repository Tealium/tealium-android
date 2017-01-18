package com.tealium.sampledebug;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.tealium.sampledebug.adapter.GridViewAdapter;
import com.tealium.sampledebug.helper.TealiumHelper;

/**
 * Created by karentamayo on 1/12/17.
 */

public class ControlGridViewActivity extends Activity{
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
