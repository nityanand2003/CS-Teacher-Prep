package com.example.csteacherprep.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csteacherprep.R;
import com.example.csteacherprep.adapters.PlaylistAdapter;
import com.example.csteacherprep.models.Playlist;
import com.example.csteacherprep.utils.PlaylistHelper;

import java.util.List;

public class PlaylistActivity extends AppCompatActivity {

    private RecyclerView recyclerPlaylists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_playlist);

        // -----------------------------
        // Toolbar
        // -----------------------------

        Toolbar toolbar = findViewById(
                R.id.toolbarPlaylist
        );

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v ->
                onBackPressed()
        );

        // -----------------------------
        // RecyclerView
        // -----------------------------

        recyclerPlaylists = findViewById(
                R.id.recyclerPlaylists
        );

        recyclerPlaylists.setLayoutManager(
                new LinearLayoutManager(this)
        );

        // -----------------------------
        // Load playlists from JSON
        // -----------------------------

        List<Playlist> playlistList =
                PlaylistHelper.loadPlaylists(this);

        PlaylistAdapter adapter =
                new PlaylistAdapter(
                        this,
                        playlistList
                );

        recyclerPlaylists.setAdapter(adapter);
    }
}