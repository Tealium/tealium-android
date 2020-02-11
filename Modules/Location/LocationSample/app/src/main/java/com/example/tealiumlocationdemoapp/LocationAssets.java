package com.example.tealiumlocationdemoapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tealium.location.TealiumLocation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LocationAssets extends Activity {

    private boolean mLocationShowcaseStarted = false;
    private TealiumLocation mInstance;
    private final String mGeofenceJsonFile = "geofences.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets);

        findViewById(R.id.location_assets_button_update)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mLocationShowcaseStarted) {
                            drawActiveGeofences();
                            drawUsedAssets();
                        } else {
                            showToast("Please press Start first!");
                        }
                    }
                });

        findViewById(R.id.location_assets_button_start)
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

        findViewById(R.id.location_assets_button_stop).setOnClickListener(new View.OnClickListener() {
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

        Set<String> tealiumInstances = new HashSet<>();
        tealiumInstances.add(TealiumHelper.TEALIUM_MAIN);

        mInstance = TealiumLocation.setupInstanceWithFile(this, tealiumInstances, mGeofenceJsonFile);
        mInstance.startLocationUpdates(true, 5000);
        drawUsedAssets();
        drawActiveGeofences();
    }

    private void drawActiveGeofences() {
        List<String> activeGeofences = mInstance.getAllGeofenceNames();
        String geofenceNames = "";

        for (String name : activeGeofences) {
            geofenceNames = geofenceNames + name + "\n";
        }

        TextView geofenceNameData = findViewById(R.id.location_assets_geofencesdata);
        geofenceNameData.setText(geofenceNames);
    }

    private void drawUsedAssets() {
        TextView urlData = findViewById(R.id.location_assets_assetsdata);
        urlData.setText(mGeofenceJsonFile);
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
