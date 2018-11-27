package com.example.sakkawy.movieapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sakkawy.movieapp.Model.Result;
import com.example.sakkawy.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Result> list;
    private Context mContext;
    private ItemClickListener mItemClickListener;

    public MovieAdapter(List<Result> popularList, Context mContext,ItemClickListener itemClickListener) {
        this.list = popularList;
        this.mContext = mContext;
        this.mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.movie_layout,viewGroup,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        Picasso.get().load("http://image.tmdb.org/t/p/w185/"+ list.get(i).getPosterPath())
                .into(movieViewHolder.posterImageView);
    }

    @Override
    public int getItemCount() {
        return list.size() == 0? 0: list.size();
    }



    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView posterImageView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.movie_image);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int movieId = list.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(movieId);
        }
    }


    public interface ItemClickListener{
        void onItemClickListener(int itemId);

    }
}
