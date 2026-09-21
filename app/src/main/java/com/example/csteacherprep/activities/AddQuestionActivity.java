package com.example.csteacherprep.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.csteacherprep.R;
import com.example.csteacherprep.database.AppDatabase;
import com.example.csteacherprep.models.UserQuestion;

public class AddQuestionActivity extends AppCompatActivity {

    private EditText etQuestion;
    private EditText etOptionA;
    private EditText etOptionB;
    private EditText etOptionC;
    private EditText etOptionD;
    private EditText etOptionE;
    private EditText etExplanation;

    private RadioGroup radioGroupCorrect;

    private int setId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_question);

        setId = getIntent().getIntExtra("set_id", -1);

        etQuestion = findViewById(R.id.etQuestion);
        etOptionA = findViewById(R.id.etOptionA);
        etOptionB = findViewById(R.id.etOptionB);
        etOptionC = findViewById(R.id.etOptionC);
        etOptionD = findViewById(R.id.etOptionD);
        etOptionE = findViewById(R.id.etOptionE);
        etExplanation = findViewById(R.id.etExplanation);

        radioGroupCorrect = findViewById(R.id.radioGroupCorrect);

        // Add Question button
        findViewById(R.id.btnAddQuestion)
                .setOnClickListener(v -> saveQuestion());

        // Save Set button
        findViewById(R.id.btnSaveSet)
                .setOnClickListener(v -> saveSet());
    }

    private void saveQuestion() {

        String question = etQuestion.getText().toString().trim();
        String optionA = etOptionA.getText().toString().trim();
        String optionB = etOptionB.getText().toString().trim();
        String optionC = etOptionC.getText().toString().trim();
        String optionD = etOptionD.getText().toString().trim();
        String optionE = etOptionE.getText().toString().trim();
        String explanation = etExplanation.getText().toString().trim();

        if (question.isEmpty()) {
            etQuestion.setError("Enter question");
            return;
        }

        if (optionA.isEmpty() ||
                optionB.isEmpty() ||
                optionC.isEmpty() ||
                optionD.isEmpty() ||
                optionE.isEmpty()) {

            Toast.makeText(
                    this,
                    "Fill all options",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        int selectedId =
                radioGroupCorrect.getCheckedRadioButtonId();

        if (selectedId == -1) {

            Toast.makeText(
                    this,
                    "Select correct answer",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        String correctAnswer = "A";

        if (selectedId == R.id.radioB) {
            correctAnswer = "B";
        } else if (selectedId == R.id.radioC) {
            correctAnswer = "C";
        } else if (selectedId == R.id.radioD) {
            correctAnswer = "D";
        } else if (selectedId == R.id.radioE) {
            correctAnswer = "E";
        }

        UserQuestion userQuestion = new UserQuestion();

        userQuestion.setPracticeSetId(setId);
        userQuestion.setQuestionText(question);
        userQuestion.setOptionA(optionA);
        userQuestion.setOptionB(optionB);
        userQuestion.setOptionC(optionC);
        userQuestion.setOptionD(optionD);
        userQuestion.setOptionE(optionE);
        userQuestion.setCorrectAnswer(correctAnswer);
        userQuestion.setExplanation(explanation);

        new Thread(() -> {

            AppDatabase db = AppDatabase.getInstance(
                    getApplicationContext()
            );

            db.userQuestionDao().insertQuestion(userQuestion);

            runOnUiThread(() -> {

                Toast.makeText(
                        this,
                        "Question added successfully",
                        Toast.LENGTH_SHORT
                ).show();

                clearFields();
            });

        }).start();
    }

    private void saveSet() {

        if (setId == -1) {
            Toast.makeText(
                    this,
                    "Invalid set",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        new Thread(() -> {

            AppDatabase db = AppDatabase.getInstance(
                    getApplicationContext()
            );

            int questionCount = db.userQuestionDao()
                    .getQuestionsBySetId(setId)
                    .size();

            runOnUiThread(() -> {

                if (questionCount == 0) {

                    Toast.makeText(
                            this,
                            "Add at least one question first",
                            Toast.LENGTH_SHORT
                    ).show();

                    return;
                }

                Toast.makeText(
                        this,
                        "Set saved successfully",
                        Toast.LENGTH_SHORT
                ).show();

                finish();
            });

        }).start();
    }

    private void clearFields() {

        etQuestion.setText("");
        etOptionA.setText("");
        etOptionB.setText("");
        etOptionC.setText("");
        etOptionD.setText("");
        etOptionE.setText("");
        etExplanation.setText("");

        radioGroupCorrect.clearCheck();

        etQuestion.requestFocus();
    }
}