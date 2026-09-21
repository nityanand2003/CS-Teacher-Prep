package com.example.csteacherprep.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csteacherprep.R;
import com.example.csteacherprep.models.UserQuestion;

import java.util.List;

public class QuestionAdapter
        extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private final List<UserQuestion> questions;
    private final OnQuestionClickListener listener;
    private final OnEditClickListener editListener;
    private final OnDeleteClickListener deleteListener;

    public interface OnQuestionClickListener {
        void onQuestionClick(UserQuestion question);
    }

    public interface OnEditClickListener {
        void onEditClick(UserQuestion question);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(UserQuestion question);
    }

    public QuestionAdapter(
            List<UserQuestion> questions,
            OnQuestionClickListener listener,
            OnEditClickListener editListener,
            OnDeleteClickListener deleteListener) {

        this.questions = questions;
        this.listener = listener;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_question, parent, false);

        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull QuestionViewHolder holder,
            int position) {

        UserQuestion question = questions.get(position);

        holder.tvQuestionNumber.setText(
                "Question " + (position + 1)
        );

        holder.tvQuestion.setText(
                question.getQuestionText()
        );

        holder.itemView.setOnClickListener(v -> {

            if (listener != null) {
                listener.onQuestionClick(question);
            }
        });

        holder.btnEdit.setOnClickListener(v -> {

            if (editListener != null) {
                editListener.onEditClick(question);
            }
        });

        holder.btnDelete.setOnClickListener(v -> {

            if (deleteListener != null) {
                deleteListener.onDeleteClick(question);
            }
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    static class QuestionViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvQuestionNumber;
        TextView tvQuestion;
        TextView btnEdit;
        TextView btnDelete;

        public QuestionViewHolder(
                @NonNull View itemView) {

            super(itemView);

            tvQuestionNumber =
                    itemView.findViewById(
                            R.id.tvQuestionNumber
                    );

            tvQuestion =
                    itemView.findViewById(
                            R.id.tvQuestion
                    );

            btnEdit =
                    itemView.findViewById(
                            R.id.btnEditQuestion
                    );

            btnDelete =
                    itemView.findViewById(
                            R.id.btnDeleteQuestion
                    );
        }
    }
}