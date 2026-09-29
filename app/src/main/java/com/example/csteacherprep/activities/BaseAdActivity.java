package com.example.csteacherprep.activities;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.csteacherprep.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.card.MaterialCardView;

public abstract class BaseAdActivity
        extends AppCompatActivity {

    private boolean bannerAdded = false;

    private static final String BANNER_AD_UNIT_ID =
            "ca-app-pub-3940256099942544/9214589741";


    @Override
    public void setContentView(
            int layoutResID) {

        super.setContentView(layoutResID);

        addBottomBanner();
    }


    @Override
    public void setContentView(
            View view) {

        super.setContentView(view);

        addBottomBanner();
    }


    @Override
    public void setContentView(
            View view,
            ViewGroup.LayoutParams params) {

        super.setContentView(
                view,
                params
        );

        addBottomBanner();
    }


    // =========================================================
    // Add Bottom Banner
    // =========================================================

    private void addBottomBanner() {

        if (bannerAdded) {
            return;
        }


        ViewGroup content =
                findViewById(
                        android.R.id.content
                );


        if (content == null ||
                content.getChildCount() == 0) {

            return;
        }


        View root =
                content.getChildAt(0);


        content.removeView(root);


        LinearLayout wrapper =
                new LinearLayout(this);

        wrapper.setOrientation(
                LinearLayout.VERTICAL
        );


        // Original screen
        LinearLayout.LayoutParams
                contentParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        0,
                        1f
                );


        wrapper.addView(
                root,
                contentParams
        );


        // =====================================================
        // Ad Card
        // =====================================================

        MaterialCardView adCard =
                new MaterialCardView(this);

        adCard.setRadius(
                dp(10)
        );

        adCard.setCardElevation(
                0
        );

        adCard.setCardBackgroundColor(
                getColor(
                        R.color.primary_blue_light
                )
        );


        LinearLayout.LayoutParams
                adCardParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        dp(60)
                );

        adCardParams.setMargins(
                dp(12),
                dp(4),
                dp(12),
                dp(6)
        );


        AdView adView =
                new AdView(this);

        adView.setAdSize(
                AdSize.BANNER
        );

        adView.setAdUnitId(
                BANNER_AD_UNIT_ID
        );


        adCard.addView(
                adView,
                new MaterialCardView.LayoutParams(
                        dp(320),
                        dp(50)
                )
        );


        MaterialCardView.LayoutParams
                adParams =
                (MaterialCardView.LayoutParams)
                        adView.getLayoutParams();

        adParams.gravity =
                Gravity.CENTER;


        adView.setLayoutParams(
                adParams
        );


        wrapper.addView(
                adCard,
                adCardParams
        );


        content.addView(
                wrapper,
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
        );


        adView.loadAd(
                new AdRequest.Builder()
                        .build()
        );


        bannerAdded = true;
    }


    private int dp(int value) {

        return (int)
                (
                        value *
                                getResources()
                                        .getDisplayMetrics()
                                        .density
                );
    }
}