package com.example.sakkawy.movieapp.DataCallBack;

import com.example.sakkawy.movieapp.DataBase.MovieEntry;

import java.util.List;

public interface DatabaseCallBack {
    void movieAdded();
    void movieDeleted();
    void loadAllMovies(List<MovieEntry> movieEntries);
    void movieError();
}
