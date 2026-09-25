package com.example.csteacherprep.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.csteacherprep.R;
import com.example.csteacherprep.database.AppDatabase;
import com.example.csteacherprep.models.Question;
import com.example.csteacherprep.models.UserQuestion;
import com.example.csteacherprep.utils.JsonHelper;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private List<Question> questionList =
            new ArrayList<>();

    private int currentQuestionIndex = 0;

    private int correctCount = 0;

    private int wrongCount = 0;

    private int unattemptedCount = 0;

    private String assetPath = null;

    private TextView questionNumber;
    private TextView questionText;

    private TextView optionA;
    private TextView optionB;
    private TextView optionC;
    private TextView optionD;
    private TextView optionE;

    private TextView explanation;

    // Timer
    private TextView timerText;

    private Handler timerHandler =
            new Handler(Looper.getMainLooper());

    private long startTime;

    private boolean timerRunning = false;

    private final Runnable timerRunnable =
            new Runnable() {

                @Override
                public void run() {

                    if (!timerRunning) {
                        return;
                    }

                    long elapsedTime =
                            System.currentTimeMillis() - startTime;

                    updateTimer(elapsedTime);

                    timerHandler.postDelayed(
                            this,
                            1000
                    );
                }
            };

    private View selectedOption;

    private boolean isSelfDesigned = false;

    private int selfDesignedSetId = -1;


    @Override
    protected void onCreate(
            Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_quiz
        );

        questionNumber =
                findViewById(
                        R.id.questionNumber
                );

        questionText =
                findViewById(
                        R.id.questionText
                );

        optionA =
                findViewById(
                        R.id.optionA
                );

        optionB =
                findViewById(
                        R.id.optionB
                );

        optionC =
                findViewById(
                        R.id.optionC
                );

        optionD =
                findViewById(
                        R.id.optionD
                );

        optionE =
                findViewById(
                        R.id.optionE
                );

        explanation =
                findViewById(
                        R.id.explanation
                );

        timerText =
                findViewById(
                        R.id.timerText
                );

        // Start timer
        startTimer();


        findViewById(
                R.id.btnNext
        ).setOnClickListener(
                v -> nextQuestion()
        );

        findViewById(
                R.id.btnSubmit
        ).setOnClickListener(
                v -> submitQuiz()
        );


        optionA.setOnClickListener(
                v -> selectOption(
                        optionA,
                        "A"
                )
        );

        optionB.setOnClickListener(
                v -> selectOption(
                        optionB,
                        "B"
                )
        );

        optionC.setOnClickListener(
                v -> selectOption(
                        optionC,
                        "C"
                )
        );

        optionD.setOnClickListener(
                v -> selectOption(
                        optionD,
                        "D"
                )
        );

        optionE.setOnClickListener(
                v -> selectOption(
                        optionE,
                        "E"
                )
        );


        // =====================================================
        // Self Designed Set
        // =====================================================

        if (getIntent().hasExtra("set_id")) {

            isSelfDesigned = true;

            selfDesignedSetId =
                    getIntent().getIntExtra(
                            "set_id",
                            -1
                    );

            loadSelfDesignedQuestions();

        }

        // =====================================================
        // Topic Wise JSON Set
        // =====================================================

        else if (
                getIntent().hasExtra(
                        "asset_path"
                )
        ) {

            assetPath =
                    getIntent().getStringExtra(
                            "asset_path"
                    );

            questionList =
                    JsonHelper.loadAssetQuestions(
                            this,
                            assetPath
                    );

            showInitialQuestion();
        }
    }


    // =========================================================
    // Set Timer
    // =========================================================

    private void startTimer() {

        startTime =
                System.currentTimeMillis();

        timerRunning = true;

        timerHandler.post(
                timerRunnable
        );
    }


    private void updateTimer(
            long elapsedTime) {

        long totalSeconds =
                elapsedTime / 1000;

        long hours =
                totalSeconds / 3600;

        long minutes =
                (totalSeconds % 3600) / 60;

        long seconds =
                totalSeconds % 60;


        if (hours > 0) {

            timerText.setText(
                    String.format(
                            "⏱ %02d:%02d:%02d",
                            hours,
                            minutes,
                            seconds
                    )
            );

        } else {

            timerText.setText(
                    String.format(
                            "⏱ %02d:%02d",
                            minutes,
                            seconds
                    )
            );
        }
    }


    // =========================================================
    // Stop Timer when Activity is destroyed
    // =========================================================

    @Override
    protected void onDestroy() {

        timerRunning = false;

        timerHandler.removeCallbacks(
                timerRunnable
        );

        super.onDestroy();
    }


    // =========================================================
    // Load Self Designed Questions
    // =========================================================

    private void loadSelfDesignedQuestions() {

        new Thread(() -> {

            AppDatabase db =
                    AppDatabase.getInstance(
                            getApplicationContext()
                    );

            List<UserQuestion> userQuestions =
                    db.userQuestionDao()
                            .getQuestionsBySetId(
                                    selfDesignedSetId
                            );

            List<Question> convertedQuestions =
                    new ArrayList<>();

            for (
                    UserQuestion userQuestion :
                    userQuestions
            ) {

                Question question =
                        new Question(
                                userQuestion.getId(),
                                userQuestion.getQuestionText(),
                                userQuestion.getOptionA(),
                                userQuestion.getOptionB(),
                                userQuestion.getOptionC(),
                                userQuestion.getOptionD(),
                                userQuestion.getOptionE(),
                                userQuestion.getCorrectAnswer(),
                                userQuestion.getExplanation()
                        );

                convertedQuestions.add(
                        question
                );
            }

            runOnUiThread(() -> {

                questionList =
                        convertedQuestions;

                showInitialQuestion();
            });

        }).start();
    }


    // =========================================================
    // Initial Question
    // =========================================================

    private void showInitialQuestion() {

        if (!questionList.isEmpty()) {

            showQuestion();

        } else {

            questionNumber.setText(
                    "No questions"
            );

            questionText.setText(
                    "No questions available."
            );

            findViewById(
                    R.id.btnNext
            ).setVisibility(
                    View.GONE
            );

            findViewById(
                    R.id.btnSubmit
            ).setVisibility(
                    View.GONE
            );
        }
    }


    // =========================================================
    // Show Question
    // =========================================================

    private void showQuestion() {

        Question question =
                questionList.get(
                        currentQuestionIndex
                );

        questionNumber.setText(
                "Question " +
                        (currentQuestionIndex + 1) +
                        " of " +
                        questionList.size()
        );

        questionText.setText(
                question.getQuestionText()
        );

        optionA.setText(
                "A. " + question.getOptionA()
        );

        optionB.setText(
                "B. " + question.getOptionB()
        );

        optionC.setText(
                "C. " + question.getOptionC()
        );

        optionD.setText(
                "D. " + question.getOptionD()
        );

        resetOptions();

        String optionEText =
                question.getOptionE();

        if (
                optionEText == null ||
                        optionEText.trim().isEmpty()
        ) {

            optionE.setVisibility(
                    View.GONE
            );

        } else {

            optionE.setVisibility(
                    View.VISIBLE
            );

            optionE.setText(
                    "E. " + optionEText
            );
        }

        explanation.setVisibility(
                View.GONE
        );

        explanation.setText("");

        selectedOption = null;


        // Last question

        if (
                currentQuestionIndex ==
                        questionList.size() - 1
        ) {

            findViewById(
                    R.id.btnNext
            ).setVisibility(
                    View.GONE
            );

            findViewById(
                    R.id.btnSubmit
            ).setVisibility(
                    View.VISIBLE
            );

        } else {

            findViewById(
                    R.id.btnNext
            ).setVisibility(
                    View.VISIBLE
            );

            findViewById(
                    R.id.btnSubmit
            ).setVisibility(
                    View.GONE
            );
        }
    }


    // =========================================================
    // Select Option
    // =========================================================

    private void selectOption(
            TextView option,
            String selectedAnswer) {

        if (selectedOption != null) {
            return;
        }

        selectedOption = option;

        Question question =
                questionList.get(
                        currentQuestionIndex
                );

        String correctAnswer =
                question.getCorrectAnswer();

        if (
                selectedAnswer.equalsIgnoreCase(
                        correctAnswer
                )
        ) {

            option.setBackgroundColor(
                    Color.parseColor(
                            "#E8F5E9"
                    )
            );

            option.setTextColor(
                    Color.parseColor(
                            "#2E7D32"
                    )
            );

            correctCount++;

        } else {

            option.setBackgroundColor(
                    Color.parseColor(
                            "#FFEBEE"
                    )
            );

            option.setTextColor(
                    Color.parseColor(
                            "#C62828"
                    )
            );

            wrongCount++;

            showCorrectAnswer(
                    correctAnswer
            );
        }

        String explanationText =
                question.getExplanation();

        if (
                explanationText != null &&
                        !explanationText
                                .trim()
                                .isEmpty()
        ) {

            explanation.setText(
                    "Explanation: " +
                            explanationText
            );

            explanation.setVisibility(
                    View.VISIBLE
            );
        }
    }


    // =========================================================
    // Show Correct Answer
    // =========================================================

    private void showCorrectAnswer(
            String correctAnswer) {

        TextView correctOption = null;

        switch (correctAnswer) {

            case "A":
                correctOption = optionA;
                break;

            case "B":
                correctOption = optionB;
                break;

            case "C":
                correctOption = optionC;
                break;

            case "D":
                correctOption = optionD;
                break;

            case "E":
                correctOption = optionE;
                break;
        }

        if (correctOption != null) {

            correctOption.setBackgroundColor(
                    Color.parseColor(
                            "#E8F5E9"
                    )
            );

            correctOption.setTextColor(
                    Color.parseColor(
                            "#2E7D32"
                    )
            );
        }
    }


    // =========================================================
    // Reset Options
    // =========================================================

    private void resetOptions() {

        TextView[] options = {
                optionA,
                optionB,
                optionC,
                optionD,
                optionE
        };

        for (TextView option : options) {

            option.setBackgroundResource(
                    R.drawable.bg_card
            );

            option.setTextColor(
                    Color.parseColor(
                            "#1F2937"
                    )
            );

            option.setVisibility(
                    View.VISIBLE
            );
        }
    }


    // =========================================================
    // Next Question
    // =========================================================

    private void nextQuestion() {

        if (selectedOption == null) {

            unattemptedCount++;
        }

        if (
                currentQuestionIndex <
                        questionList.size() - 1
        ) {

            currentQuestionIndex++;

            showQuestion();
        }
    }


    // =========================================================
    // Submit Quiz
    // =========================================================

    private void submitQuiz() {

        if (questionList.isEmpty()) {
            return;
        }

        // Stop timer
        timerRunning = false;

        timerHandler.removeCallbacks(
                timerRunnable
        );

        long timeTaken =
                System.currentTimeMillis() -
                        startTime;


        if (selectedOption == null) {

            unattemptedCount++;
        }


        Intent intent =
                new Intent(
                        this,
                        ResultActivity.class
                );

        intent.putExtra(
                "total",
                questionList.size()
        );

        intent.putExtra(
                "correct",
                correctCount
        );

        intent.putExtra(
                "wrong",
                wrongCount
        );

        intent.putExtra(
                "unattempted",
                unattemptedCount
        );

        // Send time to ResultActivity
        intent.putExtra(
                "time_taken",
                timeTaken
        );


        // Self Designed

        if (isSelfDesigned) {

            intent.putExtra(
                    "set_id",
                    selfDesignedSetId
            );

        }

        // Topic Wise JSON Set

        else if (
                assetPath != null &&
                        !assetPath.isEmpty()
        ) {

            intent.putExtra(
                    "asset_path",
                    assetPath
            );

        }
        startActivity(intent);

        finish();
    }
}