package com.example.csteacherprep.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.csteacherprep.receivers.PracticeReminderReceiver;

import java.util.Calendar;

public final class ReminderScheduler {

    private ReminderScheduler() {
    }

    private static final String PREF_NAME =
            "practice_reminder_preferences";

    private static final String KEY_ENABLED =
            "reminder_enabled";

    private static final String KEY_HOUR =
            "reminder_hour";

    private static final String KEY_MINUTE =
            "reminder_minute";

    private static final int DEFAULT_HOUR = 19;
    private static final int DEFAULT_MINUTE = 0;

    private static final int REQUEST_CODE = 2001;

    // =========================================================
    // Preferences
    // =========================================================

    private static android.content.SharedPreferences
    getPreferences(Context context) {

        return context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
        );
    }

    // =========================================================
    // Save Reminder
    // =========================================================

    public static void saveReminder(
            Context context,
            int hour,
            int minute) {

        getPreferences(context)
                .edit()
                .putBoolean(
                        KEY_ENABLED,
                        true
                )
                .putInt(
                        KEY_HOUR,
                        hour
                )
                .putInt(
                        KEY_MINUTE,
                        minute
                )
                .apply();
    }

    // =========================================================
    // Disable Reminder
    // =========================================================

    public static void disableReminder(
            Context context) {

        getPreferences(context)
                .edit()
                .putBoolean(
                        KEY_ENABLED,
                        false
                )
                .apply();

        cancelReminder(context);
    }

    // =========================================================
    // Check Enabled
    // =========================================================

    public static boolean isReminderEnabled(
            Context context) {

        return getPreferences(context)
                .getBoolean(
                        KEY_ENABLED,
                        false
                );
    }

    // =========================================================
    // Saved Hour
    // =========================================================

    public static int getSavedHour(
            Context context) {

        return getPreferences(context)
                .getInt(
                        KEY_HOUR,
                        DEFAULT_HOUR
                );
    }

    // =========================================================
    // Saved Minute
    // =========================================================

    public static int getSavedMinute(
            Context context) {

        return getPreferences(context)
                .getInt(
                        KEY_MINUTE,
                        DEFAULT_MINUTE
                );
    }

    // =========================================================
    // Schedule Reminder
    // =========================================================

    public static void scheduleReminder(
            Context context) {

        if (!isReminderEnabled(context)) {
            return;
        }

        AlarmManager alarmManager =
                (AlarmManager)
                        context.getSystemService(
                                Context.ALARM_SERVICE
                        );

        if (alarmManager == null) {
            return;
        }

        Calendar calendar =
                Calendar.getInstance();

        calendar.set(
                Calendar.HOUR_OF_DAY,
                getSavedHour(context)
        );

        calendar.set(
                Calendar.MINUTE,
                getSavedMinute(context)
        );

        calendar.set(
                Calendar.SECOND,
                0
        );

        calendar.set(
                Calendar.MILLISECOND,
                0
        );

        // If today's time has already passed,
        // schedule for tomorrow.
        if (
                calendar.getTimeInMillis()
                        <= System.currentTimeMillis()
        ) {

            calendar.add(
                    Calendar.DAY_OF_YEAR,
                    1
            );
        }

        Intent intent =
                new Intent(
                        context,
                        PracticeReminderReceiver.class
                );

        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(
                        context,
                        REQUEST_CODE,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                                | PendingIntent.FLAG_IMMUTABLE
                );

        alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                pendingIntent
        );
    }

    // =========================================================
    // Cancel Reminder
    // =========================================================

    public static void cancelReminder(
            Context context) {

        AlarmManager alarmManager =
                (AlarmManager)
                        context.getSystemService(
                                Context.ALARM_SERVICE
                        );

        if (alarmManager == null) {
            return;
        }

        Intent intent =
                new Intent(
                        context,
                        PracticeReminderReceiver.class
                );

        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(
                        context,
                        REQUEST_CODE,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                                | PendingIntent.FLAG_IMMUTABLE
                );

        alarmManager.cancel(
                pendingIntent
        );
    }
}