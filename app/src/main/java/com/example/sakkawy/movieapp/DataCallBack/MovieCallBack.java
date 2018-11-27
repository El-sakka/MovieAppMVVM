package com.example.sakkawy.movieapp.DataCallBack;

import com.example.sakkawy.movieapp.Model.Movie;
import com.example.sakkawy.movieapp.Model.Result;

import java.util.List;

public interface  MovieCallBack {
    void getPuplarMovieCallBack(List<Result> popularList);
    void getTopRatedCallBack(List<Result> topList);
    void getUpCommingCallBack(List<Result> upcommingList);
}
