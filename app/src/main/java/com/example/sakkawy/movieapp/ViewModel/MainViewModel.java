package com.example.sakkawy.movieapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.sakkawy.movieapp.DataBase.MovieEntry;
import com.example.sakkawy.movieapp.DataCallBack.DatabaseCallBack;
import com.example.sakkawy.movieapp.DataCallBack.MovieCallBack;
import com.example.sakkawy.movieapp.Model.Movie;
import com.example.sakkawy.movieapp.MovieRepository;

public class MainViewModel extends AndroidViewModel {

    private final MovieRepository mMovieRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mMovieRepository = MovieRepository.getInstance(application);
    }

    public void getPopularMovies(MovieCallBack callBack){
        mMovieRepository.getPopularMovie(callBack);
    }

    public void getTopatedMovies(MovieCallBack callBack){
        mMovieRepository.getTopRatedMovies(callBack);
    }

    public void getUpCommmingMovies(MovieCallBack callBack){
        mMovieRepository.getUpCommingMovies(callBack);
    }


}
