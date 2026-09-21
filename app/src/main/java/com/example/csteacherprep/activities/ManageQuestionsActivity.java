package com.example.csteacherprep.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csteacherprep.R;
import com.example.csteacherprep.adapters.QuestionAdapter;
import com.example.csteacherprep.database.AppDatabase;
import com.example.csteacherprep.models.UserQuestion;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;

public class ManageQuestionsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvSetName;
    private TextView tvQuestionCount;
    private TextView tvNoQuestions;

    private QuestionAdapter adapter;

    private final List<UserQuestion> questions =
            new ArrayList<>();

    private int setId = -1;
    private String setName = "Practice Set";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_manage_questions);

        setId = getIntent().getIntExtra(
                "set_id",
                -1
        );

        setName = getIntent().getStringExtra(
                "set_name"
        );

        if (setName == null || setName.isEmpty()) {
            setName = "Practice Set";
        }

        tvSetName =
                findViewById(R.id.tvManageSetName);

        tvQuestionCount =
                findViewById(R.id.tvManageQuestionCount);

        tvNoQuestions =
                findViewById(R.id.tvNoQuestions);

        recyclerView =
                findViewById(
                        R.id.manageQuestionsRecyclerView
                );

        tvSetName.setText(setName);

        findViewById(R.id.manageQuestionsToolbar)
                .setOnClickListener(v -> finish());

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        adapter = new QuestionAdapter(
                questions,

                // Question click
                question -> {
                    Toast.makeText(
                            this,
                            "Question selected",
                            Toast.LENGTH_SHORT
                    ).show();
                },

                // Edit question
                this::editQuestion,

                // Delete question
                this::showDeleteConfirmation
        );

        recyclerView.setAdapter(adapter);

        loadQuestions();

        findViewById(R.id.btnAddQuestion)
                .setOnClickListener(v -> {

                    Intent intent = new Intent(
                            ManageQuestionsActivity.this,
                            AddQuestionActivity.class
                    );

                    intent.putExtra(
                            "set_id",
                            setId
                    );

                    intent.putExtra(
                            "set_name",
                            setName
                    );

                    startActivity(intent);
                });
    }
    @Override
    protected void onResume() {
        super.onResume();

        if (setId != -1) {
            loadQuestions();
        }
    }
    private void loadQuestions() {

        if (setId == -1) {
            return;
        }

        new Thread(() -> {

            AppDatabase db =
                    AppDatabase.getInstance(
                            getApplicationContext()
                    );

            List<UserQuestion> result =
                    db.userQuestionDao()
                            .getQuestionsBySetId(setId);

            runOnUiThread(() -> {

                questions.clear();
                questions.addAll(result);

                adapter.notifyDataSetChanged();

                tvQuestionCount.setText(
                        "Questions: " + questions.size()
                );

                if (questions.isEmpty()) {

                    tvNoQuestions.setVisibility(
                            TextView.VISIBLE
                    );

                    recyclerView.setVisibility(
                            RecyclerView.GONE
                    );

                } else {

                    tvNoQuestions.setVisibility(
                            TextView.GONE
                    );

                    recyclerView.setVisibility(
                            RecyclerView.VISIBLE
                    );
                }
            });

        }).start();
    }

    private void showDeleteConfirmation(
            UserQuestion question) {

        new AlertDialog.Builder(this)
                .setTitle("Delete Question?")
                .setMessage(
                        "Are you sure you want to delete this question?"
                )
                .setNegativeButton(
                        "Cancel",
                        null
                )
                .setPositiveButton(
                        "Delete",
                        (dialog, which) ->
                                deleteQuestion(question)
                )
                .show();
    }

    private void editQuestion(UserQuestion question) {

        Intent intent = new Intent(
                ManageQuestionsActivity.this,
                AddQuestionActivity.class
        );

        intent.putExtra(
                "edit_mode",
                true
        );

        intent.putExtra(
                "question_id",
                question.getId()
        );

        intent.putExtra(
                "set_id",
                setId
        );

        intent.putExtra(
                "set_name",
                setName
        );

        startActivity(intent);
    }
    private void deleteQuestion(
            UserQuestion question) {

        new Thread(() -> {

            AppDatabase db =
                    AppDatabase.getInstance(
                            getApplicationContext()
                    );

            db.userQuestionDao()
                    .deleteQuestion(question);

            runOnUiThread(() -> {

                Toast.makeText(
                        this,
                        "Question deleted successfully",
                        Toast.LENGTH_SHORT
                ).show();

                loadQuestions();
            });

        }).start();
    }
}