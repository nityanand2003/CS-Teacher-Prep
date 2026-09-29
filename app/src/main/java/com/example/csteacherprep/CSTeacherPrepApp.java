package com.example.csteacherprep;

import android.app.Application;

import com.example.csteacherprep.utils.AdsManager;
import com.google.android.gms.ads.MobileAds;

public class CSTeacherPrepApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MobileAds.initialize(
                this,
                initializationStatus ->
                        AdsManager.preloadRewardedAd(this)
        );
    }
}