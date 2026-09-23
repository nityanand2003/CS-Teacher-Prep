package com.example.csteacherprep.fragments;

import android.content.Intent;
import android.content.res.AssetManager;
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

public class TopicSetsFragment extends Fragment {

    private RecyclerView recyclerView;

    private String topicId;
    private String topicName;

    public TopicSetsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_topic_sets,
                container,
                false
        );

        recyclerView =
                view.findViewById(
                        R.id.topicSetRecyclerView
                );

        TextView tvTopicTitle =
                view.findViewById(
                        R.id.tvTopicTitle
                );

        TextView tvTopicSubtitle =
                view.findViewById(
                        R.id.tvTopicSubtitle
                );

        recyclerView.setLayoutManager(
                new LinearLayoutManager(
                        requireContext()
                )
        );

        // Get selected topic
        Bundle arguments = getArguments();

        if (arguments != null) {

            topicId =
                    arguments.getString(
                            "topic_id",
                            ""
                    );

            topicName =
                    arguments.getString(
                            "topic_name",
                            ""
                    );
        }

        tvTopicTitle.setText(topicName);

        tvTopicSubtitle.setText(
                "Choose a " +
                        topicName +
                        " practice set"
        );

        loadTopicSets();

        return view;
    }

    // =========================================================
    // Load JSON files from selected topic folder
    // =========================================================

    private void loadTopicSets() {

        List<String> setNames =
                new ArrayList<>();

        List<Integer> questionCounts =
                new ArrayList<>();

        // JSON file paths
        List<String> assetPaths =
                new ArrayList<>();

        try {

            AssetManager assetManager =
                    requireContext()
                            .getAssets();

            String folderPath =
                    "topic_wise/" + topicId;

            String[] files =
                    assetManager.list(
                            folderPath
                    );

            if (files == null) {

                files = new String[0];
            }

            // Only JSON files
            List<String> jsonFiles =
                    new ArrayList<>();

            for (String file : files) {

                if (file
                        .toLowerCase()
                        .endsWith(".json")) {

                    jsonFiles.add(file);
                }
            }

            // Sort according to JSON "order"
            jsonFiles.sort((file1, file2) -> {

                String path1 =
                        folderPath + "/" + file1;

                String path2 =
                        folderPath + "/" + file2;

                JsonSet set1 =
                        JsonHelper.loadAssetSet(
                                requireContext(),
                                path1
                        );

                JsonSet set2 =
                        JsonHelper.loadAssetSet(
                                requireContext(),
                                path2
                        );

                return Integer.compare(
                        set1.getOrder(),
                        set2.getOrder()
                );
            });

// Read every JSON file
            for (String file : jsonFiles) {

                String assetPath =
                        folderPath +
                                "/" +
                                file;

                JsonSet jsonSet =
                        JsonHelper.loadAssetSet(
                                requireContext(),
                                assetPath
                        );

                String setName =
                        jsonSet.getSetName();

                // If setName is missing,
                // use JSON filename
                if (setName == null ||
                        setName.trim().isEmpty()) {

                    setName =
                            convertFileNameToSetName(
                                    file
                            );
                }

                int questionCount = 0;

                if (jsonSet.getQuestions() != null) {

                    questionCount =
                            jsonSet
                                    .getQuestions()
                                    .size();
                }

                setNames.add(setName);

                questionCounts.add(
                        questionCount
                );

                assetPaths.add(
                        assetPath
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        // =====================================================
        // Set Adapter
        // =====================================================

        SetAdapter adapter =
                new SetAdapter(
                        setNames,
                        questionCounts,

                        // Set clicked
                        setName -> {

                            int position =
                                    setNames.indexOf(
                                            setName
                                    );

                            if (position == -1) {
                                return;
                            }

                            String assetPath =
                                    assetPaths.get(
                                            position
                                    );

                            // Open QuizActivity
                            Intent intent =
                                    new Intent(
                                            requireContext(),
                                            QuizActivity.class
                                    );

                            // Send JSON asset path
                            intent.putExtra(
                                    "asset_path",
                                    assetPath
                            );

                            // Send set name
                            intent.putExtra(
                                    "set_name",
                                    setName
                            );

                            startActivity(intent);
                        }
                );

        recyclerView.setAdapter(adapter);
    }

    // =========================================================
    // File name → Set name
    // =========================================================

    private String convertFileNameToSetName(
            String fileName) {

        String name =
                fileName.replace(
                        ".json",
                        ""
                );

        String[] words =
                name.split("_");

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

        return result
                .toString()
                .trim();
    }
}