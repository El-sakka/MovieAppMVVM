package com.example.sakkawy.movieapp.Utils;

import com.example.sakkawy.movieapp.Model.Credits;
import com.example.sakkawy.movieapp.Model.Latest;
import com.example.sakkawy.movieapp.Model.Movie;
import com.example.sakkawy.movieapp.Model.MovieDetailModel;
import com.example.sakkawy.movieapp.Model.Trailer;
import com.example.sakkawy.movieapp.Model.TrailerList;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IClient {
    @GET("popular")
    Observable<Movie> fetchPopularMovies(@Query("api_key") String api_key);

    @GET("top_rated")
    Observable<Movie> fetchTopRatedMovies(@Query("api_key") String api_key);

    @GET("upcoming")
    Observable<Movie> fetchUpCommingMovies(@Query("api_key") String api_key);



    @GET("latest")
    Observable<Latest> getLatestMovie(@Query("api_key") String api_key);

    @GET("{movie_id}/recommendations")
    Observable<Movie> getRecommendationsMovies(@Path(value = "movie_id",encoded = true) Integer movieId,@Query("api_key") String api_key);

    @GET("{movie_id}")
    Observable<MovieDetailModel> getDetails(@Path(value = "movie_id",encoded = true) Integer movie_id, @Query("api_key") String api_key);


    @GET("{movie_id}/credits")
    Observable<Credits> getCast(@Path(value = "movie_id",encoded = true) Integer movie_id,@Query("api_key") String api_key);


    @GET("{movie_id}/videos")
    Observable<Trailer> getTrailers(@Path(value = "movie_id",encoded = true) Integer id, @Query("api_key") String api_key);


}
