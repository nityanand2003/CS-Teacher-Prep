package com.example.csteacherprep.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csteacherprep.R;

import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {

    private final List<String> topicNames;
    private final List<String> topicIds;
    private final OnTopicClickListener listener;

    public interface OnTopicClickListener {
        void onTopicClick(String topicId, String topicName);
    }

    public TopicAdapter(
            List<String> topicNames,
            List<String> topicIds,
            OnTopicClickListener listener) {

        this.topicNames = topicNames;
        this.topicIds = topicIds;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_topic, parent, false);

        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull TopicViewHolder holder,
            int position) {

        String topicName = topicNames.get(position);
        String topicId = topicIds.get(position);

        holder.tvTopicName.setText(topicName);

        holder.tvTopicSubtitle.setText(
                "Practice " + topicName + " questions"
        );

        holder.itemView.setOnClickListener(v ->
                listener.onTopicClick(topicId, topicName)
        );
    }

    @Override
    public int getItemCount() {
        return topicNames.size();
    }

    static class TopicViewHolder extends RecyclerView.ViewHolder {

        TextView tvTopicName;
        TextView tvTopicSubtitle;

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTopicName = itemView.findViewById(R.id.tvTopicName);
            tvTopicSubtitle = itemView.findViewById(R.id.tvTopicSubtitle);
        }
    }
}