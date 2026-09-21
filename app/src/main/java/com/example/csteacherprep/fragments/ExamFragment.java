package com.example.csteacherprep.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.csteacherprep.R;

public class ExamFragment extends Fragment {

    public ExamFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_exam,
                container,
                false
        );

        TextView examName = view.findViewById(R.id.examName);

        // Get selected exam
        String exam = "Bihar STET";

        if (getArguments() != null) {
            String argumentExam =
                    getArguments().getString("exam_name");

            if (argumentExam != null) {
                exam = argumentExam;
            }
        }

        // Final value for lambda expressions
        final String selectedExam = exam;

        examName.setText(selectedExam);


        // -----------------------------
        // PYQ
        // -----------------------------

        view.findViewById(R.id.btnPyq).setOnClickListener(v -> {

            PYQFragment fragment = new PYQFragment();

            Bundle bundle = new Bundle();
            bundle.putString("exam_name", selectedExam);

            fragment.setArguments(bundle);

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });


        // -----------------------------
        // Practice Sets
        // -----------------------------

        view.findViewById(R.id.btnPractice).setOnClickListener(v -> {

            PracticeFragment fragment = new PracticeFragment();

            Bundle bundle = new Bundle();
            bundle.putString("exam_name", selectedExam);

            fragment.setArguments(bundle);

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });


        // -----------------------------
        // Self Designed Sets
        // -----------------------------

        view.findViewById(R.id.btnSelfDesigned).setOnClickListener(v -> {

            SelfDesignedFragment fragment = new SelfDesignedFragment();

            Bundle bundle = new Bundle();
            bundle.putString("exam_name", selectedExam);

            fragment.setArguments(bundle);

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}