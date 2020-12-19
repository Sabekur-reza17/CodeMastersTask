package com.sabekur2017.codemasterstask.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sabekur2017.codemasterstask.R;
import com.sabekur2017.codemasterstask.data.models.populartvseries.Result;

import java.util.ArrayList;
import java.util.List;

public class TVSerieusAdapter extends RecyclerView.Adapter<TVSerieusAdapter.TVViewHolder>{
    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w342";
    private Context context;
    private List<Result> tvserieslist=new ArrayList<>();

    public TVSerieusAdapter(Context context, List<Result> tvserieslist) {
        this.context = context;
        this.tvserieslist = tvserieslist;
    }

    @NonNull
    @Override
    public TVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tvseries_item, parent, false);
        return new TVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVViewHolder holder, int position) {
        final Result tvdata = tvserieslist.get(position);
        final TVViewHolder tvViewHolder=holder;
        tvViewHolder.tvtitle.setText(tvdata.getName());
        tvViewHolder.tvreleaseDate.setText(tvdata.getFirstAirDate());
        tvViewHolder.tvrating.setText(String.valueOf(tvdata.getVoteCount()));
        String imageulr=BASE_URL_IMG+tvdata.getPosterPath();
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background);
        Glide.with(context).load(imageulr).apply(options).into(tvViewHolder.tvposterImage);
    }

    @Override
    public int getItemCount() {
        return tvserieslist.size();
    }

    class TVViewHolder extends RecyclerView.ViewHolder{
        TextView tvreleaseDate;
        TextView tvtitle;
        TextView tvrating;
        ImageView tvposterImage;

        public TVViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtitle=itemView.findViewById(R.id.tvtitle);
            tvreleaseDate=itemView.findViewById(R.id.tvdate);
            tvrating=itemView.findViewById(R.id.tvvotecount);
            tvposterImage=itemView.findViewById(R.id.tv_item_poster);
        }
    }
}
