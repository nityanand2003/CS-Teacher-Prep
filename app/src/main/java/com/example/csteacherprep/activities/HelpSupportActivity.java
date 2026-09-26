package com.example.csteacherprep.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.csteacherprep.R;

public class HelpSupportActivity extends AppCompatActivity {

    private static final String SUPPORT_EMAIL = "your-email@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_help_support);

        findViewById(R.id.btnEmailSupport).setOnClickListener(v -> {

            if (SUPPORT_EMAIL.contains("your-email")) {
                Toast.makeText(
                        this,
                        "Please add support email in HelpSupportActivity.java",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + SUPPORT_EMAIL));
            intent.putExtra(
                    Intent.EXTRA_SUBJECT,
                    "Help & Support - CS Teacher Prep"
            );

            try {
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(
                        this,
                        "No email application found",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}