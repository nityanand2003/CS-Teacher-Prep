package com.example.csteacherprep.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csteacherprep.R;
import com.example.csteacherprep.activities.QuizActivity;
import com.example.csteacherprep.adapters.SetAdapter;
import com.example.csteacherprep.models.JsonSet;
import com.example.csteacherprep.utils.JsonHelper;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.List;

public class PracticeFragment extends Fragment {

    private RecyclerView recyclerView;
    private MaterialButtonToggleGroup practiceTypeToggle;

    private String selectedExam = "Bihar STET";

    public PracticeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_practice,
                container,
                false
        );

        // Exam Name
        TextView examName =
                view.findViewById(R.id.practiceExamName);

        if (getArguments() != null) {

            String argumentExam =
                    getArguments().getString("exam_name");

            if (argumentExam != null &&
                    !argumentExam.isEmpty()) {

                selectedExam = argumentExam;
            }
        }

        examName.setText(selectedExam);

        // RecyclerView
        recyclerView =
                view.findViewById(
                        R.id.practiceRecyclerView
                );

        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );

        // Toggle
        practiceTypeToggle =
                view.findViewById(
                        R.id.practiceTypeToggle
                );

        practiceTypeToggle.addOnButtonCheckedListener(
                (group, checkedId, isChecked) -> {

                    if (isChecked) {

                        if (checkedId == R.id.btnModelSets) {

                            loadModelSets();

                        } else if (checkedId ==
                                R.id.btnTopicWise) {

                            loadTopicWiseSets();
                        }
                    }
                }
        );

        // Default: Model Sets
        loadModelSets();

        return view;
    }

    /**
     * Load Model / Miscellaneous Practice Sets
     */
    private void loadModelSets() {

        List<String> setNames =
                new ArrayList<>();

        List<Integer> questionCounts =
                new ArrayList<>();

        List<Integer> resourceIds =
                new ArrayList<>();

        if (selectedExam.equals("Bihar STET")) {

            // Model Set 1
            addJsonSet(
                    R.raw.stet_practice_1,
                    setNames,
                    questionCounts,
                    resourceIds
            );

            // Model Set 2
            addJsonSet(
                    R.raw.stet_practice_2,
                    setNames,
                    questionCounts,
                    resourceIds
            );
        }

        setAdapter(
                setNames,
                questionCounts,
                resourceIds
        );
    }

    /**
     * Load Topic Wise Practice Sets
     */
    private void loadTopicWiseSets() {

        List<String> setNames =
                new ArrayList<>();

        List<Integer> questionCounts =
                new ArrayList<>();

        List<Integer> resourceIds =
                new ArrayList<>();

        if (selectedExam.equals("Bihar STET")) {

            // C Programming
            addJsonSet(
                    R.raw.stet_topic_c_programming,
                    setNames,
                    questionCounts,
                    resourceIds
            );

            // Data Structures
            addJsonSet(
                    R.raw.stet_topic_data_structures,
                    setNames,
                    questionCounts,
                    resourceIds
            );
        }

        setAdapter(
                setNames,
                questionCounts,
                resourceIds
        );
    }

    /**
     * Add JSON set information to lists
     */
    private void addJsonSet(
            int resourceId,
            List<String> setNames,
            List<Integer> questionCounts,
            List<Integer> resourceIds) {

        JsonSet set =
                JsonHelper.loadSet(
                        requireContext(),
                        resourceId
                );

        if (set.getSetName() != null &&
                !set.getSetName().isEmpty()) {

            setNames.add(
                    set.getSetName()
            );

            int count = 0;

            if (set.getQuestions() != null) {

                count =
                        set.getQuestions().size();
            }

            questionCounts.add(count);

            resourceIds.add(resourceId);
        }
    }

    /**
     * Set RecyclerView Adapter
     */
    private void setAdapter(
            List<String> setNames,
            List<Integer> questionCounts,
            List<Integer> resourceIds) {

        SetAdapter adapter =
                new SetAdapter(
                        setNames,
                        questionCounts,

                        setName -> {

                            int position =
                                    setNames.indexOf(
                                            setName
                                    );

                            if (position != -1) {

                                Intent intent =
                                        new Intent(
                                                requireContext(),
                                                QuizActivity.class
                                        );

                                intent.putExtra(
                                        "json_resource_id",
                                        resourceIds.get(position)
                                );

                                intent.putExtra(
                                        "set_name",
                                        setName
                                );

                                startActivity(intent);
                            }
                        }
                );

        recyclerView.setAdapter(adapter);
    }
}