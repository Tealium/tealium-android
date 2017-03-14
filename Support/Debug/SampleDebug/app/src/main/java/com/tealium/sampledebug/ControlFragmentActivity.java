package com.tealium.sampledebug;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.tealium.sampledebug.fragment.ControlFragment;

/**
 * Created by karentamayo on 1/12/17.
 */

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
}
