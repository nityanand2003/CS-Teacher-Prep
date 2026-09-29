package com.example.csteacherprep.utils;

import android.content.Context;
import android.content.SharedPreferences;

public final class SetAccessManager {

    private SetAccessManager() {
    }

    private static final String PREF_NAME =
            "set_access_preferences";

    private static final long UNLOCK_DURATION =
            24L * 60L * 60L * 1000L;


    private static SharedPreferences getPreferences(
            Context context) {

        return context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
        );
    }


    // =========================================================
    // Check whether set is unlocked
    // =========================================================

    public static boolean isUnlocked(
            Context context,
            String key) {

        long expiryTime =
                getPreferences(context)
                        .getLong(key, 0);


        long currentTime =
                System.currentTimeMillis();


        if (expiryTime > currentTime) {

            return true;
        }


        // Expired access remove
        getPreferences(context)
                .edit()
                .remove(key)
                .apply();


        return false;
    }


    // =========================================================
    // Unlock for 24 Hours
    // =========================================================

    public static void unlockFor24Hours(
            Context context,
            String key) {

        long expiryTime =
                System.currentTimeMillis()
                        + UNLOCK_DURATION;


        getPreferences(context)
                .edit()
                .putLong(
                        key,
                        expiryTime
                )
                .apply();
    }
}