package com.tealium.consentmanagerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Locale;

public class PreferencesListViewActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        updateTextViews();
        CompoundButton.OnCheckedChangeListener listener = createSwitchListener();

        Switch s = findViewById(R.id.consent_status_switch);
        s.setOnCheckedChangeListener(listener);

        Switch s1 = findViewById(R.id.analytics_switch);
        s1.setOnCheckedChangeListener(listener);

        Switch s2 = findViewById(R.id.affiliates_switch);
        s2.setOnCheckedChangeListener(listener);

        Switch s3 = findViewById(R.id.display_ads_switch);
        s3.setOnCheckedChangeListener(listener);

        Switch s4 = findViewById(R.id.email_switch);
        s4.setOnCheckedChangeListener(listener);

        Switch s5 = findViewById(R.id.personalization_switch);
        s5.setOnCheckedChangeListener(listener);

        Switch s6 = findViewById(R.id.search_switch);
        s6.setOnCheckedChangeListener(listener);

        Switch s7 = findViewById(R.id.social_switch);
        s7.setOnCheckedChangeListener(listener);

        Switch s8 = findViewById(R.id.big_data_switch);
        s8.setOnCheckedChangeListener(listener);

        Switch s9 = findViewById(R.id.mobile_switch);
        s9.setOnCheckedChangeListener(listener);

        Switch s10 = findViewById(R.id.monitoring_switch);
        s10.setOnCheckedChangeListener(listener);

        Switch s11 = findViewById(R.id.engagement_switch);
        s11.setOnCheckedChangeListener(listener);

        Switch s12 = findViewById(R.id.cdp_switch);
        s12.setOnCheckedChangeListener(listener);

        Switch s13 = findViewById(R.id.crm_switch);
        s13.setOnCheckedChangeListener(listener);

        Switch s14 = findViewById(R.id.cookiematch_switch);
        s14.setOnCheckedChangeListener(listener);

        Switch s15 = findViewById(R.id.misc_switch);
        s15.setOnCheckedChangeListener(listener);


    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTextViews();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void updateTextViews() {
        TextView status = findViewById(R.id.current_consent_status_textview);
        status.setText(String.format(Locale.ROOT, "Consent Status: %s", TealiumHelper.getConsentManager().getUserConsentStatus()));

        TextView categories = findViewById(R.id.current_consent_categories_textview);
        categories.setText(String.format(Locale.ROOT, "Consented Categories: %s", TealiumHelper.getConsentCategoryString()));

        Switch s = findViewById(R.id.consent_status_switch);
        s.setChecked(TealiumHelper.getConsentModel().isMasterStatus());

        Switch s1 = findViewById(R.id.analytics_switch);
        s1.setChecked(TealiumHelper.getConsentModel().isAnalytics());

        Switch s2 = findViewById(R.id.affiliates_switch);
        s2.setChecked(TealiumHelper.getConsentModel().isAffiliates());

        Switch s3 = findViewById(R.id.display_ads_switch);
        s3.setChecked(TealiumHelper.getConsentModel().isDisplayAds());

        Switch s4 = findViewById(R.id.email_switch);
        s4.setChecked(TealiumHelper.getConsentModel().isEmail());

        Switch s5 = findViewById(R.id.personalization_switch);
        s5.setChecked(TealiumHelper.getConsentModel().isPersonalization());

        Switch s6 = findViewById(R.id.search_switch);
        s6.setChecked(TealiumHelper.getConsentModel().isSearch());

        Switch s7 = findViewById(R.id.social_switch);
        s7.setChecked(TealiumHelper.getConsentModel().isSocial());

        Switch s8 = findViewById(R.id.big_data_switch);
        s8.setChecked(TealiumHelper.getConsentModel().isBigData());

        Switch s9 = findViewById(R.id.mobile_switch);
        s9.setChecked(TealiumHelper.getConsentModel().isMobile());

        Switch s10 = findViewById(R.id.monitoring_switch);
        s10.setChecked(TealiumHelper.getConsentModel().isMonitoring());

        Switch s11 = findViewById(R.id.engagement_switch);
        s11.setChecked(TealiumHelper.getConsentModel().isEngagement());

        Switch s12 = findViewById(R.id.cdp_switch);
        s12.setChecked(TealiumHelper.getConsentModel().isCdp());

        Switch s13 = findViewById(R.id.crm_switch);
        s13.setChecked(TealiumHelper.getConsentModel().isCrm());

        Switch s14 = findViewById(R.id.cookiematch_switch);
        s14.setChecked(TealiumHelper.getConsentModel().isCookiematch());

        Switch s15 = findViewById(R.id.misc_switch);
        s15.setChecked(TealiumHelper.getConsentModel().isMisc());
    }

    private CompoundButton.OnCheckedChangeListener createSwitchListener() {

        return new CompoundButton.OnCheckedChangeListener() {

            private void setCheckNoCallback(int id, boolean isChecked) {
                CompoundButton button = findViewById(id);
                button.setOnCheckedChangeListener(null);
                button.setChecked(isChecked);
                button.setOnCheckedChangeListener(this);
            }

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId()) {
                    case R.id.consent_status_switch:
                        TealiumHelper.getConsentModel().setMasterStatus(isChecked);
                        break;
                    case R.id.analytics_switch:
                        TealiumHelper.getConsentModel().setAnalytics(isChecked);
                        break;
                    case R.id.affiliates_switch:
                        TealiumHelper.getConsentModel().setAffiliates(isChecked);
                        break;
                    case R.id.display_ads_switch:
                        TealiumHelper.getConsentModel().setDisplayAds(isChecked);
                        break;
                    case R.id.email_switch:
                        TealiumHelper.getConsentModel().setEmail(isChecked);
                        break;
                    case R.id.personalization_switch:
                        TealiumHelper.getConsentModel().setPersonalization(isChecked);
                        break;
                    case R.id.search_switch:
                        TealiumHelper.getConsentModel().setSearch(isChecked);
                        break;
                    case R.id.social_switch:
                        TealiumHelper.getConsentModel().setSocial(isChecked);
                        break;
                    case R.id.big_data_switch:
                        TealiumHelper.getConsentModel().setBigData(isChecked);
                        break;
                    case R.id.mobile_switch:
                        TealiumHelper.getConsentModel().setMobile(isChecked);
                        break;
                    case R.id.monitoring_switch:
                        TealiumHelper.getConsentModel().setMonitoring(isChecked);
                        break;
                    case R.id.engagement_switch:
                        TealiumHelper.getConsentModel().setEngagement(isChecked);
                        break;
                    case R.id.cdp_switch:
                        TealiumHelper.getConsentModel().setCdp(isChecked);
                        break;
                    case R.id.crm_switch:
                        TealiumHelper.getConsentModel().setCrm(isChecked);
                        break;
                    case R.id.cookiematch_switch:
                        TealiumHelper.getConsentModel().setCookiematch(isChecked);
                        break;
                    case R.id.misc_switch:
                        TealiumHelper.getConsentModel().setMisc(isChecked);
                        break;
                }

                setCheckNoCallback(R.id.consent_status_switch, TealiumHelper.getConsentModel().isMasterStatus());
                setCheckNoCallback(R.id.analytics_switch, TealiumHelper.getConsentModel().isAnalytics());
                setCheckNoCallback(R.id.affiliates_switch, TealiumHelper.getConsentModel().isAffiliates());
                setCheckNoCallback(R.id.display_ads_switch, TealiumHelper.getConsentModel().isDisplayAds());
                setCheckNoCallback(R.id.email_switch, TealiumHelper.getConsentModel().isEmail());
                setCheckNoCallback(R.id.personalization_switch, TealiumHelper.getConsentModel().isPersonalization());
                setCheckNoCallback(R.id.search_switch, TealiumHelper.getConsentModel().isSearch());
                setCheckNoCallback(R.id.social_switch, TealiumHelper.getConsentModel().isSocial());
                setCheckNoCallback(R.id.big_data_switch, TealiumHelper.getConsentModel().isBigData());
                setCheckNoCallback(R.id.mobile_switch, TealiumHelper.getConsentModel().isMobile());
                setCheckNoCallback(R.id.monitoring_switch, TealiumHelper.getConsentModel().isMonitoring());
                setCheckNoCallback(R.id.engagement_switch, TealiumHelper.getConsentModel().isEngagement());
                setCheckNoCallback(R.id.cdp_switch, TealiumHelper.getConsentModel().isCdp());
                setCheckNoCallback(R.id.crm_switch, TealiumHelper.getConsentModel().isCrm());
                setCheckNoCallback(R.id.cookiematch_switch, TealiumHelper.getConsentModel().isCookiematch());
                setCheckNoCallback(R.id.misc_switch, TealiumHelper.getConsentModel().isMisc());

                TealiumHelper.updateConsentManager();
                updateTextViews();
            }
        };

    }
}
