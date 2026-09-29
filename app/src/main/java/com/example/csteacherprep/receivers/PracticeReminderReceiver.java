package com.example.csteacherprep.receivers;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.csteacherprep.MainActivity;
import com.example.csteacherprep.R;
import com.example.csteacherprep.utils.ReminderScheduler;

public class PracticeReminderReceiver
        extends BroadcastReceiver {

    private static final String CHANNEL_ID =
            "practice_reminder_channel";

    private static final int NOTIFICATION_ID =
            3001;

    @Override
    public void onReceive(
            Context context,
            Intent intent) {

        // Schedule the next day's reminder
        if (
                ReminderScheduler
                        .isReminderEnabled(context)
        ) {

            ReminderScheduler
                    .scheduleReminder(context);
        }

        // Android 8+ notification channel
        createNotificationChannel(context);

        // Android 13+ permission check
        if (
                Build.VERSION.SDK_INT >=
                        Build.VERSION_CODES.TIRAMISU
        ) {

            if (
                    context.checkSelfPermission(
                            Manifest.permission.POST_NOTIFICATIONS
                    )
                            != PackageManager.PERMISSION_GRANTED
            ) {

                return;
            }
        }

        Intent openAppIntent =
                new Intent(
                        context,
                        MainActivity.class
                );

        openAppIntent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
        );

        android.app.PendingIntent contentIntent =
                android.app.PendingIntent.getActivity(
                        context,
                        4001,
                        openAppIntent,
                        android.app.PendingIntent.FLAG_UPDATE_CURRENT
                                | android.app.PendingIntent.FLAG_IMMUTABLE
                );

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(
                        context,
                        CHANNEL_ID
                )
                        .setSmallIcon(
                                R.drawable.ic_cs_teacher_prep_notification
                        )
                        .setContentTitle(
                                "Time for your practice!"
                        )
                        .setContentText(
                                "Open CS Teacher Prep and start your daily Computer Science practice."
                        )
                        .setStyle(
                                new NotificationCompat.BigTextStyle()
                                        .bigText(
                                                "It's time for your Computer Science practice. Open CS Teacher Prep and start practicing."
                                        )
                        )
                        .setPriority(
                                NotificationCompat.PRIORITY_DEFAULT
                        )
                        .setAutoCancel(true)
                        .setContentIntent(
                                contentIntent
                        );

        NotificationManagerCompat
                notificationManager =
                NotificationManagerCompat.from(
                        context
                );

        notificationManager.notify(
                NOTIFICATION_ID,
                builder.build()
        );
    }

    // =========================================================
    // Notification Channel
    // =========================================================

    private void createNotificationChannel(
            Context context) {

        if (
                Build.VERSION.SDK_INT >=
                        Build.VERSION_CODES.O
        ) {

            NotificationChannel channel =
                    new NotificationChannel(
                            CHANNEL_ID,
                            "Practice Reminders",
                            NotificationManager.IMPORTANCE_DEFAULT
                    );

            channel.setDescription(
                    "Daily Computer Science practice reminders"
            );

            NotificationManager manager =
                    context.getSystemService(
                            NotificationManager.class
                    );

            if (manager != null) {

                manager.createNotificationChannel(
                        channel
                );
            }
        }
    }
}