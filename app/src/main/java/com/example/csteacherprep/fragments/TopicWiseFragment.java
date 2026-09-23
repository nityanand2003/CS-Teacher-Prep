package com.example.csteacherprep.fragments;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csteacherprep.R;
import com.example.csteacherprep.adapters.TopicAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TopicWiseFragment extends Fragment {

    private RecyclerView recyclerView;

    public TopicWiseFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_topic_wise,
                container,
                false
        );

        recyclerView =
                view.findViewById(R.id.topicRecyclerView);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );

        loadTopics();

        return view;
    }


    // =========================================================
    // Automatically load topics from assets/topic_wise/
    // =========================================================

    private void loadTopics() {

        List<String> topicNames =
                new ArrayList<>();

        List<String> topicIds =
                new ArrayList<>();

        try {

            AssetManager assetManager =
                    requireContext().getAssets();

            String[] folders =
                    assetManager.list("topic_wise");

            if (folders == null) {
                folders = new String[0];
            }

            // Alphabetical order
            Arrays.sort(folders);

            for (String folder : folders) {

                // topic_wise के अंदर जो भी entry है,
                // उसे topic folder माना जाएगा.
                String topicId = folder;

                String topicName =
                        convertToTopicName(folder);

                topicIds.add(topicId);
                topicNames.add(topicName);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }


        TopicAdapter adapter =
                new TopicAdapter(
                        topicNames,
                        topicIds,
                        (topicId, topicName) -> {

                            TopicSetsFragment fragment =
                                    new TopicSetsFragment();

                            Bundle bundle =
                                    new Bundle();

                            bundle.putString(
                                    "topic_id",
                                    topicId
                            );

                            bundle.putString(
                                    "topic_name",
                                    topicName
                            );

                            fragment.setArguments(bundle);

                            requireActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(
                                            R.id.main_container,
                                            fragment
                                    )
                                    .addToBackStack(null)
                                    .commit();
                        }
                );

        recyclerView.setAdapter(adapter);
    }


    // =========================================================
    // Folder name → Display name
    // =========================================================

    private String convertToTopicName(
            String folderName) {

        switch (folderName) {

            case "c_programming":
                return "C Programming";

            case "cpp":
                return "C++";

            case "java":
                return "Java";

            case "python":
                return "Python";

            case "javascript":
                return "JavaScript";

            case "data_structures":
                return "Data Structures";

            case "algorithms":
                return "Algorithms";

            case "dbms":
                return "DBMS";

            case "operating_system":
                return "Operating System";

            case "computer_networks":
                return "Computer Networks";

            case "coa":
                return "Computer Organization & Architecture";

            case "software_engineering":
                return "Software Engineering";

            case "web_technology":
                return "Web Technology";

            case "computer_graphics":
                return "Computer Graphics";

            case "artificial_intelligence":
                return "Artificial Intelligence";

            case "machine_learning":
                return "Machine Learning";

            case "cyber_security":
                return "Cyber Security";

            case "theory_of_computation":
                return "Theory of Computation";

            default:
                return formatFolderName(folderName);
        }
    }


    // =========================================================
    // Unknown/new folder का नाम automatically format करना
    // =========================================================

    private String formatFolderName(
            String folderName) {

        String[] words =
                folderName.split("_");

        StringBuilder result =
                new StringBuilder();

        for (String word : words) {

            if (word.isEmpty()) {
                continue;
            }

            result.append(
                    Character.toUpperCase(
                            word.charAt(0)
                    )
            );

            if (word.length() > 1) {

                result.append(
                        word.substring(1)
                );
            }

            result.append(" ");
        }

        return result.toString().trim();
    }
}