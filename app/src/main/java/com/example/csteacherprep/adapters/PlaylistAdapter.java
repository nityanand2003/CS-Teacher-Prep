package com.example.csteacherprep.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csteacherprep.R;
import com.example.csteacherprep.models.Playlist;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class PlaylistAdapter
        extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {

    private final Context context;
    private final List<Playlist> playlistList;

    public PlaylistAdapter(
            Context context,
            List<Playlist> playlistList) {

        this.context = context;
        this.playlistList = playlistList;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(
                        R.layout.item_playlist,
                        parent,
                        false
                );

        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull PlaylistViewHolder holder,
            int position) {

        Playlist playlist = playlistList.get(position);

        holder.tvTitle.setText(playlist.getTitle());
        holder.tvChannel.setText(playlist.getChannel());
        holder.tvCategory.setText(playlist.getCategory());

        holder.btnOpen.setOnClickListener(v -> {

            String url = playlist.getUrl();

            if (url != null && !url.trim().isEmpty()) {

                Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(url)
                );

                context.startActivity(intent);
            }
        });

        // Entire card can also be clicked
        holder.itemView.setOnClickListener(v -> {

            String url = playlist.getUrl();

            if (url != null && !url.trim().isEmpty()) {

                Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(url)
                );

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return playlistList.size();
    }

    public static class PlaylistViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvChannel;
        TextView tvCategory;
        MaterialButton btnOpen;

        public PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(
                    R.id.tvPlaylistTitle
            );

            tvChannel = itemView.findViewById(
                    R.id.tvChannel
            );

            tvCategory = itemView.findViewById(
                    R.id.tvCategory
            );

            btnOpen = itemView.findViewById(
                    R.id.btnOpenPlaylist
            );
        }
    }
}