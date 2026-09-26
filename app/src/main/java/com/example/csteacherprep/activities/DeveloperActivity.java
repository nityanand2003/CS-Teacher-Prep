package com.example.csteacherprep.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.csteacherprep.R;

public class DeveloperActivity extends AppCompatActivity {

    // =========================================================
    // Replace these two values with your actual contact details
    // =========================================================

    private static final String WHATSAPP_NUMBER = "917856876825";
    private static final String DEVELOPER_EMAIL = "03nkumar@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_developer);

        // ================= SOCIAL LINKS =================

        findViewById(R.id.btnLinkedIn).setOnClickListener(v ->
                openUrl("https://www.linkedin.com/in/nkumar03")
        );

        findViewById(R.id.btnFacebook).setOnClickListener(v ->
                openUrl("https://www.facebook.com/03nkumar")
        );

        findViewById(R.id.btnInstagram).setOnClickListener(v ->
                openUrl("https://www.instagram.com/nkumar.03")
        );

        findViewById(R.id.btnYouTube).setOnClickListener(v ->
                openUrl("https://www.youtube.com/@Nkumarshorts")
        );

        findViewById(R.id.btnHackerRank).setOnClickListener(v ->
                openUrl("https://www.hackerrank.com/nityanand2003")
        );

        findViewById(R.id.btnLeetCode).setOnClickListener(v ->
                openUrl("https://leetcode.com/nityanand2003")
        );

        findViewById(R.id.btnGitHub).setOnClickListener(v ->
                openUrl("https://github.com/nityanand2003")
        );

        // ================= WHATSAPP =================

        findViewById(R.id.btnWhatsApp).setOnClickListener(v -> {

            if (WHATSAPP_NUMBER.contains("X")) {
                Toast.makeText(
                        DeveloperActivity.this,
                        "Please add WhatsApp number in DeveloperActivity.java",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            String url = "https://wa.me/" + WHATSAPP_NUMBER;

            openUrl(url);
        });

        // ================= EMAIL =================

        findViewById(R.id.btnEmail).setOnClickListener(v -> {

            if (DEVELOPER_EMAIL.contains("your-email")) {
                Toast.makeText(
                        DeveloperActivity.this,
                        "Please add your email in DeveloperActivity.java",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:" + DEVELOPER_EMAIL));
            emailIntent.putExtra(
                    Intent.EXTRA_SUBJECT,
                    "Contact from CS Teacher Prep"
            );

            try {
                startActivity(emailIntent);
            } catch (Exception e) {
                Toast.makeText(
                        DeveloperActivity.this,
                        "No email application found",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    // ================= OPEN URL =================

    private void openUrl(String url) {

        try {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);

        } catch (Exception e) {

            Toast.makeText(
                    DeveloperActivity.this,
                    "Unable to open link",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}