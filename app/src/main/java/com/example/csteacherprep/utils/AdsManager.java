package com.example.csteacherprep.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.csteacherprep.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public final class AdsManager {

    private AdsManager() {
    }

    // Google test rewarded ad
    private static final String REWARDED_AD_UNIT_ID =
            "ca-app-pub-3940256099942544/5224354917";

    private static RewardedAd rewardedAd;

    private static boolean isLoading = false;


    // =========================================================
    // Preload Rewarded Ad
    // =========================================================

    public static void preloadRewardedAd(
            Context context) {

        if (rewardedAd != null ||
                isLoading) {

            return;
        }

        isLoading = true;

        AdRequest request =
                new AdRequest.Builder()
                        .build();

        RewardedAd.load(
                context.getApplicationContext(),
                REWARDED_AD_UNIT_ID,
                request,
                new RewardedAdLoadCallback() {

                    @Override
                    public void onAdLoaded(
                            @NonNull RewardedAd ad) {

                        isLoading = false;

                        rewardedAd = ad;
                    }

                    @Override
                    public void onAdFailedToLoad(
                            @NonNull LoadAdError adError) {

                        isLoading = false;

                        rewardedAd = null;
                    }
                }
        );
    }


    // =========================================================
    // Show Rewarded Ad
    // =========================================================

    public static void showRewardedAd(
            Activity activity,
            Runnable onRewardEarned) {

        if (rewardedAd != null) {

            showLoadedAd(
                    activity,
                    onRewardEarned
            );

            return;
        }


        Toast.makeText(
                activity,
                "Loading ad...",
                Toast.LENGTH_SHORT
        ).show();


        AdRequest request =
                new AdRequest.Builder()
                        .build();


        isLoading = true;


        RewardedAd.load(
                activity,
                REWARDED_AD_UNIT_ID,
                request,
                new RewardedAdLoadCallback() {

                    @Override
                    public void onAdLoaded(
                            @NonNull RewardedAd ad) {

                        isLoading = false;

                        rewardedAd = ad;

                        showLoadedAd(
                                activity,
                                onRewardEarned
                        );
                    }

                    @Override
                    public void onAdFailedToLoad(
                            @NonNull LoadAdError adError) {

                        isLoading = false;

                        rewardedAd = null;

                        Toast.makeText(
                                activity,
                                "Ad is not available right now. Please try again.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );
    }


    // =========================================================
    // Display Loaded Ad
    // =========================================================

    private static void showLoadedAd(
            Activity activity,
            Runnable onRewardEarned) {

        RewardedAd ad = rewardedAd;

        if (ad == null) {
            preloadRewardedAd(activity);
            return;
        }

        rewardedAd = null;


        final boolean[] rewardEarned = {
                false
        };


        ad.setFullScreenContentCallback(
                new FullScreenContentCallback() {

                    @Override
                    public void onAdDismissedFullScreenContent() {

                        if (!rewardEarned[0]) {

                            Toast.makeText(
                                    activity,
                                    "Set unlock ke liye ad complete karein.",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }

                        preloadRewardedAd(activity);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(
                            @NonNull com.google.android.gms.ads.AdError adError) {

                        preloadRewardedAd(activity);
                    }
                }
        );


        ad.show(
                activity,
                rewardItem -> {

                    rewardEarned[0] = true;

                    onRewardEarned.run();
                }
        );
    }
}