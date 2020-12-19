package com.sabekur2017.codemasterstask.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sabekur2017.codemasterstask.R;
import com.sabekur2017.codemasterstask.data.models.popularmovie.Result;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PopularMovieAdapter extends RecyclerView.Adapter<PopularMovieAdapter.MovieViewHolder> {
    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w342";
    private Context context;
    public List<Result> movielist=new ArrayList<>();

    public PopularMovieAdapter(Context context, List<Result> movielist) {
        this.context = context;
        this.movielist = movielist;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        final Result moviedata = movielist.get(position);
        final MovieViewHolder movieViewHolder=holder;
        movieViewHolder.title.setText(moviedata.getTitle());
        movieViewHolder.releaseDate.setText(moviedata.getReleaseDate());
        movieViewHolder.rating.setText(String.valueOf(moviedata.getVoteCount()));
        String imageulr=BASE_URL_IMG+moviedata.getPosterPath();
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background);
        Glide.with(context).load(imageulr).apply(options).into(movieViewHolder.posterImage);




    }


    @Override
    public int getItemCount() {
        return movielist.size();
    }



    class MovieViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        TextView releaseDate;
        TextView title;
        TextView rating;
        ImageView posterImage;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            title=itemView.findViewById(R.id.movietitle);
            releaseDate=itemView.findViewById(R.id.moviedate);
            rating=itemView.findViewById(R.id.movievotecount);
            posterImage=itemView.findViewById(R.id.movie_item_poster);

        }
    }



}
