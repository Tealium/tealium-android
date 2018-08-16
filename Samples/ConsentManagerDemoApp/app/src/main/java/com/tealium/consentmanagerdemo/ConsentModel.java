package com.tealium.consentmanagerdemo;

import android.content.Context;
import android.content.SharedPreferences;

public class ConsentModel {

    private final SharedPreferences mSharedPreferences;
    private final static String PREFS_NAME = "PrefsFile";

    private final static String ANALYTICS_STATUS = "analytics_status";
    private final static String AFFILIATES_STATUS = "affiliates_ status";
    private final static String DISPLAY_ADS_STATUS = "display_ad_status";
    private final static String EMAIL_STATUS = "email_status";
    private final static String PERSONALIZATION_STATUS = "personalization_status";
    private final static String SEARCH_STATUS = "search_status";
    private final static String SOCIAL_STATUS = "social_status";
    private final static String BIG_DATA_STATUS = "big_data_status";
    private final static String MOBILE_STATUS = "mobile_status";
    private final static String MONITORING_STATUS = "monitoring_status";
    private final static String ENGAGEMENT_STATUS = "engagement_status";
    private final static String CDP_STATUS = "cdp_status";
    private final static String CRM_STATUS = "crm_status";
    private final static String COOKIEMATCH_STATUS = "cookiematch_status";
    private final static String MISC_STATUS = "misc_status";

    private boolean mAnalytics;
    private boolean mAffiliates;
    private boolean mDisplay_ads;
    private boolean mEmail;
    private boolean mPersonalization;
    private boolean mSearch;
    private boolean mSocial;
    private boolean mBig_data;
    private boolean mMobile;
    private boolean mMonitoring;
    private boolean mEngagement;
    private boolean mCdp;
    private boolean mCrm;
    private boolean mCookiematch;
    private boolean mMisc;


    public ConsentModel(Context context) {
        mSharedPreferences = context.getApplicationContext().getSharedPreferences(PREFS_NAME, 0);

        mAnalytics = mSharedPreferences.getBoolean(ANALYTICS_STATUS, false);
        mAffiliates = mSharedPreferences.getBoolean(AFFILIATES_STATUS, false);
        mDisplay_ads  = mSharedPreferences.getBoolean(DISPLAY_ADS_STATUS, false);
        mEmail  = mSharedPreferences.getBoolean(EMAIL_STATUS, false);
        mPersonalization = mSharedPreferences.getBoolean(PERSONALIZATION_STATUS, false);
        mSearch = mSharedPreferences.getBoolean(SEARCH_STATUS, false);
        mSocial = mSharedPreferences.getBoolean(SOCIAL_STATUS, false);
        mBig_data = mSharedPreferences.getBoolean(BIG_DATA_STATUS, false);
        mMobile = mSharedPreferences.getBoolean(MOBILE_STATUS, false);
        mMonitoring = mSharedPreferences.getBoolean(MONITORING_STATUS, false);
        mEngagement = mSharedPreferences.getBoolean(ENGAGEMENT_STATUS, false);
        mCdp = mSharedPreferences.getBoolean(CDP_STATUS, false);
        mCrm = mSharedPreferences.getBoolean(CRM_STATUS, false);
        mCookiematch = mSharedPreferences.getBoolean(COOKIEMATCH_STATUS, false);
        mMisc = mSharedPreferences.getBoolean(MISC_STATUS, false);
    }

    public void setMasterStatus (boolean status) {
        mAnalytics = status;
        mAffiliates = status;
        mDisplay_ads = status;
        mEmail = status;
        mPersonalization = status;
        mSearch = status;
        mSocial = status;
        mBig_data = status;
        mMobile = status;
        mMonitoring = status;
        mEngagement = status;
        mCdp = status;
        mCrm = status;
        mCookiematch = status;
        mMisc = status;

        mSharedPreferences.edit()
                .putBoolean(ANALYTICS_STATUS,status)
                .putBoolean(AFFILIATES_STATUS,status)
                .putBoolean(DISPLAY_ADS_STATUS,status)
                .putBoolean(EMAIL_STATUS,status)
                .putBoolean(PERSONALIZATION_STATUS,status)
                .putBoolean(SEARCH_STATUS,status)
                .putBoolean(SOCIAL_STATUS,status)
                .putBoolean(BIG_DATA_STATUS,status)
                .putBoolean(MOBILE_STATUS,status)
                .putBoolean(MONITORING_STATUS,status)
                .putBoolean(ENGAGEMENT_STATUS,status)
                .putBoolean(CDP_STATUS,status)
                .putBoolean(CRM_STATUS,status)
                .putBoolean(COOKIEMATCH_STATUS,status)
                .putBoolean(MISC_STATUS,status)
                .apply();
    }

