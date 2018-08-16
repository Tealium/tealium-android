package com.tealium.consentmanagerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

public class PreferencesSliderViewActivity extends Activity {

    private final static int NOT_CONSENTED = 0;
    private final static int PERFORMANCE = 30;
    private final static int MARKETING = 65;
    private final static int PERSONALIZED = 100;
    private final static int CUSTOM = 50;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        final TextView status = findViewById(R.id.current_consent_status_textview);
        status.setText(String.format(Locale.ROOT, "Consent Status: %s", TealiumHelper.getConsentManager().getUserConsentStatus()));

        final TextView categories = findViewById(R.id.current_consent_categories_textview);
        categories.setText(String.format(Locale.ROOT, "Current Categories: %s", TealiumHelper.getConsentCategoryString()));

        final ProgressBar bar = findViewById(R.id.consent_seekBar);
        if (TealiumHelper.getConsentModel().isMasterStatus()) {
            if (isPerformance()) {
                bar.setProgress(PERFORMANCE);
            } else if (isMarketing()) {
                bar.setProgress(MARKETING);
            } else if (isPersonalized()) {
                bar.setProgress(PERSONALIZED);
            } else {
                bar.setProgress(CUSTOM);
            }
        } else {
            bar.setProgress(NOT_CONSENTED);
        }

