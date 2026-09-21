package com.example.csteacherprep.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.csteacherprep.models.PracticeSet;

import java.util.List;

@Dao
public interface PracticeSetDao {

    @Insert
    long insertSet(PracticeSet practiceSet);

    @Query("SELECT * FROM practice_sets ORDER BY createdAt DESC")
    List<PracticeSet> getAllSets();

    @Query("SELECT * FROM practice_sets WHERE id = :setId LIMIT 1")
    PracticeSet getSetById(int setId);

    @Delete
    void deleteSet(PracticeSet practiceSet);

    @Update
    void updateSet(PracticeSet practiceSet);

    @Query("DELETE FROM practice_sets")
    void deleteAllSets();
}