    public boolean isMasterStatus() {
        return (mAnalytics || mAffiliates || mDisplay_ads || mEmail || mPersonalization || mSearch || mSocial
                    || mBig_data || mMobile || mMonitoring || mEngagement || mCdp || mCrm || mCookiematch || mMisc);
    }

    public boolean isAnalytics() {
        return mAnalytics;
    }

    public void setAnalytics(boolean analytics) {
        mAnalytics = analytics;
        mSharedPreferences.edit().putBoolean(ANALYTICS_STATUS, analytics).apply();
    }

    public boolean isAffiliates() {
        return  mAffiliates;

    }

    public void setAffiliates(boolean affiliates) {
        mAffiliates = affiliates;
        mSharedPreferences.edit().putBoolean(AFFILIATES_STATUS, affiliates).apply();

    }

    public boolean isDisplayAds() {
        return  mDisplay_ads;
    }

    public void setDisplayAds(boolean display_ads) {
        mDisplay_ads = display_ads;
        mSharedPreferences.edit().putBoolean(DISPLAY_ADS_STATUS, display_ads).apply();
    }

    public boolean isEmail() {
        return  mEmail;
    }

    public void setEmail(boolean email) {
        mEmail = email;
        mSharedPreferences.edit().putBoolean(EMAIL_STATUS, email).apply();
    }

    public boolean isPersonalization() {
        return mPersonalization;
    }

    public void setPersonalization(boolean personalization) {
        mPersonalization = personalization;
        mSharedPreferences.edit().putBoolean(PERSONALIZATION_STATUS, personalization).apply();
    }

    public boolean isSearch() {
        return mSearch;
    }

    public void setSearch(boolean search) {
        mSearch = search;
        mSharedPreferences.edit().putBoolean(SEARCH_STATUS, search).apply();
    }

    public boolean isSocial() {
        return mSocial;
    }

    public void setSocial(boolean social) {
        mSocial = social;
        mSharedPreferences.edit().putBoolean(SOCIAL_STATUS, social).apply();
    }

    public boolean isBigData() {
        return mBig_data;
    }

    public void setBigData(boolean big_data) {
        mBig_data = big_data;
        mSharedPreferences.edit().putBoolean(BIG_DATA_STATUS, big_data).apply();
    }

    public boolean isMobile() {
        return mMobile;
    }

    public void setMobile(boolean mobile) {
        mMobile = mobile;
        mSharedPreferences.edit().putBoolean(MOBILE_STATUS, mobile).apply();
    }

    public boolean isMonitoring() {
        return mMonitoring;
    }

    public void setMonitoring(boolean monitoring) {
        mMonitoring = monitoring;
        mSharedPreferences.edit().putBoolean(MONITORING_STATUS, monitoring).apply();
    }

    public boolean isEngagement() {
        return mEngagement;
    }

    public void setEngagement(boolean engagement) {
        mEngagement = engagement;
        mSharedPreferences.edit().putBoolean(ENGAGEMENT_STATUS, engagement).apply();
    }

    public boolean isCdp() {
        return mCdp;
    }

    public void setCdp(boolean cdp) {
        mCdp = cdp;
        mSharedPreferences.edit().putBoolean(CDP_STATUS, cdp).apply();
    }

    public boolean isCrm() {
        return mCrm;
    }

    public void setCrm(boolean crm) {
        mCrm = crm;
        mSharedPreferences.edit().putBoolean(CRM_STATUS, crm).apply();
    }

    public boolean isCookiematch() {
        return mCookiematch;
    }

    public void setCookiematch(boolean cookiematch) {
        mCookiematch = cookiematch;
        mSharedPreferences.edit().putBoolean(COOKIEMATCH_STATUS, cookiematch).apply();
    }

    public boolean isMisc() {
        return mMisc;
    }

    public void setMisc(boolean misc) {
        mMisc = misc;
        mSharedPreferences.edit().putBoolean(MISC_STATUS, misc).apply();
    }
}
