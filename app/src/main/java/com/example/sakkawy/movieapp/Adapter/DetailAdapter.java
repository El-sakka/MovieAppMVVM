package com.example.sakkawy.movieapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sakkawy.movieapp.Model.Cast;
import com.example.sakkawy.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {
    private List<Cast> castList;
    private Context mContext;

    public DetailAdapter(List<Cast> castList, Context mContext) {
        this.castList = castList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.profit_layout,viewGroup,false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder detailViewHolder, int i) {
        Picasso.get().load("http://image.tmdb.org/t/p/w185/"+castList.get(i).getProfilePath())
                .into(detailViewHolder.circleImageView);
        detailViewHolder.actorTextView.setText(castList.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return castList.size()==0 ? 0:castList.size();
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        TextView actorTextView;
        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.profile_image);
            actorTextView = itemView.findViewById(R.id.actor_name);
        }
    }
}
