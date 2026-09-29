package com.example.csteacherprep.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.csteacherprep.MainActivity;
import com.example.csteacherprep.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 1500;

    private final Handler handler =
            new Handler(Looper.getMainLooper());

    private final Runnable splashRunnable = () -> {

        Intent intent = new Intent(
                SplashActivity.this,
                MainActivity.class
        );

        startActivity(intent);

        finish();
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);


        handler.postDelayed(
                splashRunnable,
                SPLASH_DURATION
        );
    }


    @Override
    protected void onDestroy() {

        handler.removeCallbacks(
                splashRunnable
        );

        super.onDestroy();
    }
}