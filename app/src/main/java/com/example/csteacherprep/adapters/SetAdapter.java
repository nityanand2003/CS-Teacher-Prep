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

    public interface OnSetClickListener {
        void onSetClick(String setName);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(String setName);
    }

    // Existing constructor
    // PYQ and Practice ke liye delete nahi dikhega
    public SetAdapter(
            List<String> setNames,
            List<Integer> questionCounts,
            OnSetClickListener listener) {

        this(
                setNames,
                questionCounts,
                listener,
                null
        );
    }

    // Self Designed Sets ke liye
    public SetAdapter(
            List<String> setNames,
            List<Integer> questionCounts,
            OnSetClickListener listener,
            OnDeleteClickListener deleteListener) {

        this.setNames = setNames;
        this.questionCounts = questionCounts;
        this.listener = listener;
        this.deleteListener = deleteListener;
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

        // Open set
        holder.itemView.setOnClickListener(v ->
                listener.onSetClick(setName)
        );

        // Delete button
        if (deleteListener != null) {

            holder.btnDelete.setVisibility(View.VISIBLE);

            holder.btnDelete.setOnClickListener(v ->
                    deleteListener.onDeleteClick(setName)
            );

        } else {

            holder.btnDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return setNames.size();
    }

    static class SetViewHolder extends RecyclerView.ViewHolder {

        TextView tvSetName;
        TextView tvQuestionCount;
        TextView btnDelete;

        public SetViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSetName =
                    itemView.findViewById(R.id.tvSetName);

            tvQuestionCount =
                    itemView.findViewById(R.id.tvQuestionCount);

            btnDelete =
                    itemView.findViewById(R.id.btnDelete);
        }
    }
}