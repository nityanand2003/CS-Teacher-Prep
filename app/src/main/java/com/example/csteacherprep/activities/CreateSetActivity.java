package com.example.csteacherprep.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.csteacherprep.R;
import com.example.csteacherprep.database.AppDatabase;
import com.example.csteacherprep.models.PracticeSet;
import com.google.android.material.button.MaterialButton;

public class CreateSetActivity extends AppCompatActivity {

    private EditText etSetName;
    private EditText etDescription;

    private boolean editMode = false;
    private int setId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_set);

        etSetName = findViewById(R.id.etSetName);
        etDescription = findViewById(R.id.etDescription);

        editMode = getIntent().getBooleanExtra(
                "edit_mode",
                false
        );

        setId = getIntent().getIntExtra(
                "set_id",
                -1
        );

        if (editMode) {
            loadSetForEdit();
        }

        findViewById(R.id.btnCreateSet).setOnClickListener(v -> {

            if (editMode) {
                updateSet();
            } else {
                createSet();
            }
        });
    }

    private void createSet() {

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

        String exam = getIntent().getStringExtra(
                "exam_name"
        );

        if (exam == null || exam.isEmpty()) {
            exam = "Bihar STET";
        }

        String finalExam = exam;

        new Thread(() -> {

            AppDatabase db =
                    AppDatabase.getInstance(
                            getApplicationContext()
                    );

            PracticeSet practiceSet =
                    new PracticeSet(
                            setName,
                            finalExam,
                            description,
                            System.currentTimeMillis()
                    );

            long newSetId =
                    db.practiceSetDao()
                            .insertSet(practiceSet);

            runOnUiThread(() -> {

                Toast.makeText(
                        this,
                        "Set created successfully",
                        Toast.LENGTH_SHORT
                ).show();

                Intent intent =
                        new Intent(
                                CreateSetActivity.this,
                                AddQuestionActivity.class
                        );

                intent.putExtra(
                        "set_id",
                        (int) newSetId
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
    }

    private void loadSetForEdit() {

        if (setId == -1) {
            return;
        }

        new Thread(() -> {

            AppDatabase db =
                    AppDatabase.getInstance(
                            getApplicationContext()
                    );

            PracticeSet practiceSet =
                    db.practiceSetDao()
                            .getSetById(setId);

            runOnUiThread(() -> {

                if (practiceSet == null) {

                    Toast.makeText(
                            this,
                            "Set not found",
                            Toast.LENGTH_SHORT
                    ).show();

                    finish();
                    return;
                }

                etSetName.setText(
                        practiceSet.getName()
                );

                etDescription.setText(
                        practiceSet.getDescription()
                );

                MaterialButton btnCreateSet =
                        findViewById(R.id.btnCreateSet);

                btnCreateSet.setText("Update Set");
            });

        }).start();
    }

    private void updateSet() {

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

        new Thread(() -> {

            AppDatabase db =
                    AppDatabase.getInstance(
                            getApplicationContext()
                    );

            PracticeSet practiceSet =
                    db.practiceSetDao()
                            .getSetById(setId);

            if (practiceSet == null) {

                runOnUiThread(() ->
                        Toast.makeText(
                                this,
                                "Set not found",
                                Toast.LENGTH_SHORT
                        ).show()
                );

                return;
            }

            practiceSet.setName(setName);
            practiceSet.setDescription(description);

            db.practiceSetDao()
                    .updateSet(practiceSet);

            runOnUiThread(() -> {

                Toast.makeText(
                        this,
                        "Set updated successfully",
                        Toast.LENGTH_SHORT
                ).show();

                finish();
            });

        }).start();
    }
}