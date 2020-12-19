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
import com.sabekur2017.codemasterstask.data.models.trendingcontent.Result;

import java.util.ArrayList;
import java.util.List;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.TrendingViewHolder> {
    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w342";
    private Context context;
    private List<Result> trendinglist=new ArrayList<>();

    public TrendingAdapter(Context context, List<Result> trendinglist) {
        this.context = context;
        this.trendinglist = trendinglist;
    }

    @NonNull
    @Override
    public TrendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trendingitem, parent, false);

        return new TrendingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingViewHolder holder, int position) {
        final Result trendingitem = trendinglist.get(position);
        final TrendingViewHolder tvViewHolder=holder;
        tvViewHolder.trendingtitle.setText("Name :"+trendingitem.getName());
        tvViewHolder.trendingreleaseDate.setText("AirDate :"+trendingitem.getFirstAirDate());
        tvViewHolder.trendingrating.setText("Vote :"+String.valueOf(trendingitem.getVoteCount()));
        String imageulr=BASE_URL_IMG+trendingitem.getPosterPath();
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background);
        Glide.with(context).load(imageulr).apply(options).into(tvViewHolder.trendingposterImage);
    }

    @Override
    public int getItemCount() {
        return trendinglist.size();
    }

    class TrendingViewHolder extends RecyclerView.ViewHolder{
        TextView trendingreleaseDate;
        TextView trendingtitle;
        TextView trendingrating;
        ImageView trendingposterImage;

        public TrendingViewHolder(@NonNull View itemView) {
            super(itemView);
            trendingtitle=itemView.findViewById(R.id.trendingtitle);
            trendingreleaseDate=itemView.findViewById(R.id.trendingdate);
            trendingrating=itemView.findViewById(R.id.trendingvotecount);
            trendingposterImage=itemView.findViewById(R.id.trending_item_poster);
        }
    }
}
