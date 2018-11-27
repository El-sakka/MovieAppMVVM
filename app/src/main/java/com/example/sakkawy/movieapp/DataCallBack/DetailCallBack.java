package com.example.sakkawy.movieapp.DataCallBack;

import com.example.sakkawy.movieapp.Model.Cast;
import com.example.sakkawy.movieapp.Model.MovieDetailModel;
import com.example.sakkawy.movieapp.Model.TrailerList;

import java.util.List;

public interface DetailCallBack {

    void getDetailCallBack(MovieDetailModel model);
    void getMovieCast(List<Cast> castList);
    void getTrailers(List<TrailerList> trailers);
}
