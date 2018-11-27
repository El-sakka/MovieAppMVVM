package com.example.sakkawy.movieapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.sakkawy.movieapp.DataBase.MovieDao;
import com.example.sakkawy.movieapp.DataBase.MovieDatabase;
import com.example.sakkawy.movieapp.DataBase.MovieEntry;
import com.example.sakkawy.movieapp.DataCallBack.DatabaseCallBack;
import com.example.sakkawy.movieapp.DataCallBack.DetailCallBack;
import com.example.sakkawy.movieapp.DataCallBack.MovieCallBack;
import com.example.sakkawy.movieapp.Model.Credits;
import com.example.sakkawy.movieapp.Model.Movie;
import com.example.sakkawy.movieapp.Model.MovieDetailModel;
import com.example.sakkawy.movieapp.Model.Trailer;
import com.example.sakkawy.movieapp.Model.TrailerList;
import com.example.sakkawy.movieapp.Utils.IClient;
import com.example.sakkawy.movieapp.Utils.MovieClient;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MovieRepository {
    private static final String TAG = "MovieRepository";
    private static final Object LOCK = new Object();
    private static MovieRepository sInstance;
    private final MovieDao movieDao;
    private Context mContext;
    private IClient iClient;
    private static  String API_KEY ;

    CompositeDisposable disposable = new CompositeDisposable();


    private MovieRepository(Application application){
        iClient = MovieClient.getsInstance().create(IClient.class);
        mContext = application.getApplicationContext();
        API_KEY = mContext.getResources().getString(R.string.API_KEY);
        movieDao = MovieDatabase.getInstance(mContext).movieDao();
    }

    public synchronized static MovieRepository getInstance(Application application){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = new MovieRepository(application);
                Log.d(TAG, "getInstance: made new instance of repo");
            }
        }
        return sInstance;
    }

    public void getPopularMovie(final MovieCallBack callBack) {
        Log.d(TAG, "getPopularMovie: "+mContext.getResources().getString(R.string.API_KEY));
        disposable.add( iClient.fetchPopularMovies(API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Movie>() {
                    @Override
                    public void accept(Movie movie) throws Exception {
                        Log.d(TAG, "movie totla Page: "+movie.getTotalPages());
                        callBack.getPuplarMovieCallBack(movie.getResults());
                    }
                }));
    }

    public void getTopRatedMovies(final MovieCallBack callBack){
        disposable.add(
                iClient.fetchTopRatedMovies(API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Movie>() {
                    @Override
                    public void accept(Movie movie) throws Exception {
                        callBack.getTopRatedCallBack(movie.getResults());
                    }
                })
        );
    }

    public void getUpCommingMovies(final MovieCallBack callBack){
        disposable.add(
                iClient.fetchUpCommingMovies(API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Movie>() {
                    @Override
                    public void accept(Movie movie) throws Exception {
                        Log.d(TAG, "accept: upcomming movies "+movie.getResults().size());
                        callBack.getUpCommingCallBack(movie.getResults());
                    }
                })
        );
    }

    public void getMovieDetails(final DetailCallBack callBack, int id){
        disposable.add(
                iClient.getDetails(id,API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieDetailModel>() {
                    @Override
                    public void accept(MovieDetailModel movieDetailModel) throws Exception {
                        callBack.getDetailCallBack(movieDetailModel);
                    }
                })
        );
    }

    public void getMovieCast(final DetailCallBack callBack, int id){
        disposable.add(
                iClient.getCast(id,API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Credits>() {
                    @Override
                    public void accept(Credits credits) throws Exception {
                        callBack.getMovieCast(credits.getCast());
                    }
                })
        );
    }

    public void getTrailers(final DetailCallBack callBack, int id){
        disposable.add(
                iClient.getTrailers(id,API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Trailer>() {
                    @Override
                    public void accept(Trailer trailer) throws Exception {
                        callBack.getTrailers(trailer.getResults());
                    }
                })
        );
    }

    public void insertMovie(final DatabaseCallBack callBack, final MovieEntry movieEntry){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                movieDao.insertMovie(movieEntry);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        callBack.movieAdded();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void deleteMovie(final DatabaseCallBack callBack, final MovieEntry movieEntry){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                movieDao.deleteMovie(movieEntry);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        callBack.movieDeleted();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void loadAllMovies(final DatabaseCallBack callBack){
        movieDao.loadAllMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MovieEntry>>() {
                    @Override
                    public void accept(List<MovieEntry> movieEntries) throws Exception {
                        callBack.loadAllMovies(movieEntries);
                    }
                });
    }

}
