package com.example.sakkawy.movieapp.Utils;

import android.util.Log;

import com.example.sakkawy.movieapp.R;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieClient {
    private static final String TAG = "MovieClient";
    private final static Object LOCK = new Object();
    private static Retrofit sInstance;
    private static final String BASE_URL ="http://api.themoviedb.org/3/movie/";


    public MovieClient() {
    }

    public static Retrofit getsInstance() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        if(sInstance == null){
            synchronized (LOCK) {
                Log.d(TAG, "getsInstance: retrofit");
                sInstance = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(httpClient.build())
                        .build();
            }
        }
        return sInstance;
    }
}
