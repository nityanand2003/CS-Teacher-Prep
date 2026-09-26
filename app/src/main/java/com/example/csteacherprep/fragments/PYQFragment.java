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
import java.util.Arrays;
import java.util.List;

public class PYQFragment extends Fragment {

    public PYQFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_pyq,
                container,
                false
        );

        // =====================================================
        // Exam Name
        // =====================================================

        TextView examName =
                view.findViewById(R.id.pyqExamName);

        String selectedExam = "Bihar STET";

        if (getArguments() != null) {

            String argumentExam =
                    getArguments().getString("exam_name");

            if (argumentExam != null) {
                selectedExam = argumentExam;
            }
        }

        examName.setText(selectedExam);


        // =====================================================
        // RecyclerView
        // =====================================================

        RecyclerView recyclerView =
                view.findViewById(R.id.pyqRecyclerView);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );


        // =====================================================
        // Lists
        // =====================================================

        List<String> setNames =
                new ArrayList<>();

        List<Integer> questionCounts =
                new ArrayList<>();

        List<String> assetPaths =
                new ArrayList<>();


        // =====================================================
        // Select PYQ Asset Folder According to Exam
        // =====================================================

        String folderPath;

        if (selectedExam.equals("BPSC PGT")) {

            folderPath =
                    "bpsc_pgt/pyq";

        } else {

            folderPath =
                    "bihar_stet/pyq";
        }


        // =====================================================
        // Load JSON Files
        // =====================================================

        try {

            String[] files =
                    requireContext()
                            .getAssets()
                            .list(folderPath);

            if (files != null) {

                Arrays.sort(files);

                for (String fileName : files) {

                    if (!fileName
                            .toLowerCase()
                            .endsWith(".json")) {

                        continue;
                    }


                    String assetPath =
                            folderPath + "/" + fileName;


                    JsonSet jsonSet =
                            JsonHelper.loadAssetSet(
                                    requireContext(),
                                    assetPath
                            );


                    if (jsonSet == null) {
                        continue;
                    }


                    if (jsonSet.getSetName() == null
                            || jsonSet.getSetName()
                            .trim()
                            .isEmpty()) {

                        continue;
                    }


                    setNames.add(
                            jsonSet.getSetName()
                    );


                    int questionCount = 0;

                    if (jsonSet.getQuestions() != null) {

                        questionCount =
                                jsonSet.getQuestions().size();
                    }


                    questionCounts.add(
                            questionCount
                    );


                    assetPaths.add(
                            assetPath
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }


        // =====================================================
        // Adapter
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


                            if (position >= 0
                                    && position <
                                    assetPaths.size()) {

                                String assetPath =
                                        assetPaths.get(
                                                position
                                        );


                                Intent intent =
                                        new Intent(
                                                requireContext(),
                                                QuizActivity.class
                                        );


                                intent.putExtra(
                                        "asset_path",
                                        assetPath
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