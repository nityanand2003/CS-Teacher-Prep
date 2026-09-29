package com.example.csteacherprep.activities;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.csteacherprep.R;
import com.example.csteacherprep.utils.ReminderScheduler;
import com.google.android.material.button.MaterialButton;

import java.util.Locale;

public class PracticeReminderActivity extends BaseAdActivity {

    private static final int NOTIFICATION_PERMISSION_REQUEST = 1001;

    private TextView tvSelectedTime;
    private TextView tvReminderStatus;
    private Switch switchReminder;
    private MaterialButton btnSelectTime;
    private MaterialButton btnSaveReminder;

    private int selectedHour;
    private int selectedMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_practice_reminder);

        tvSelectedTime =
                findViewById(R.id.tvSelectedTime);

        tvReminderStatus =
                findViewById(R.id.tvReminderStatus);

        switchReminder =
                findViewById(R.id.switchReminder);

        btnSelectTime =
                findViewById(R.id.btnSelectTime);

        btnSaveReminder =
                findViewById(R.id.btnSaveReminder);

        loadSavedReminder();

        btnSelectTime.setOnClickListener(v ->
                showTimePicker()
        );

        switchReminder.setOnCheckedChangeListener(
                (buttonView, isChecked) -> updateStatus()
        );

        btnSaveReminder.setOnClickListener(v ->
                saveReminder()
        );
    }

    // =========================================================
    // Load Saved Reminder
    // =========================================================

    private void loadSavedReminder() {

        selectedHour =
                ReminderScheduler.getSavedHour(this);

        selectedMinute =
                ReminderScheduler.getSavedMinute(this);

        boolean enabled =
                ReminderScheduler.isReminderEnabled(this);

        switchReminder.setChecked(enabled);

        updateTimeText();
        updateStatus();
    }

    // =========================================================
    // Time Picker
    // =========================================================

    private void showTimePicker() {

        TimePickerDialog dialog =
                new TimePickerDialog(
                        this,
                        (view, hourOfDay, minute) -> {

                            selectedHour = hourOfDay;
                            selectedMinute = minute;

                            updateTimeText();
                            updateStatus();
                        },
                        selectedHour,
                        selectedMinute,
                        false
                );

        dialog.show();
    }

    // =========================================================
    // Update Time Text
    // =========================================================

    private void updateTimeText() {

        String formattedTime =
                String.format(
                        Locale.getDefault(),
                        "%02d:%02d",
                        selectedHour,
                        selectedMinute
                );

        tvSelectedTime.setText(formattedTime);
    }

    // =========================================================
    // Status
    // =========================================================

    private void updateStatus() {

        if (switchReminder.isChecked()) {

            String time =
                    String.format(
                            Locale.getDefault(),
                            "%02d:%02d",
                            selectedHour,
                            selectedMinute
                    );

            tvReminderStatus.setText(
                    "Reminder is ON • Daily at " + time
            );

        } else {

            tvReminderStatus.setText(
                    "Reminder is currently OFF"
            );
        }
    }

    // =========================================================
    // Save Reminder
    // =========================================================

    private void saveReminder() {

        boolean enabled =
                switchReminder.isChecked();

        if (!enabled) {

            ReminderScheduler.disableReminder(this);

            Toast.makeText(
                    this,
                    "Practice reminder disabled.",
                    Toast.LENGTH_SHORT
            ).show();

            updateStatus();

            return;
        }

        ReminderScheduler.saveReminder(
                this,
                selectedHour,
                selectedMinute
        );

        ReminderScheduler.scheduleReminder(
                this
        );

        // Android 13+ notification permission
        if (
                Build.VERSION.SDK_INT >=
                        Build.VERSION_CODES.TIRAMISU
        ) {

            if (
                    ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.POST_NOTIFICATIONS
                    )
                            != PackageManager.PERMISSION_GRANTED
            ) {

                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.POST_NOTIFICATIONS
                        },
                        NOTIFICATION_PERMISSION_REQUEST
                );

            } else {

                Toast.makeText(
                        this,
                        "Daily practice reminder saved.",
                        Toast.LENGTH_SHORT
                ).show();
            }

        } else {

            Toast.makeText(
                    this,
                    "Daily practice reminder saved.",
                    Toast.LENGTH_SHORT
            ).show();
        }

        updateStatus();
    }

    // =========================================================
    // Notification Permission Result
    // =========================================================

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
        );

        if (
                requestCode ==
                        NOTIFICATION_PERMISSION_REQUEST
        ) {

            if (
                    grantResults.length > 0 &&
                            grantResults[0] ==
                                    PackageManager.PERMISSION_GRANTED
            ) {

                Toast.makeText(
                        this,
                        "Daily practice reminder saved.",
                        Toast.LENGTH_SHORT
                ).show();

            } else {

                Toast.makeText(
                        this,
                        "Notification permission is required to show reminders.",
                        Toast.LENGTH_LONG
                ).show();
            }
        }
    }
}