package com.tealium.consentmanagerdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.track_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //track current consent status/categories
                        TealiumHelper.trackEvent("demo_consent_event", getSampleData());
                    }
                });

        findViewById(R.id.list_view_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(v.getContext(), PreferencesListViewActivity.class));
                    }
                });

        findViewById(R.id.slider_view_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(v.getContext(), PreferencesSliderViewActivity.class));
                    }
                });

        findViewById(R.id.simple_view_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(v.getContext(), SimplePreferencesViewActivity.class));
                    }
                });

        findViewById(R.id.reset_preferences_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TealiumHelper.getConsentManager().resetUserConsentPreferences();
                        TealiumHelper.getConsentModel().setMasterStatus(false);
                        updateTextViews();
                    }
                });

        updateTextViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTextViews();
    }

    private void updateTextViews() {
        TextView status = findViewById(R.id.current_consent_status_textview);
        status.setText(String.format(Locale.ROOT, "Consent Status: %s", TealiumHelper.getConsentManager().getUserConsentStatus()));

        TextView categories = findViewById(R.id.current_consent_categories_textview);

        categories.setText(String.format(Locale.ROOT, "Consented Categories: %s", TealiumHelper.getConsentCategoryString()));
    }

    private Map<String, Object> getSampleData(){
        Map<String, Object> data = new HashMap<>(1);
        data.put("custom_event", "track_button_triggered");
        data.put("sample_key", "sample_value");

        return data;
    }
}