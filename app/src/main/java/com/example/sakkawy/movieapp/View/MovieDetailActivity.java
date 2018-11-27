package com.example.sakkawy.movieapp.View;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sakkawy.movieapp.Adapter.DetailAdapter;
import com.example.sakkawy.movieapp.DataBase.MovieEntry;
import com.example.sakkawy.movieapp.DataCallBack.DatabaseCallBack;
import com.example.sakkawy.movieapp.DataCallBack.DetailCallBack;
import com.example.sakkawy.movieapp.Model.Cast;
import com.example.sakkawy.movieapp.Model.Movie;
import com.example.sakkawy.movieapp.Model.MovieDetailModel;
import com.example.sakkawy.movieapp.Model.TrailerList;
import com.example.sakkawy.movieapp.R;
import com.example.sakkawy.movieapp.ViewModel.MovieDetailViewModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class MovieDetailActivity extends AppCompatActivity implements DetailCallBack ,DatabaseCallBack {

    private static final String TAG = "MovieDetailActivity";
    public static final String MOVIE_ID = "movie_id";
    private String YOUTUBE_URL = "https://www.youtube.com/watch?v=";


    RecyclerView castRecyleView;
    DetailAdapter mAdapter;
    MovieDetailViewModel viewModel;

    private TextView TV_title,TV_genres,TV_rating,TV_Status,TV_Date,TV_RunTime,TV_Description,TV_watchTrailer;
    private ImageView IV_poster , IV_fav;
    private FloatingActionButton fab;
    private int movie_id;
    private CoordinatorLayout coordinatorLayout;
    MovieDetailModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        setupViews();

        movie_id = getIntent().getIntExtra(MOVIE_ID,0);

        viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);

        castRecyleView = findViewById(R.id.casts_recycleview);
        castRecyleView.setHasFixedSize(false);
        castRecyleView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));


        setupViewModel();

    }

    private void setupViews(){
        TV_title = findViewById(R.id.tv_title_detail);
        TV_genres = findViewById(R.id.genres);
        TV_rating = findViewById(R.id.tv_rating_value_detail);
        TV_Status = findViewById(R.id.tv_status_detail);
        TV_Date = findViewById(R.id.tv_date_detail);
        TV_RunTime = findViewById(R.id.tv_duration_detail);
        TV_Description = findViewById(R.id.description);
        IV_poster = findViewById(R.id.imageView);
        TV_watchTrailer = findViewById(R.id.watch_trailer);
        IV_fav = findViewById(R.id.fav_image);
        fab = findViewById(R.id.back);
        coordinatorLayout = findViewById(R.id.coordinator);


        IV_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap1 = ((BitmapDrawable)IV_fav.getDrawable()).getBitmap();
                Drawable myDrawable = getResources().getDrawable(R.drawable.fav_un);
                Bitmap bitmap2 = ((BitmapDrawable)myDrawable).getBitmap();
                if(bitmap1.sameAs(bitmap2)){
                    IV_fav.setImageResource(R.drawable.fav_pre);
                   // Toast.makeText(MovieDetailActivity.this,"this is it",Toast.LENGTH_LONG).show();

                    MovieEntry movieEntry = new MovieEntry(
                            model.getPosterPath(),
                            model.getTitle(),
                            model.getOverview(),
                            model.getRevenue().toString(),
                            model.getRuntime().toString()
                    );

                    viewModel.insertMovie(MovieDetailActivity.this,movieEntry);

                }else{
                    IV_fav.setImageResource(R.drawable.fav_un);
                }


               /* if(IV_fav.getDrawable().equals(R.drawable.fav_unpressed_24dp)){
                    IV_fav.setBackground(getResources().getDrawable(R.drawable.fav_pressed_24dp));

                    MovieEntry movieEntry = new MovieEntry(
                            model.getPosterPath(),
                            model.getTitle(),
                            model.getOverview(),
                            model.getRevenue().toString(),
                            model.getRuntime().toString()
                    );

                    viewModel.insertMovie(MovieDetailActivity.this,movieEntry);
                }
                else{

                }*/
            }
        });

    }

    private void setupViewModel(){
        viewModel.getCast(this,movie_id);
        viewModel.getDetails(this,movie_id);
        viewModel.getTrailers(this,movie_id);

    }

    @Override
    public void getDetailCallBack(MovieDetailModel model) {
        this.model = model;
        TV_title.setText(model.getTitle());
        for (int i =0 ;i< model.getGenres().size();i++) {
            TV_genres.setText(model.getGenres().get(i).getName() + ", ");
        }
        TV_rating.setText(model.getVoteAverage()+"");
        TV_Status.setText(model.getStatus());
        TV_Date.setText(model.getReleaseDate());
        TV_RunTime.setText(model.getRuntime()+" min");
        TV_Description.setText(model.getOverview());
        TV_Description.setMovementMethod(new ScrollingMovementMethod());

        Picasso.get().load("http://image.tmdb.org/t/p/w185/"+model.getPosterPath())
                .into(IV_poster);
        Picasso.get().load("http://image.tmdb.org/t/p/w780/"+model.getBackdropPath())
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        coordinatorLayout.setBackground(new BitmapDrawable(bitmap));
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    }

    @Override
    public void getMovieCast(List<Cast> castList) {
        mAdapter = new DetailAdapter(castList,this);
        castRecyleView.setAdapter(mAdapter);

    }

    @Override
    public void getTrailers(List<TrailerList> trailers) {
        Log.d(TAG, "getTrailers: "+trailers.size());
        final String url = YOUTUBE_URL + trailers.get(0).getKey();
        TV_watchTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });


    }

    @Override
    public void movieAdded() {
        Toast.makeText(this,"Added",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void movieDeleted() {
        Toast.makeText(this,"deleted",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void loadAllMovies(List<MovieEntry> movieEntries) {

    }

    @Override
    public void movieError() {

    }
}
