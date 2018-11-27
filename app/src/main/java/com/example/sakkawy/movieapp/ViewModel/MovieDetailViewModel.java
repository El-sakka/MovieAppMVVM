package com.example.sakkawy.movieapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.sakkawy.movieapp.DataBase.MovieEntry;
import com.example.sakkawy.movieapp.DataCallBack.DatabaseCallBack;
import com.example.sakkawy.movieapp.DataCallBack.DetailCallBack;
import com.example.sakkawy.movieapp.MovieRepository;

public class MovieDetailViewModel extends AndroidViewModel {
    private MovieRepository mRepository;
    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        mRepository = MovieRepository.getInstance(application);
    }

    public void getCast(DetailCallBack callBack,int id){
        mRepository.getMovieCast(callBack,id);
    }

    public void getDetails(DetailCallBack callBack,int id){
        mRepository.getMovieDetails(callBack,id);
    }
    public void getTrailers(DetailCallBack callBack,int id){
        mRepository.getTrailers(callBack,id);
    }

    public void insertMovie(DatabaseCallBack callBack, MovieEntry movieEntry){
        mRepository.insertMovie(callBack,movieEntry);
    }

    public void deleteMovie(DatabaseCallBack callBack,MovieEntry movieEntry){
        mRepository.deleteMovie(callBack,movieEntry);
    }
}
