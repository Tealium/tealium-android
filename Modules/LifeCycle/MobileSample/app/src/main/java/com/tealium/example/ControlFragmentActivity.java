package com.tealium.example;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.tealium.example.fragment.ControlFragment;
import com.tealium.example.helper.TealiumHelper;

public class ControlFragmentActivity extends FragmentActivity {

    public static final int RESULT_NONE = 0;
    public static final int RESULT_SELECT = 1;

    public static final String KEY_CONTROL_LAYOUT_ID = "control_layout_id";
    private int selectedLayoutId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_fragment);

        Bundle extras;

        if (((extras = getIntent().getExtras()) == null) ||
                ((selectedLayoutId = extras.getInt(KEY_CONTROL_LAYOUT_ID)) == 0)) {
            // Should never happen.
            Toast.makeText(this,
                    getResources().getString(R.string.error_hasnotbeenimplemented),
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        ((ControlFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_custom))
                .setControl(selectedLayoutId);

        setResult(RESULT_NONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TealiumHelper.trackView("ControlDetail", null);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // If unknown, etc, not conerned with it.
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Intent data = new Intent();
            data.putExtra(KEY_CONTROL_LAYOUT_ID, selectedLayoutId);
            setResult(RESULT_SELECT, data);
            finish();
        }
    }
}
