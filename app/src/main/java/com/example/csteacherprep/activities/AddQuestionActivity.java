package com.example.csteacherprep.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.csteacherprep.R;
import com.example.csteacherprep.database.AppDatabase;
import com.example.csteacherprep.models.UserQuestion;
import com.google.android.material.button.MaterialButton;

public class AddQuestionActivity extends AppCompatActivity {

    private EditText etQuestion;
    private EditText etOptionA;
    private EditText etOptionB;
    private EditText etOptionC;
    private EditText etOptionD;
    private EditText etOptionE;
    private EditText etExplanation;

    private RadioGroup radioGroupCorrect;

    private int setId = -1;

    private boolean editMode = false;
    private int questionId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_question);

        setId = getIntent().getIntExtra(
                "set_id",
                -1
        );

        editMode = getIntent().getBooleanExtra(
                "edit_mode",
                false
        );

        questionId = getIntent().getIntExtra(
                "question_id",
                -1
        );

        etQuestion = findViewById(R.id.etQuestion);
        etOptionA = findViewById(R.id.etOptionA);
        etOptionB = findViewById(R.id.etOptionB);
        etOptionC = findViewById(R.id.etOptionC);
        etOptionD = findViewById(R.id.etOptionD);
        etOptionE = findViewById(R.id.etOptionE);
        etExplanation = findViewById(R.id.etExplanation);

        radioGroupCorrect =
                findViewById(R.id.radioGroupCorrect);

        if (editMode) {

            MaterialButton btnAddQuestion =
                    findViewById(R.id.btnAddQuestion);

            MaterialButton btnSaveSet =
                    findViewById(R.id.btnSaveSet);

            btnAddQuestion.setText("Update Question");
            btnSaveSet.setText("Cancel");

            loadQuestionForEdit();

            btnAddQuestion.setOnClickListener(
                    v -> updateQuestion()
            );

            btnSaveSet.setOnClickListener(
                    v -> finish()
            );

        } else {

            findViewById(R.id.btnAddQuestion)
                    .setOnClickListener(
                            v -> saveQuestion()
                    );

            findViewById(R.id.btnSaveSet)
                    .setOnClickListener(
                            v -> saveSet()
                    );
        }
    }

    private void loadQuestionForEdit() {

        if (questionId == -1) {
            return;
        }

        new Thread(() -> {

            AppDatabase db =
                    AppDatabase.getInstance(
                            getApplicationContext()
                    );

            UserQuestion question =
                    db.userQuestionDao()
                            .getQuestionById(
                                    questionId
                            );

            runOnUiThread(() -> {

                if (question == null) {
                    Toast.makeText(
                            this,
                            "Question not found",
                            Toast.LENGTH_SHORT
                    ).show();

                    finish();
                    return;
                }

                etQuestion.setText(
                        question.getQuestionText()
                );

                etOptionA.setText(
                        question.getOptionA()
                );

                etOptionB.setText(
                        question.getOptionB()
                );

                etOptionC.setText(
                        question.getOptionC()
                );

                etOptionD.setText(
                        question.getOptionD()
                );

                etOptionE.setText(
                        question.getOptionE()
                );

                etExplanation.setText(
                        question.getExplanation()
                );

                selectCorrectAnswer(
                        question.getCorrectAnswer()
                );
            });

        }).start();
    }

    private void selectCorrectAnswer(
            String correctAnswer) {

        if (correctAnswer == null) {
            return;
        }

        switch (correctAnswer.toUpperCase()) {

            case "A":
                radioGroupCorrect.check(
                        R.id.radioA
                );
                break;

            case "B":
                radioGroupCorrect.check(
                        R.id.radioB
                );
                break;

            case "C":
                radioGroupCorrect.check(
                        R.id.radioC
                );
                break;

            case "D":
                radioGroupCorrect.check(
                        R.id.radioD
                );
                break;

            case "E":
                radioGroupCorrect.check(
                        R.id.radioE
                );
                break;
        }
    }

    private void saveQuestion() {

        String question =
                etQuestion.getText()
                        .toString()
                        .trim();

        String optionA =
                etOptionA.getText()
                        .toString()
                        .trim();

        String optionB =
                etOptionB.getText()
                        .toString()
                        .trim();

        String optionC =
                etOptionC.getText()
                        .toString()
                        .trim();

        String optionD =
                etOptionD.getText()
                        .toString()
                        .trim();

        String optionE =
                etOptionE.getText()
                        .toString()
                        .trim();

        String explanation =
                etExplanation.getText()
                        .toString()
                        .trim();

        if (question.isEmpty()) {

            etQuestion.setError(
                    "Enter question"
            );

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

        String correctAnswer =
                getSelectedCorrectAnswer();

        if (correctAnswer == null) {

            Toast.makeText(
                    this,
                    "Select correct answer",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        UserQuestion userQuestion =
                new UserQuestion();

        userQuestion.setPracticeSetId(
                setId
        );

        userQuestion.setQuestionText(
                question
        );

        userQuestion.setOptionA(
                optionA
        );

        userQuestion.setOptionB(
                optionB
        );

        userQuestion.setOptionC(
                optionC
        );

        userQuestion.setOptionD(
                optionD
        );

        userQuestion.setOptionE(
                optionE
        );

        userQuestion.setCorrectAnswer(
                correctAnswer
        );

        userQuestion.setExplanation(
                explanation
        );

        new Thread(() -> {

            AppDatabase db =
                    AppDatabase.getInstance(
                            getApplicationContext()
                    );

            db.userQuestionDao()
                    .insertQuestion(
                            userQuestion
                    );

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

    private void updateQuestion() {

        String question =
                etQuestion.getText()
                        .toString()
                        .trim();

        String optionA =
                etOptionA.getText()
                        .toString()
                        .trim();

        String optionB =
                etOptionB.getText()
                        .toString()
                        .trim();

        String optionC =
                etOptionC.getText()
                        .toString()
                        .trim();

        String optionD =
                etOptionD.getText()
                        .toString()
                        .trim();

        String optionE =
                etOptionE.getText()
                        .toString()
                        .trim();

        String explanation =
                etExplanation.getText()
                        .toString()
                        .trim();

        if (question.isEmpty()) {

            etQuestion.setError(
                    "Enter question"
            );

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

        String correctAnswer =
                getSelectedCorrectAnswer();

        if (correctAnswer == null) {

            Toast.makeText(
                    this,
                    "Select correct answer",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        new Thread(() -> {

            AppDatabase db =
                    AppDatabase.getInstance(
                            getApplicationContext()
                    );

            UserQuestion userQuestion =
                    db.userQuestionDao()
                            .getQuestionById(
                                    questionId
                            );

            if (userQuestion == null) {

                runOnUiThread(() ->
                        Toast.makeText(
                                this,
                                "Question not found",
                                Toast.LENGTH_SHORT
                        ).show()
                );

                return;
            }

            userQuestion.setQuestionText(
                    question
            );

            userQuestion.setOptionA(
                    optionA
            );

            userQuestion.setOptionB(
                    optionB
            );

            userQuestion.setOptionC(
                    optionC
            );

            userQuestion.setOptionD(
                    optionD
            );

            userQuestion.setOptionE(
                    optionE
            );

            userQuestion.setCorrectAnswer(
                    correctAnswer
            );

            userQuestion.setExplanation(
                    explanation
            );

            db.userQuestionDao()
                    .updateQuestion(
                            userQuestion
                    );

            runOnUiThread(() -> {

                Toast.makeText(
                        this,
                        "Question updated successfully",
                        Toast.LENGTH_SHORT
                ).show();

                finish();
            });

        }).start();
    }

    private String getSelectedCorrectAnswer() {

        int selectedId =
                radioGroupCorrect
                        .getCheckedRadioButtonId();

        if (selectedId == -1) {
            return null;
        }

        if (selectedId == R.id.radioA) {
            return "A";
        }

        if (selectedId == R.id.radioB) {
            return "B";
        }

        if (selectedId == R.id.radioC) {
            return "C";
        }

        if (selectedId == R.id.radioD) {
            return "D";
        }

        if (selectedId == R.id.radioE) {
            return "E";
        }

        return null;
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

            AppDatabase db =
                    AppDatabase.getInstance(
                            getApplicationContext()
                    );

            int questionCount =
                    db.userQuestionDao()
                            .getQuestionsBySetId(
                                    setId
                            )
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