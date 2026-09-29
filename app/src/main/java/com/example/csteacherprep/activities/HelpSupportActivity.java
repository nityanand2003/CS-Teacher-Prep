package com.example.csteacherprep.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.csteacherprep.R;

public class HelpSupportActivity extends BaseAdActivity {

    private static final String SUPPORT_EMAIL =
            "03nkumar@gmail.com";

    private static final String WHATSAPP_NUMBER =
            "917856876825";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_help_support
        );


        // =================================================
        // Email Support
        // =================================================

        findViewById(
                R.id.btnEmailSupport
        ).setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            Intent.ACTION_SENDTO
                    );

            intent.setData(
                    Uri.parse(
                            "mailto:" + SUPPORT_EMAIL
                    )
            );

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


        // =================================================
        // WhatsApp Support
        // =================================================

        findViewById(
                R.id.btnWhatsAppSupport
        ).setOnClickListener(v -> {

            String message =
                    "Hello, I need help regarding CS Teacher Prep.";

            String url =
                    "https://wa.me/" +
                            WHATSAPP_NUMBER +
                            "?text=" +
                            Uri.encode(message);

            Intent intent =
                    new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(url)
                    );

            try {

                startActivity(intent);

            } catch (Exception e) {

                Toast.makeText(
                        this,
                        "WhatsApp is not available",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}