        seekBarListener();
    }

    private void seekBarListener() {
        ((SeekBar) findViewById(R.id.consent_seekBar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (0 <= progress && progress < 15) {
                    TealiumHelper.getConsentModel().setMasterStatus(false);
                    TealiumHelper.updateConsentManager();
                    updateTextViews(NOT_CONSENTED);
                } else if (15 <= progress && progress < 42) {
                    enablePerformanceCategories();
                    TealiumHelper.updateConsentManager();
                    updateTextViews(PERFORMANCE);
                } else if (42 <= progress && progress < 72) {
                    enableMarketingCategories();
                    TealiumHelper.updateConsentManager();
                    updateTextViews(MARKETING);
                } else if (72 <= progress && progress <= 100) {
                    TealiumHelper.getConsentModel().setMasterStatus(true);
                    TealiumHelper.updateConsentManager();
                    updateTextViews(PERSONALIZED);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void enablePerformanceCategories() {
        TealiumHelper.getConsentModel().setAnalytics(true);
        TealiumHelper.getConsentModel().setMonitoring(true);
        TealiumHelper.getConsentModel().setBigData(true);
        TealiumHelper.getConsentModel().setMobile(true);
        TealiumHelper.getConsentModel().setCrm(true);

        TealiumHelper.getConsentModel().setAffiliates(false);
        TealiumHelper.getConsentModel().setEmail(false);
        TealiumHelper.getConsentModel().setSearch(false);
        TealiumHelper.getConsentModel().setEngagement(false);
        TealiumHelper.getConsentModel().setCdp(false);
        TealiumHelper.getConsentModel().setDisplayAds(false);
        TealiumHelper.getConsentModel().setPersonalization(false);
        TealiumHelper.getConsentModel().setSocial(false);
        TealiumHelper.getConsentModel().setCookiematch(false);
        TealiumHelper.getConsentModel().setMisc(false);
    }

    private void enableMarketingCategories() {
        TealiumHelper.getConsentModel().setAnalytics(true);
        TealiumHelper.getConsentModel().setAffiliates(true);
        TealiumHelper.getConsentModel().setMonitoring(true);
        TealiumHelper.getConsentModel().setBigData(true);
        TealiumHelper.getConsentModel().setMobile(true);
        TealiumHelper.getConsentModel().setCrm(true);
        TealiumHelper.getConsentModel().setEmail(true);
        TealiumHelper.getConsentModel().setSearch(true);
        TealiumHelper.getConsentModel().setEngagement(true);
        TealiumHelper.getConsentModel().setCdp(true);

        TealiumHelper.getConsentModel().setMisc(false);
        TealiumHelper.getConsentModel().setCookiematch(false);
        TealiumHelper.getConsentModel().setSocial(false);
        TealiumHelper.getConsentModel().setPersonalization(false);
        TealiumHelper.getConsentModel().setDisplayAds(false);
    }

    private boolean isPerformance() {
        return (TealiumHelper.getConsentModel().isAnalytics() &&
                TealiumHelper.getConsentModel().isMonitoring() &&
                TealiumHelper.getConsentModel().isBigData() &&
                TealiumHelper.getConsentModel().isMobile() &&
                TealiumHelper.getConsentModel().isCrm() &&
                !TealiumHelper.getConsentModel().isAffiliates() &&
                !TealiumHelper.getConsentModel().isEmail() &&
                !TealiumHelper.getConsentModel().isSearch() &&
                !TealiumHelper.getConsentModel().isEngagement() &&
                !TealiumHelper.getConsentModel().isCdp() &&
                !TealiumHelper.getConsentModel().isDisplayAds() &&
                !TealiumHelper.getConsentModel().isPersonalization() &&
                !TealiumHelper.getConsentModel().isSocial() &&
                !TealiumHelper.getConsentModel().isCookiematch() &&
                !TealiumHelper.getConsentModel().isMisc());
    }

    private boolean isMarketing() {
        return (TealiumHelper.getConsentModel().isAnalytics() &&
                TealiumHelper.getConsentModel().isAffiliates() &&
                TealiumHelper.getConsentModel().isMonitoring() &&
                TealiumHelper.getConsentModel().isBigData() &&
                TealiumHelper.getConsentModel().isMobile() &&
                TealiumHelper.getConsentModel().isCrm() &&
                TealiumHelper.getConsentModel().isEmail() &&
                TealiumHelper.getConsentModel().isSearch() &&
                TealiumHelper.getConsentModel().isEngagement() &&
                TealiumHelper.getConsentModel().isCdp() &&
                !TealiumHelper.getConsentModel().isDisplayAds() &&
                !TealiumHelper.getConsentModel().isPersonalization() &&
                !TealiumHelper.getConsentModel().isSocial() &&
                !TealiumHelper.getConsentModel().isCookiematch() &&
                !TealiumHelper.getConsentModel().isMisc());
    }

    private boolean isPersonalized() {
        return (TealiumHelper.getConsentModel().isAnalytics() &&
                TealiumHelper.getConsentModel().isAffiliates() &&
                TealiumHelper.getConsentModel().isMonitoring() &&
                TealiumHelper.getConsentModel().isBigData() &&
                TealiumHelper.getConsentModel().isMobile() &&
                TealiumHelper.getConsentModel().isCrm() &&
                TealiumHelper.getConsentModel().isEmail() &&
                TealiumHelper.getConsentModel().isSearch() &&
                TealiumHelper.getConsentModel().isEngagement() &&
                TealiumHelper.getConsentModel().isCdp() &&
                TealiumHelper.getConsentModel().isDisplayAds() &&
                TealiumHelper.getConsentModel().isPersonalization() &&
                TealiumHelper.getConsentModel().isSocial() &&
                TealiumHelper.getConsentModel().isCookiematch() &&
                TealiumHelper.getConsentModel().isMisc());
    }

    private void updateTextViews(int progress) {
        TextView status = findViewById(R.id.current_consent_status_textview);
        status.setText(String.format(Locale.ROOT, "Consent Status: %s", TealiumHelper.getConsentManager().getUserConsentStatus()));

        TextView categories = findViewById(R.id.current_consent_categories_textview);
        categories.setText(String.format(Locale.ROOT, "Consented Categories: %s", TealiumHelper.getConsentCategoryString()));

        final ProgressBar bar = findViewById(R.id.consent_seekBar);
        bar.setProgress(progress);

        TextView level = findViewById(R.id.consent_level_textview);
        if (progress == NOT_CONSENTED) {
            level.setText(String.format(Locale.ROOT, "Consent Level : %s", "Off"));
        } else if (progress == PERFORMANCE) {
            level.setText(String.format(Locale.ROOT, "Consent Level : %s", "Performance"));
        } else if (progress == MARKETING) {
            level.setText(String.format(Locale.ROOT, "Consent Level : %s", "Marketing"));
        } else if (progress == PERSONALIZED) {
            level.setText(String.format(Locale.ROOT, "Consent Level : %s", "Personalized Advertising"));
        }
    }
}
