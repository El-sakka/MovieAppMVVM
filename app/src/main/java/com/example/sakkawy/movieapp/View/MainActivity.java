package com.example.sakkawy.movieapp.View;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sakkawy.movieapp.Adapter.MovieAdapter;
import com.example.sakkawy.movieapp.Model.MovieDetailModel;
import com.example.sakkawy.movieapp.Model.Result;
import com.example.sakkawy.movieapp.DataCallBack.MovieCallBack;
import com.example.sakkawy.movieapp.R;
import com.example.sakkawy.movieapp.ViewModel.MainViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieCallBack , MovieAdapter.ItemClickListener {

    private static final String TAG = "MainActivity";
    RecyclerView mPopularRecyclerView;
    RecyclerView mTopRatedRecyclerView;
    RecyclerView mUpCommingRecyclerView;
    MainViewModel viewModel;
    MovieAdapter mAdapter;


    private ImageView wallImageView;
    private TextView titleTextView;
    private TextView feturesTextView;
    private TextView moreInfoTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mPopularRecyclerView = findViewById(R.id.pupular_recycleview);
        mPopularRecyclerView.setHasFixedSize(false);
        mPopularRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        mTopRatedRecyclerView = findViewById(R.id.toprated_recycleview);
        mTopRatedRecyclerView.setHasFixedSize(false);
        mTopRatedRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        mUpCommingRecyclerView = findViewById(R.id.upcomming_recycleview);
        mUpCommingRecyclerView.setHasFixedSize(false);
        mUpCommingRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));


        setupViewModel();
    }

    private void setUpWallpaper(Result movie){
        wallImageView = findViewById(R.id.image_main);
        titleTextView = findViewById(R.id.title_main);
       // feturesTextView = findViewById(R.id.fetures_main);
        moreInfoTextView = findViewById(R.id.more_info);

        Picasso.get().load("http://image.tmdb.org/t/p/w780/"+movie.getBackdropPath())
                .into(wallImageView);
        titleTextView.setText(movie.getTitle());
        //feturesTextView.setText(movie.);

    }


    private void setupViewModel(){
        viewModel.getPopularMovies(this);
        viewModel.getTopatedMovies(this);
        viewModel.getUpCommmingMovies(this);
    }

    @Override
    public void getPuplarMovieCallBack(List<Result> popularList) {
        Log.d(TAG, "getPuplarMovieCallBack: ");
        mAdapter = new MovieAdapter(popularList,this,this);
        mPopularRecyclerView.setAdapter(mAdapter);

        setUpWallpaper(popularList.get(0));
    }

    @Override
    public void getTopRatedCallBack(List<Result> topList) {
        mAdapter = new MovieAdapter(topList,this,this);
        mTopRatedRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void getUpCommingCallBack(List<Result> upcommingList) {
        mAdapter = new MovieAdapter(upcommingList,this,this);
        mUpCommingRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onItemClickListener(int itemId) {
        Intent intent = new Intent(MainActivity.this,MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.MOVIE_ID,itemId);
        startActivity(intent);
    }
}
