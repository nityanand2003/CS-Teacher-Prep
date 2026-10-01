package com.example.csteacherprep.utils;

import android.content.Context;

import com.example.csteacherprep.models.Playlist;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PlaylistHelper {

    private static final String FILE_PATH =
            "yt_playlist/playlists.json";

    public static List<Playlist> loadPlaylists(Context context) {

        List<Playlist> playlistList = new ArrayList<>();

        try {

            // --------------------------------
            // Read JSON file from assets
            // --------------------------------

            InputStream inputStream =
                    context.getAssets().open(FILE_PATH);

            int size = inputStream.available();

            byte[] buffer = new byte[size];

            inputStream.read(buffer);
            inputStream.close();

            String json =
                    new String(buffer, StandardCharsets.UTF_8);

            // --------------------------------
            // Parse JSON
            // --------------------------------

            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject object =
                        jsonArray.getJSONObject(i);

                String title =
                        object.optString("title");

                String channel =
                        object.optString("channel");

                String category =
                        object.optString("category");

                String url =
                        object.optString("url");

                Playlist playlist = new Playlist(
                        title,
                        channel,
                        category,
                        url
                );

                playlistList.add(playlist);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return playlistList;
    }
}