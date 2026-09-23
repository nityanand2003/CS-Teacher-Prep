package com.example.csteacherprep.utils;

import android.content.Context;
import android.content.res.AssetManager;

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

    // =========================================================
    // OLD METHOD
    // Existing res/raw JSON files के लिए
    // =========================================================

    public static JsonSet loadSet(Context context, int resourceId) {

        JsonSet jsonSet = new JsonSet();

        try {

            InputStream inputStream =
                    context.getResources().openRawResource(resourceId);

            jsonSet = parseJson(
                    readInputStream(inputStream)
            );

            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonSet;
    }


    // =========================================================
    // NEW METHOD
    // assets से JSON file load करने के लिए
    // =========================================================

    public static JsonSet loadAssetSet(
            Context context,
            String assetPath) {

        JsonSet jsonSet = new JsonSet();

        try {

            AssetManager assetManager =
                    context.getAssets();

            InputStream inputStream =
                    assetManager.open(assetPath);

            jsonSet = parseJson(
                    readInputStream(inputStream)
            );

            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonSet;
    }


    // =========================================================
    // JSON String को JsonSet में convert करना
    // =========================================================

    private static JsonSet parseJson(String jsonString)
            throws Exception {

        JsonSet jsonSet = new JsonSet();

        JSONObject root =
                new JSONObject(jsonString);


        // Basic information

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


        // Topic Wise information

        jsonSet.setSection(
                root.optString("section", "")
        );

        jsonSet.setTopicId(
                root.optString("topicId", "")
        );

        jsonSet.setTopicName(
                root.optString("topicName", "")
        );

        jsonSet.setLevel(
                root.optString("level", "")
        );

        jsonSet.setOrder(
                root.optInt("order", 0)
        );


        // Questions

        JSONArray jsonArray =
                root.optJSONArray("questions");

        List<Question> questionList =
                new ArrayList<>();

        if (jsonArray != null) {

            for (int i = 0;
                 i < jsonArray.length();
                 i++) {

                JSONObject object =
                        jsonArray.getJSONObject(i);

                Question question =
                        new Question();

                question.setId(
                        object.optInt("id", i + 1)
                );

                question.setQuestionText(
                        object.optString(
                                "questionText",
                                ""
                        )
                );

                question.setOptionA(
                        object.optString(
                                "optionA",
                                ""
                        )
                );

                question.setOptionB(
                        object.optString(
                                "optionB",
                                ""
                        )
                );

                question.setOptionC(
                        object.optString(
                                "optionC",
                                ""
                        )
                );

                question.setOptionD(
                        object.optString(
                                "optionD",
                                ""
                        )
                );

                question.setOptionE(
                        object.optString(
                                "optionE",
                                ""
                        )
                );

                question.setCorrectAnswer(
                        object.optString(
                                "correctAnswer",
                                ""
                        )
                );

                question.setExplanation(
                        object.optString(
                                "explanation",
                                ""
                        )
                );

                questionList.add(question);
            }
        }

        jsonSet.setQuestions(questionList);

        return jsonSet;
    }


    // =========================================================
    // InputStream → String
    // =========================================================

    private static String readInputStream(
            InputStream inputStream)
            throws Exception {

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                inputStream
                        )
                );

        StringBuilder jsonBuilder =
                new StringBuilder();

        String line;

        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        reader.close();

        return jsonBuilder.toString();
    }


    // =========================================================
    // OLD METHOD
    // QuizActivity के लिए
    // =========================================================

    public static List<Question> loadQuestions(
            Context context,
            int resourceId) {

        JsonSet jsonSet =
                loadSet(context, resourceId);

        if (jsonSet.getQuestions() != null) {
            return jsonSet.getQuestions();
        }

        return new ArrayList<>();
    }


    // =========================================================
    // NEW METHOD
    // Assets JSON के questions के लिए
    // =========================================================

    public static List<Question> loadAssetQuestions(
            Context context,
            String assetPath) {

        JsonSet jsonSet =
                loadAssetSet(
                        context,
                        assetPath
                );

        if (jsonSet.getQuestions() != null) {
            return jsonSet.getQuestions();
        }

        return new ArrayList<>();
    }
}