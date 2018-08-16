package com.tealium.consentmanagerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Locale;

public class SimplePreferencesViewActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        final TextView status = findViewById(R.id.current_consent_status_textview);
        status.setText("Consent Status: " + TealiumHelper.getConsentManager().getUserConsentStatus());

        final TextView categories = findViewById(R.id.current_consent_categories_textview);
        categories.setText("Current Categories: " + TealiumHelper.getConsentCategoryString());

        final Switch consented = findViewById(R.id.consent_status_switch);
        consented.setChecked(TealiumHelper.getConsentModel().isMasterStatus());

        switchListener();
    }

    private void switchListener() {
        Switch s = findViewById(R.id.consent_status_switch);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TealiumHelper.getConsentModel().setMasterStatus(isChecked);
                TealiumHelper.updateConsentManager();
                updateTextViews();
            }
        });
    }

    private void updateTextViews() {
        TextView status = findViewById(R.id.current_consent_status_textview);
        status.setText(String.format(Locale.ROOT, "Consent Status: %s", TealiumHelper.getConsentManager().getUserConsentStatus()));

        TextView categories = findViewById(R.id.current_consent_categories_textview);
        categories.setText(String.format(Locale.ROOT, "Consented Categories: %s", TealiumHelper.getConsentCategoryString()));
    }
}
