package com.example.csteacherprep.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.csteacherprep.R;
import com.example.csteacherprep.models.Question;
import com.example.csteacherprep.utils.JsonHelper;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Load questions from JSON
        questionList = JsonHelper.loadQuestions(
                this,
                R.raw.stet_pyq
        );

        TextView questionNumber = findViewById(R.id.questionNumber);
        TextView questionText = findViewById(R.id.questionText);

        TextView optionA = findViewById(R.id.optionA);
        TextView optionB = findViewById(R.id.optionB);
        TextView optionC = findViewById(R.id.optionC);
        TextView optionD = findViewById(R.id.optionD);
        TextView optionE = findViewById(R.id.optionE);

        if (!questionList.isEmpty()) {

            Question question = questionList.get(0);

            questionNumber.setText("Question 1 of " + questionList.size());

            questionText.setText(question.getQuestionText());

            optionA.setText("A. " + question.getOptionA());
            optionB.setText("B. " + question.getOptionB());
            optionC.setText("C. " + question.getOptionC());
            optionD.setText("D. " + question.getOptionD());
            optionE.setText("E. " + question.getOptionE());

        } else {
            questionNumber.setText("No questions");
            questionText.setText("No questions available.");
        }
    }
}