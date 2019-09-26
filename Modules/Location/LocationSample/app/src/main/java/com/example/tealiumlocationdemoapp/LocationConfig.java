package com.example.tealiumlocationdemoapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tealium.library.Tealium;
import com.tealium.location.TealiumLocation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LocationConfig extends Activity {

    private Tealium.Config mTealiumConfig;
    private boolean mLocationShowcaseStarted = false;
    private TealiumLocation mInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        findViewById(R.id.location_config_button_update)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mLocationShowcaseStarted) {
                            drawActiveGeofences();
                            drawUsedConfig();
                        } else {
                            showToast("Please press Start first!");
                        }
                    }
                });

        findViewById(R.id.location_config_button_start)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!mLocationShowcaseStarted) {
                            startLocationShowcase();
                        } else {
                            showToast("Showcase Already Started!");
                        }
                    }
                });

        findViewById(R.id.location_config_button_stop)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!mLocationShowcaseStarted) {
                            showToast("Please press Start first!");
                        }
                    }
                });
    }

    private void startLocationShowcase() {
        mLocationShowcaseStarted = true;

        mTealiumConfig = TealiumHelper.getConfig();

        Set<String> tealiumInstances = new HashSet<>();
        tealiumInstances.add(TealiumHelper.TEALIUM_MAIN);

        if (mTealiumConfig != null) {
            mInstance = TealiumLocation.setupInstanceWithConfig(this, tealiumInstances, mTealiumConfig);
            mInstance.startLocationUpdates(true, 5000);
            drawActiveGeofences();
            drawUsedConfig();
        } else {
            showToast("Error with config");
        }
    }

    private void drawActiveGeofences() {
        ArrayList<String> activeGeofences = mInstance.getAllGeofenceNames();
        String geofenceNames = "";

        for (String name : activeGeofences) {
            geofenceNames = geofenceNames + name + "\n";
        }

        TextView geofenceNameData = findViewById(R.id.location_config_geofencesdata);
        geofenceNameData.setText(geofenceNames);
    }

    private void drawUsedConfig() {
        TextView urlData = findViewById(R.id.location_config_configdata);
        urlData.setText(mTealiumConfig.getAccountName());
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocationShowcaseStarted) {
            TealiumLocation.destroyInstance();
        }
    }
}
