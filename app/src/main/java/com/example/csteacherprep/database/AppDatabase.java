package com.example.csteacherprep.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.csteacherprep.models.PracticeSet;
import com.example.csteacherprep.models.UserQuestion;

@Database(
        entities = {
                PracticeSet.class,
                UserQuestion.class
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PracticeSetDao practiceSetDao();

    public abstract UserQuestionDao userQuestionDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {

        if (INSTANCE == null) {

            synchronized (AppDatabase.class) {

                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "cs_teacher_prep_database"
                    ).build();
                }
            }
        }

        return INSTANCE;
    }
}