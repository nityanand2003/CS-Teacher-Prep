package com.example.csteacherprep.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import com.example.csteacherprep.activities.QuizActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csteacherprep.R;
import com.example.csteacherprep.adapters.SetAdapter;
import com.example.csteacherprep.models.JsonSet;
import com.example.csteacherprep.utils.JsonHelper;

import java.util.ArrayList;
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

        // Exam name
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


        // RecyclerView
        RecyclerView recyclerView =
                view.findViewById(R.id.pyqRecyclerView);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );


        // Load JSON Set
        List<String> setNames = new ArrayList<>();
        List<Integer> questionCounts = new ArrayList<>();


        JsonSet jsonSet = JsonHelper.loadSet(
                requireContext(),
                R.raw.stet_pyq
        );

        if (jsonSet.getSetName() != null
                && !jsonSet.getSetName().isEmpty()) {

            setNames.add(jsonSet.getSetName());

            int questionCount = 0;

            if (jsonSet.getQuestions() != null) {
                questionCount = jsonSet.getQuestions().size();
            }

            questionCounts.add(questionCount);
        }


        // Adapter
        SetAdapter adapter = new SetAdapter(
                setNames,
                questionCounts,
                setName -> {

                    Intent intent = new Intent(
                            requireContext(),
                            QuizActivity.class
                    );

                    intent.putExtra("json_resource_id", R.raw.stet_pyq);
                    intent.putExtra("set_name", setName);

                    startActivity(intent);
                }
        );

        recyclerView.setAdapter(adapter);


        return view;
    }
}