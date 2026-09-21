package com.example.csteacherprep.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csteacherprep.R;
import com.example.csteacherprep.activities.CreateSetActivity;
import com.example.csteacherprep.adapters.SetAdapter;
import com.example.csteacherprep.database.AppDatabase;
import com.example.csteacherprep.models.PracticeSet;

import java.util.ArrayList;
import java.util.List;

public class SelfDesignedFragment extends Fragment {

    private RecyclerView recyclerView;
    private SetAdapter adapter;

    private final List<String> setNames = new ArrayList<>();
    private final List<Integer> questionCounts = new ArrayList<>();
    private final List<Integer> setIds = new ArrayList<>();

    public SelfDesignedFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_self_designed,
                container,
                false
        );

        TextView examName =
                view.findViewById(R.id.selfDesignedExamName);

        String selectedExam = "Bihar STET";

        if (getArguments() != null) {

            String argumentExam =
                    getArguments().getString("exam_name");

            if (argumentExam != null) {
                selectedExam = argumentExam;
            }
        }

        examName.setText(selectedExam);

        recyclerView =
                view.findViewById(R.id.selfDesignedRecyclerView);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );

        String finalExam = selectedExam;

        adapter = new SetAdapter(

                setNames,
                questionCounts,

                // Open set
                setName -> {

                    int position = setNames.indexOf(setName);

                    if (position != -1) {

                        int selectedSetId =
                                setIds.get(position);

                        Intent intent = new Intent(
                                requireContext(),
                                com.example.csteacherprep.activities.QuizActivity.class
                        );

                        intent.putExtra(
                                "set_id",
                                selectedSetId
                        );

                        startActivity(intent);
                    }
                },

                // Delete set
                this::showDeleteConfirmation
        );

        recyclerView.setAdapter(adapter);

        // Create New Set
        view.findViewById(R.id.btnCreateNewSet)
                .setOnClickListener(v -> {

                    Intent intent = new Intent(
                            requireContext(),
                            CreateSetActivity.class
                    );

                    intent.putExtra(
                            "exam_name",
                            finalExam
                    );

                    startActivity(intent);
                });

        loadSets(finalExam);

        return view;
    }

    @Override
    public void onResume() {

        super.onResume();

        if (getView() != null) {

            String exam = "Bihar STET";

            if (getArguments() != null) {

                String argumentExam =
                        getArguments().getString("exam_name");

                if (argumentExam != null) {
                    exam = argumentExam;
                }
            }

            loadSets(exam);
        }
    }

    private void loadSets(String exam) {

        new Thread(() -> {

            AppDatabase db =
                    AppDatabase.getInstance(
                            requireContext()
                                    .getApplicationContext()
                    );

            List<PracticeSet> allSets =
                    db.practiceSetDao().getAllSets();

            setNames.clear();
            questionCounts.clear();
            setIds.clear();

            for (PracticeSet set : allSets) {

                if (exam.equals(set.getExam())) {

                    setIds.add(set.getId());

                    setNames.add(set.getName());

                    int count =
                            db.userQuestionDao()
                                    .getQuestionsBySetId(
                                            set.getId()
                                    )
                                    .size();

                    questionCounts.add(count);
                }
            }

            requireActivity().runOnUiThread(() -> {

                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            });

        }).start();
    }

    private void showDeleteConfirmation(String setName) {

        int position = setNames.indexOf(setName);

        if (position == -1) {
            return;
        }

        int setId = setIds.get(position);

        new AlertDialog.Builder(requireContext())
                .setTitle("Delete Set?")
                .setMessage(
                        "Are you sure you want to delete \"" +
                                setName +
                                "\"?\n\nAll questions in this set will also be deleted."
                )
                .setNegativeButton(
                        "Cancel",
                        null
                )
                .setPositiveButton(
                        "Delete",
                        (dialog, which) ->
                                deleteSet(setId, setName)
                )
                .show();
    }

    private void deleteSet(int setId, String setName) {

        new Thread(() -> {

            AppDatabase db =
                    AppDatabase.getInstance(
                            requireContext()
                                    .getApplicationContext()
                    );

            // First delete all questions
            db.userQuestionDao()
                    .deleteQuestionsBySetId(setId);

            // Then delete the set
            PracticeSet practiceSet =
                    db.practiceSetDao()
                            .getSetById(setId);

            if (practiceSet != null) {

                db.practiceSetDao()
                        .deleteSet(practiceSet);
            }

            requireActivity().runOnUiThread(() -> {

                Toast.makeText(
                        requireContext(),
                        "Set deleted successfully",
                        Toast.LENGTH_SHORT
                ).show();

                String exam = "Bihar STET";

                if (getArguments() != null) {

                    String argumentExam =
                            getArguments()
                                    .getString("exam_name");

                    if (argumentExam != null) {
                        exam = argumentExam;
                    }
                }

                loadSets(exam);
            });

        }).start();
    }
}