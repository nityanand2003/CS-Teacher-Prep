package com.example.csteacherprep.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csteacherprep.R;

import java.util.List;

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.SetViewHolder> {

    private final List<String> setNames;
    private final List<Integer> questionCounts;

    private final OnSetClickListener listener;
    private final OnDeleteClickListener deleteListener;
    private final OnManageQuestionsClickListener manageQuestionsListener;
    private final OnEditSetClickListener editSetListener;

    public interface OnSetClickListener {
        void onSetClick(String setName);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(String setName);
    }

    public interface OnManageQuestionsClickListener {
        void onManageQuestionsClick(String setName);
    }

    public interface OnEditSetClickListener {
        void onEditSetClick(String setName);
    }

    // PYQ / Practice
    public SetAdapter(
            List<String> setNames,
            List<Integer> questionCounts,
            OnSetClickListener listener) {

        this(
                setNames,
                questionCounts,
                listener,
                null,
                null,
                null
        );
    }

    // Self Designed - Delete
    public SetAdapter(
            List<String> setNames,
            List<Integer> questionCounts,
            OnSetClickListener listener,
            OnDeleteClickListener deleteListener) {

        this(
                setNames,
                questionCounts,
                listener,
                deleteListener,
                null,
                null
        );
    }

    // Self Designed - Delete + Manage Questions
    public SetAdapter(
            List<String> setNames,
            List<Integer> questionCounts,
            OnSetClickListener listener,
            OnDeleteClickListener deleteListener,
            OnManageQuestionsClickListener manageQuestionsListener) {

        this(
                setNames,
                questionCounts,
                listener,
                deleteListener,
                manageQuestionsListener,
                null
        );
    }

    // Full constructor
    public SetAdapter(
            List<String> setNames,
            List<Integer> questionCounts,
            OnSetClickListener listener,
            OnDeleteClickListener deleteListener,
            OnManageQuestionsClickListener manageQuestionsListener,
            OnEditSetClickListener editSetListener) {

        this.setNames = setNames;
        this.questionCounts = questionCounts;
        this.listener = listener;
        this.deleteListener = deleteListener;
        this.manageQuestionsListener = manageQuestionsListener;
        this.editSetListener = editSetListener;
    }

    @NonNull
    @Override
    public SetViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_set, parent, false);

        return new SetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull SetViewHolder holder,
            int position) {

        String setName = setNames.get(position);
        int questionCount = questionCounts.get(position);

        holder.tvSetName.setText(setName);

        holder.tvQuestionCount.setText(
                questionCount + " Questions"
        );

        // Open Quiz
        holder.itemView.setOnClickListener(v ->
                listener.onSetClick(setName)
        );

        // Edit Set
        if (editSetListener != null) {

            holder.btnEditSet.setVisibility(View.VISIBLE);

            holder.btnEditSet.setOnClickListener(v ->
                    editSetListener.onEditSetClick(setName)
            );

        } else {

            holder.btnEditSet.setVisibility(View.GONE);
        }

        // Delete Set
        if (deleteListener != null) {

            holder.btnDelete.setVisibility(View.VISIBLE);

            holder.btnDelete.setOnClickListener(v ->
                    deleteListener.onDeleteClick(setName)
            );

        } else {

            holder.btnDelete.setVisibility(View.GONE);
        }

        // Manage Questions
        if (manageQuestionsListener != null) {

            holder.btnManageQuestions.setVisibility(
                    View.VISIBLE
            );

            holder.btnManageQuestions.setOnClickListener(v ->
                    manageQuestionsListener
                            .onManageQuestionsClick(setName)
            );

        } else {

            holder.btnManageQuestions.setVisibility(
                    View.GONE
            );
        }
    }

    @Override
    public int getItemCount() {
        return setNames.size();
    }

    static class SetViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvSetName;
        TextView tvQuestionCount;
        TextView btnEditSet;
        TextView btnDelete;
        TextView btnManageQuestions;

        public SetViewHolder(
                @NonNull View itemView) {

            super(itemView);

            tvSetName =
                    itemView.findViewById(
                            R.id.tvSetName
                    );

            tvQuestionCount =
                    itemView.findViewById(
                            R.id.tvQuestionCount
                    );

            btnEditSet =
                    itemView.findViewById(
                            R.id.btnEditSet
                    );

            btnDelete =
                    itemView.findViewById(
                            R.id.btnDelete
                    );

            btnManageQuestions =
                    itemView.findViewById(
                            R.id.btnManageQuestions
                    );
        }
    }
}