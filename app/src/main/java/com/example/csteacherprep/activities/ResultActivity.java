package com.example.csteacherprep.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.csteacherprep.MainActivity;
import com.example.csteacherprep.R;

public class ResultActivity extends AppCompatActivity {

    private int total;
    private int correct;
    private int wrong;
    private int unattempted;
    private long timeTaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);

        // Get result data
        total = getIntent().getIntExtra(
                "total",
                0
        );

        correct = getIntent().getIntExtra(
                "correct",
                0
        );

        wrong = getIntent().getIntExtra(
                "wrong",
                0
        );

        unattempted = getIntent().getIntExtra(
                "unattempted",
                0
        );

        timeTaken = getIntent().getLongExtra(
                "time_taken",
                0
        );

        // Find views
        TextView tvScore =
                findViewById(R.id.tvScore);

        TextView tvTotal =
                findViewById(R.id.tvTotal);

        TextView tvCorrect =
                findViewById(R.id.tvCorrect);

        TextView tvWrong =
                findViewById(R.id.tvWrong);

        TextView tvUnattempted =
                findViewById(R.id.tvUnattempted);

        TextView tvTimeTaken =
                findViewById(R.id.tvTimeTaken);

        // Calculate percentage
        double percentage = 0;

        if (total > 0) {
            percentage =
                    (correct * 100.0) / total;
        }

        // Display result
        tvScore.setText(
                String.format(
                        "Score: %.2f%%",
                        percentage
                )
        );

        tvTotal.setText(
                "Total Questions: " + total
        );

        tvCorrect.setText(
                "Correct: " + correct
        );

        tvWrong.setText(
                "Wrong: " + wrong
        );

        tvUnattempted.setText(
                "Unattempted: " + unattempted
        );

        long totalSeconds = timeTaken / 1000;

        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        if (hours > 0) {

            tvTimeTaken.setText(
                    String.format(
                            "⏱ Time Taken: %02d hr %02d min %02d sec",
                            hours,
                            minutes,
                            seconds
                    )
            );

        } else {

            tvTimeTaken.setText(
                    String.format(
                            "⏱ Time Taken: %02d min %02d sec",
                            minutes,
                            seconds
                    )
            );
        }

        // Retry
        findViewById(R.id.btnRetry)
                .setOnClickListener(v -> retryQuiz());

        // Back to Main
        findViewById(R.id.btnBack)
                .setOnClickListener(v -> {

                    Intent intent = new Intent(
                            ResultActivity.this,
                            MainActivity.class
                    );

                    intent.addFlags(
                            Intent.FLAG_ACTIVITY_CLEAR_TOP
                    );

                    startActivity(intent);

                    finish();
                });
    }

    private void retryQuiz() {

        Intent intent = new Intent(
                ResultActivity.this,
                QuizActivity.class
        );

        // Topic Wise JSON Set
        if (getIntent().hasExtra("asset_path")) {

            String assetPath =
                    getIntent().getStringExtra(
                            "asset_path"
                    );

            intent.putExtra(
                    "asset_path",
                    assetPath
            );
        }

        // Self Designed Set
        else if (getIntent().hasExtra("set_id")) {

            int setId =
                    getIntent().getIntExtra(
                            "set_id",
                            -1
                    );

            intent.putExtra(
                    "set_id",
                    setId
            );
        }

        // PYQ / Practice Set
        else {

            int jsonResourceId =
                    getIntent().getIntExtra(
                            "json_resource_id",
                            R.raw.stet_pyq
                    );

            intent.putExtra(
                    "json_resource_id",
                    jsonResourceId
            );
        }

        startActivity(intent);

        finish();
    }
}