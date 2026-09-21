package com.example.csteacherprep.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.csteacherprep.R;
import com.example.csteacherprep.database.AppDatabase;
import com.example.csteacherprep.models.PracticeSet;

public class CreateSetActivity extends AppCompatActivity {

    private EditText etSetName;
    private EditText etDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_set);

        etSetName = findViewById(R.id.etSetName);
        etDescription = findViewById(R.id.etDescription);

        findViewById(R.id.btnCreateSet).setOnClickListener(v -> {

            String setName = etSetName.getText()
                    .toString()
                    .trim();

            String description = etDescription.getText()
                    .toString()
                    .trim();

            if (setName.isEmpty()) {
                etSetName.setError("Enter set name");
                return;
            }

            String exam = getIntent().getStringExtra("exam_name");

            if (exam == null || exam.isEmpty()) {
                exam = "Bihar STET";
            }

            String finalExam = exam;

            new Thread(() -> {

                AppDatabase db = AppDatabase.getInstance(
                        getApplicationContext()
                );

                PracticeSet practiceSet = new PracticeSet(
                        setName,
                        finalExam,
                        description,
                        System.currentTimeMillis()
                );

                long setId = db.practiceSetDao()
                        .insertSet(practiceSet);

                runOnUiThread(() -> {

                    Toast.makeText(
                            this,
                            "Set created successfully",
                            Toast.LENGTH_SHORT
                    ).show();

                    Intent intent = new Intent(
                            CreateSetActivity.this,
                            AddQuestionActivity.class
                    );

                    intent.putExtra(
                            "set_id",
                            (int) setId
                    );

                    intent.putExtra(
                            "exam_name",
                            finalExam
                    );

                    intent.putExtra(
                            "set_name",
                            setName
                    );

                    startActivity(intent);

                    finish();
                });

            }).start();
        });
    }
}