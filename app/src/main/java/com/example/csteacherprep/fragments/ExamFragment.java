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

        // Get selected exam name
        if (getArguments() != null) {

            String selectedExam =
                    getArguments().getString("exam_name");

            if (selectedExam != null) {
                examName.setText(selectedExam);
            }
        }

        return view;
    }
}