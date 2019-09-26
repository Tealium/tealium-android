package com.example.tealiumlocationdemoapp;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tealium.location.TealiumLocation;

import java.util.HashSet;
import java.util.Set;

public class LocationTracking extends Activity {

    private boolean mLocationShowcaseStarted = false;
    private Location mLastLocation;
    private TealiumLocation mInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        findViewById(R.id.location_tracking_button_update)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mLocationShowcaseStarted) {
                            mLastLocation = mInstance.getLastLocation();
                            drawLastLocation();
                        } else {
                            showToast("Please press Start first!");
                        }
                    }
                });

        findViewById(R.id.location_tracking_button_start)
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

        findViewById(R.id.location_tracking_button_stop)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mLocationShowcaseStarted) {
                            mInstance.stopLocationUpdates();
                            showToast("Showcase Stopped.");
                        } else {
                            showToast("Please press Start first!");
                        }
                    }
                });
    }

    private void startLocationShowcase() {
        mLocationShowcaseStarted = true;

        Set<String> tealiumInstances = new HashSet<>();
        tealiumInstances.add(TealiumHelper.TEALIUM_MAIN);

        mInstance = TealiumLocation.setupInstance(this, tealiumInstances);
        mInstance.startLocationUpdates(true, 5000);
        drawLastLocation();
    }

    private void drawLastLocation() {
        TextView locationData = findViewById(R.id.location_tracking_locationdata);

        if (mLastLocation != null) {
            locationData.setText("Latitude: " + mLastLocation.getLatitude() + "\nLongitude: " + mLastLocation.getLongitude());
        } else {
            locationData.setText("Please Update UI");
            showToast("Location Updates Every 5 Seconds, Please wait a bit!");
        }
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
