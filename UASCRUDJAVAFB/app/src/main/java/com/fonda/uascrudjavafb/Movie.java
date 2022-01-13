package com.fonda.uascrudjavafb;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Movie implements Serializable {

    @Exclude
    private String key;
    private String title;
    private String genre;
    private String duration;



    public Movie(){}

    public Movie(String title, String genre, String duration) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
