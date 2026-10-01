package com.example.csteacherprep.models;

public class Playlist {

    private String title;
    private String channel;
    private String category;
    private String url;

    public Playlist() {
        // Required empty constructor
    }

    public Playlist(String title, String channel, String category, String url) {
        this.title = title;
        this.channel = channel;
        this.category = category;
        this.url = url;
    }

    // -----------------------------
    // Getters
    // -----------------------------

    public String getTitle() {
        return title;
    }

    public String getChannel() {
        return channel;
    }

    public String getCategory() {
        return category;
    }

    public String getUrl() {
        return url;
    }

    // -----------------------------
    // Setters
    // -----------------------------

    public void setTitle(String title) {
        this.title = title;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}