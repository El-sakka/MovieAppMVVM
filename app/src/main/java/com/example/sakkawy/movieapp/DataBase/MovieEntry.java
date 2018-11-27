package com.example.sakkawy.movieapp.DataBase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "movie")
public class MovieEntry {
    @PrimaryKey(autoGenerate = true)
    int id;
    String imagePoster;
    String title;
    String description ;
    String rating;
    String duration;

    @Ignore
    public MovieEntry(String imagePoster, String title, String description, String rating, String duration) {
        this.imagePoster = imagePoster;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.duration = duration;
    }

    public MovieEntry(int id, String imagePoster, String title, String description, String rating, String duration) {
        this.id = id;
        this.imagePoster = imagePoster;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePoster() {
        return imagePoster;
    }

    public void setImagePoster(String imagePoster) {
        this.imagePoster = imagePoster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}