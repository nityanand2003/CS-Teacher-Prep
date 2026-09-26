package com.example.csteacherprep.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.csteacherprep.R;

public class RateAppActivity extends AppCompatActivity {

    // Play Store par publish hone ke baad isi link ka use hoga
    private static final String PLAY_STORE_URL =
            "https://play.google.com/store/apps/details?id=com.example.csteacherprep";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rate_app);

        findViewById(R.id.btnRateApp).setOnClickListener(v -> openPlayStore());
    }

    private void openPlayStore() {

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(
                    "market://details?id=com.example.csteacherprep"
            ));
            startActivity(intent);

        } catch (Exception e) {

            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(PLAY_STORE_URL));
                startActivity(intent);

            } catch (Exception ex) {

                Toast.makeText(
                        this,
                        "Unable to open Play Store",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }
}