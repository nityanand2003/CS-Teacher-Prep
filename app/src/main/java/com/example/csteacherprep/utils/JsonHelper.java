package com.example.csteacherprep.utils;

import android.content.Context;

import com.example.csteacherprep.models.Question;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JsonHelper {

    private JsonHelper() {
        // Utility class
    }

    public static List<Question> loadQuestions(Context context, int resourceId) {

        List<Question> questionList = new ArrayList<>();

        try {
            InputStream inputStream = context.getResources().openRawResource(resourceId);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream)
            );

            StringBuilder jsonBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }

            reader.close();
            inputStream.close();

            JSONArray jsonArray = new JSONArray(jsonBuilder.toString());

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject object = jsonArray.getJSONObject(i);

                Question question = new Question();

                question.setId(object.getInt("id"));
                question.setQuestionText(object.getString("questionText"));

                question.setOptionA(object.getString("optionA"));
                question.setOptionB(object.getString("optionB"));
                question.setOptionC(object.getString("optionC"));
                question.setOptionD(object.getString("optionD"));
                question.setOptionE(object.getString("optionE"));

                question.setCorrectAnswer(
                        object.getString("correctAnswer")
                );

                question.setExplanation(
                        object.optString("explanation", "")
                );

                questionList.add(question);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return questionList;
    }
}