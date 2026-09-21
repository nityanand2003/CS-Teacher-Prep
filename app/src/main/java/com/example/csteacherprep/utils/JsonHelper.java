package com.example.csteacherprep.utils;

import android.content.Context;

import com.example.csteacherprep.models.JsonSet;
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

    // Load complete JSON set
    public static JsonSet loadSet(Context context, int resourceId) {

        JsonSet jsonSet = new JsonSet();

        try {
            InputStream inputStream =
                    context.getResources().openRawResource(resourceId);

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

            JSONObject root =
                    new JSONObject(jsonBuilder.toString());

            jsonSet.setSetId(
                    root.optString("setId", "")
            );

            jsonSet.setSetName(
                    root.optString("setName", "")
            );

            jsonSet.setExam(
                    root.optString("exam", "")
            );

            jsonSet.setType(
                    root.optString("type", "")
            );

            jsonSet.setDescription(
                    root.optString("description", "")
            );

            JSONArray jsonArray =
                    root.getJSONArray("questions");

            List<Question> questionList =
                    new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject object =
                        jsonArray.getJSONObject(i);

                Question question = new Question();

                question.setId(
                        object.getInt("id")
                );

                question.setQuestionText(
                        object.getString("questionText")
                );

                question.setOptionA(
                        object.getString("optionA")
                );

                question.setOptionB(
                        object.getString("optionB")
                );

                question.setOptionC(
                        object.getString("optionC")
                );

                question.setOptionD(
                        object.getString("optionD")
                );

                question.setOptionE(
                        object.getString("optionE")
                );

                question.setCorrectAnswer(
                        object.getString("correctAnswer")
                );

                question.setExplanation(
                        object.optString("explanation", "")
                );

                questionList.add(question);
            }

            jsonSet.setQuestions(questionList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonSet;
    }


    // Old method - used by QuizActivity
    public static List<Question> loadQuestions(
            Context context,
            int resourceId) {

        JsonSet jsonSet = loadSet(context, resourceId);

        if (jsonSet.getQuestions() != null) {
            return jsonSet.getQuestions();
        }

        return new ArrayList<>();
    }
}