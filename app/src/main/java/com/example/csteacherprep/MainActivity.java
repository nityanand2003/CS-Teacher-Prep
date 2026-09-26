package com.example.csteacherprep;

import android.content.Intent;
import android.os.Bundle;

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

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

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
        // Modern Back Button Handling
        // =====================================================

        getOnBackPressedDispatcher()
                .addCallback(
                        this,
                        new OnBackPressedCallback(true) {

                            @Override
                            public void handleOnBackPressed() {

                                if (drawerLayout.isDrawerOpen(
                                        GravityCompat.START
                                )) {

                                    drawerLayout.closeDrawer(
                                            GravityCompat.START
                                    );

                                } else {

                                    setEnabled(false);

                                    getOnBackPressedDispatcher()
                                            .onBackPressed();
                                }
                            }
                        }
                );
    }
}