package com.example.csteacherprep.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.csteacherprep.models.UserQuestion;

import java.util.List;

@Dao
public interface UserQuestionDao {

    @Insert
    long insertQuestion(UserQuestion question);

    @Query("SELECT * FROM user_questions WHERE practiceSetId = :setId ORDER BY id ASC")
    List<UserQuestion> getQuestionsBySetId(int setId);

    @Query("SELECT * FROM user_questions WHERE id = :questionId LIMIT 1")
    UserQuestion getQuestionById(int questionId);

    @Delete
    void deleteQuestion(UserQuestion question);

    @Query("DELETE FROM user_questions WHERE practiceSetId = :setId")
    void deleteQuestionsBySetId(int setId);

    @Query("DELETE FROM user_questions")
    void deleteAllQuestions();
}