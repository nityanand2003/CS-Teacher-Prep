package com.example.csteacherprep;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.csteacherprep.activities.AboutActivity;
import com.example.csteacherprep.activities.DeveloperActivity;
import com.example.csteacherprep.activities.DisclaimerActivity;
import com.example.csteacherprep.activities.HelpSupportActivity;
import com.example.csteacherprep.activities.PrivacyPolicyActivity;
import com.example.csteacherprep.activities.RateAppActivity;
import com.example.csteacherprep.activities.ShareAppActivity;
import com.example.csteacherprep.fragments.HomeFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import android.content.Intent;

import com.google.android.material.appbar.MaterialToolbar;
import com.example.csteacherprep.activities.PracticeReminderActivity;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;

    // Double back press
    private long lastBackPressedTime = 0;

    private static final long BACK_PRESS_INTERVAL = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        MaterialToolbar commonToolbar =
                findViewById(R.id.commonToolbar);

        if (commonToolbar != null) {

            commonToolbar.setOnMenuItemClickListener(item -> {

                if (item.getItemId() ==
                        R.id.action_notifications) {

                    Intent intent =
                            new Intent(
                                    MainActivity.this,
                                    PracticeReminderActivity.class
                            );

                    startActivity(intent);

                    return true;
                }

                return false;
            });
        }

        AdView mainBannerAd = findViewById(R.id.mainBannerAd);

        if (mainBannerAd != null) {
            mainBannerAd.loadAd(
                    new AdRequest.Builder().build()
            );
        }

        // =====================================================
        // Initialize Views
        // =====================================================

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.commonToolbar);


        // =====================================================
        // Open Drawer
        // =====================================================

        toolbar.setNavigationOnClickListener(v ->
                drawerLayout.openDrawer(GravityCompat.START)
        );


        // =====================================================
        // Initial Home Fragment
        // =====================================================

        if (savedInstanceState == null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(
                            R.id.main_container,
                            new HomeFragment()
                    )
                    .commit();
        }


        // =====================================================
        // Drawer Item Click
        // =====================================================

        navigationView.setNavigationItemSelectedListener(item -> {

            int itemId = item.getItemId();


            // -------------------------------------------------
            // About App
            // -------------------------------------------------

            if (itemId == R.id.nav_about_app) {

                Intent intent = new Intent(
                        MainActivity.this,
                        AboutActivity.class
                );

                startActivity(intent);
            }


            // -------------------------------------------------
            // Developer
            // -------------------------------------------------

            else if (itemId == R.id.nav_developer) {

                Intent intent = new Intent(
                        MainActivity.this,
                        DeveloperActivity.class
                );

                startActivity(intent);
            }


            // -------------------------------------------------
            // Help & Support
            // -------------------------------------------------

            else if (itemId == R.id.nav_help_support) {

                Intent intent = new Intent(
                        MainActivity.this,
                        HelpSupportActivity.class
                );

                startActivity(intent);
            }


            // -------------------------------------------------
            // Rate This App
            // -------------------------------------------------

            else if (itemId == R.id.nav_rate_app) {

                Intent intent = new Intent(
                        MainActivity.this,
                        RateAppActivity.class
                );

                startActivity(intent);
            }


            // -------------------------------------------------
            // Share This App
            // -------------------------------------------------

            else if (itemId == R.id.nav_share_app) {

                Intent intent = new Intent(
                        MainActivity.this,
                        ShareAppActivity.class
                );

                startActivity(intent);
            }


            // -------------------------------------------------
            // Privacy Policy
            // -------------------------------------------------

            else if (itemId == R.id.nav_privacy_policy) {

                Intent intent = new Intent(
                        MainActivity.this,
                        PrivacyPolicyActivity.class
                );

                startActivity(intent);
            }


            // -------------------------------------------------
            // Disclaimer
            // -------------------------------------------------

            else if (itemId == R.id.nav_disclaimer) {

                Intent intent = new Intent(
                        MainActivity.this,
                        DisclaimerActivity.class
                );

                startActivity(intent);
            }


            // =================================================
            // Close Drawer
            // =================================================

            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        });


        // =====================================================
        // Back Button
        // =====================================================

        getOnBackPressedDispatcher()
                .addCallback(
                        this,
                        new OnBackPressedCallback(true) {

                            @Override
                            public void handleOnBackPressed() {

                                // ---------------------------------
                                // Drawer open
                                // ---------------------------------

                                if (drawerLayout.isDrawerOpen(
                                        GravityCompat.START
                                )) {

                                    drawerLayout.closeDrawer(
                                            GravityCompat.START
                                    );

                                    lastBackPressedTime = 0;

                                    return;
                                }


                                androidx.fragment.app.FragmentManager
                                        fragmentManager =
                                        getSupportFragmentManager();


                                // ---------------------------------
                                // Fragment Back Stack
                                // ---------------------------------

                                if (
                                        fragmentManager
                                                .getBackStackEntryCount()
                                                > 0
                                ) {

                                    fragmentManager.popBackStack();

                                    lastBackPressedTime = 0;

                                    return;
                                }


                                // ---------------------------------
                                // Current Fragment
                                // ---------------------------------

                                androidx.fragment.app.Fragment
                                        currentFragment =
                                        fragmentManager.findFragmentById(
                                                R.id.main_container
                                        );


                                if (
                                        currentFragment != null &&
                                                !(currentFragment instanceof HomeFragment)
                                ) {

                                    fragmentManager
                                            .beginTransaction()
                                            .replace(
                                                    R.id.main_container,
                                                    new HomeFragment()
                                            )
                                            .commit();

                                    lastBackPressedTime = 0;

                                    return;
                                }


                                // ---------------------------------
                                // Home → Double Back To Exit
                                // ---------------------------------

                                long currentTime =
                                        SystemClock.elapsedRealtime();


                                if (
                                        currentTime -
                                                lastBackPressedTime
                                                < BACK_PRESS_INTERVAL
                                ) {

                                    finishAffinity();

                                } else {

                                    lastBackPressedTime =
                                            currentTime;

                                    Toast.makeText(
                                            MainActivity.this,
                                            "Press back again to exit",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }
                        }
                );
    }
}