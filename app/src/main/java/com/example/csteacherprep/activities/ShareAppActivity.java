package com.example.csteacherprep.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.csteacherprep.R;

public class ShareAppActivity extends AppCompatActivity {

    // Play Store par publish hone ke baad isi link ko use kar sakte hain
    private static final String APP_LINK =
            "https://play.google.com/store/apps/details?id=com.example.csteacherprep";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_share_app);

        findViewById(R.id.btnShareApp).setOnClickListener(v -> shareApp());
    }

    private void shareApp() {

        String shareText =
                "Check out CS Teacher Prep - Computer Science Preparation App.\n\n"
                        + "Useful for Bihar STET and BPSC PGT (Computer Science) preparation.\n\n"
                        + APP_LINK;

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

        startActivity(
                Intent.createChooser(
                        shareIntent,
                        "Share CS Teacher Prep"
                )
        );
    }
}