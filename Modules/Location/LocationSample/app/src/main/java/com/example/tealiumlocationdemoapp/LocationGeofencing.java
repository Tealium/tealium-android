package com.example.tealiumlocationdemoapp;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tealium.location.TealiumLocation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LocationGeofencing extends Activity {

    private boolean mLocationShowcaseStarted = false;
    private Location mLastLocation;
    private TealiumLocation mInstance;
    private static final String GEOFENCES_URL = "https://tags.tiqcdn.com/dle/tealiummobile/location/geofences.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geofencing);

        findViewById(R.id.location_geofencing_button_update)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mLocationShowcaseStarted) {
                            mLastLocation = mInstance.getLastLocation();
                            drawLastLocation();
                            drawActiveGeofences();
                        } else {
                            showToast("Please press Start first!");
                        }
                    }
                });

        findViewById(R.id.location_geofencing_button_start)
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

        findViewById(R.id.location_geofencing_button_stop)
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

        mInstance = TealiumLocation.setupInstanceWithUrl(this, tealiumInstances, GEOFENCES_URL);
        mInstance.startLocationUpdates(true, 5000);
        drawLastLocation();
        drawActiveGeofences();
    }

    private void drawLastLocation() {
        TextView locationData = findViewById(R.id.location_geofencing_locationdata);

        if (mLastLocation != null) {
            locationData.setText("Latitude: " + mLastLocation.getLatitude() + "\nLongitude: " + mLastLocation.getLongitude());
        } else {
            locationData.setText("Please Update UI");
            Toast.makeText(getApplicationContext(), "Location Updates Every 5 Seconds, Please wait a bit!", Toast.LENGTH_SHORT).show();
        }
    }

    private void drawActiveGeofences() {
        ArrayList<String> activeGeofences = mInstance.getActiveGeofenceNames();
        String geofenceNames = "";

        for (String geofence : activeGeofences) {
            geofenceNames = geofenceNames + geofence + "\n";
        }

        TextView geofenceNameData = findViewById(R.id.location_geofencing_geofencesdata);
        geofenceNameData.setText(geofenceNames);
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
