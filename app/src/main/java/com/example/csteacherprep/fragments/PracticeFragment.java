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

import java.util.ArrayList;
import java.util.List;

public class PracticeFragment extends Fragment {

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

        TextView examName =
                view.findViewById(R.id.practiceExamName);

        String selectedExam = "Bihar STET";

        if (getArguments() != null) {

            String argumentExam =
                    getArguments().getString("exam_name");

            if (argumentExam != null) {
                selectedExam = argumentExam;
            }
        }

        examName.setText(selectedExam);

        RecyclerView recyclerView =
                view.findViewById(
                        R.id.practiceRecyclerView
                );

        recyclerView.setLayoutManager(
                new LinearLayoutManager(
                        requireContext()
                )
        );

        // =====================================================
        // Practice Sets
        // =====================================================

        List<String> setNames =
                new ArrayList<>();

        List<Integer> questionCounts =
                new ArrayList<>();

        List<String> assetPaths =
                new ArrayList<>();


        // =====================================================
        // Bihar STET Model Sets
        // =====================================================

        if (selectedExam.equals("Bihar STET")) {

            // -------------------------------------------------
            // Model Set 1
            // -------------------------------------------------

            String assetPath1 =
                    "bihar_stet/model_set/stet_model_set_1.json";

            JsonSet set1 =
                    JsonHelper.loadAssetSet(
                            requireContext(),
                            assetPath1
                    );

            if (set1 != null &&
                    set1.getSetName() != null &&
                    !set1.getSetName().isEmpty()) {

                setNames.add(
                        set1.getSetName()
                );

                int count = 0;

                if (set1.getQuestions() != null) {

                    count =
                            set1.getQuestions().size();
                }

                questionCounts.add(count);

                assetPaths.add(
                        assetPath1
                );
            }


            // -------------------------------------------------
            // Model Set 2
            // -------------------------------------------------

            String assetPath2 =
                    "bihar_stet/model_set/stet_model_set_2.json";

            JsonSet set2 =
                    JsonHelper.loadAssetSet(
                            requireContext(),
                            assetPath2
                    );

            if (set2 != null &&
                    set2.getSetName() != null &&
                    !set2.getSetName().isEmpty()) {

                setNames.add(
                        set2.getSetName()
                );

                int count = 0;

                if (set2.getQuestions() != null) {

                    count =
                            set2.getQuestions().size();
                }

                questionCounts.add(count);

                assetPaths.add(
                        assetPath2
                );
            }
        }


        // =====================================================
        // Set Adapter
        // =====================================================

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
                                        "asset_path",
                                        assetPaths.get(
                                                position
                                        )
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

        return view;
    }
}