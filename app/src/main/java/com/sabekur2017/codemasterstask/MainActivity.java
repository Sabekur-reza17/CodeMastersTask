package com.sabekur2017.codemasterstask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sabekur2017.codemasterstask.data.APIClient;
import com.sabekur2017.codemasterstask.data.ApiInterface;
import com.sabekur2017.codemasterstask.data.models.popularmovie.PopularMovie;
import com.sabekur2017.codemasterstask.data.models.popularmovie.Result;
import com.sabekur2017.codemasterstask.data.models.populartvseries.PopularTV;
import com.sabekur2017.codemasterstask.data.models.trendingcontent.TrendingContent;
import com.sabekur2017.codemasterstask.ui.adapters.PopularMovieAdapter;
import com.sabekur2017.codemasterstask.ui.adapters.RecyclerTouchListener;
import com.sabekur2017.codemasterstask.ui.adapters.TVSerieusAdapter;
import com.sabekur2017.codemasterstask.ui.adapters.TrendingAdapter;
import com.sabekur2017.codemasterstask.ui.adapters.screen.MovieDetailsActivity;
import com.sabekur2017.codemasterstask.ui.adapters.screen.TvSeriesDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity   {
    ApiInterface apiInterface;
    RecyclerView movieRecycler,tvrecylcerview,trendingrecyclerview;
    PopularMovieAdapter popularMovieAdapter;
    TVSerieusAdapter tvSerieusAdapter;
    TrendingAdapter trendingAdapter;
    LinearLayoutManager linearLayoutManager;
    List<Result> movielist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieRecycler=findViewById(R.id.movie_reyclerview);
        tvrecylcerview=findViewById(R.id.tv_recyclerview);
        trendingrecyclerview=findViewById(R.id.trending_recyclerview);
        apiInterface= APIClient.createService(ApiInterface.class);
        getpopularmoviedata();
        getTvseries();
        getTrending();
        String movieid="577922";
       //getMOviedeatils();



    }


    private void getpopularmoviedata(){
        String apikey="1a97f3b8d5deee1d649c0025f3acf75c";
        String shortby="vote_average.desc";
        String relaease="2020";

        Call<PopularMovie> call = apiInterface.getpopularmoviedata(apikey,shortby,relaease);
        call.enqueue(new Callback<PopularMovie>() {
            @Override
            public void onResponse(Call<PopularMovie> call, Response<PopularMovie> response) {
                if(response.isSuccessful()){
                    Log.d("responsdata",response.body().getResults().get(0).getTitle());
                    String movieresposndata=response.body().getResults().get(0).getTitle()+" "
                            +response.body().getResults().get(0).getReleaseDate()+" "
                            +response.body().getResults().get(0).getVoteCount()+" "
                            +response.body().getResults().get(0).getPosterPath();
                    Log.d("moviedataone",movieresposndata);

                 movielist=fetchMovie(response);
                    //popularMovieAdapter.addAll(movielist);
                    linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    movieRecycler.setLayoutManager(linearLayoutManager);
                    popularMovieAdapter = new PopularMovieAdapter(getApplicationContext(),movielist);
                    movieRecycler.setAdapter(popularMovieAdapter);
                    movieRecycler.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), movieRecycler, new RecyclerTouchListener.ClickListener(){

                        @Override
                        public void onClick(View view, int position) {
                            Result result=movielist.get(position);
                            Toast.makeText(MainActivity.this, "clicked"+result.getId(), Toast.LENGTH_SHORT).show();
                          //  startActivity(new Intent(MainActivity.this, MovieDetailsActivity.class));
                            String movieid=String.valueOf(result.getId());
                            Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
                            intent.putExtra("movieidstr", movieid);
                            startActivity(intent);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));

                    Log.d("movielist",movielist.get(1).getVoteCount().toString());


                }else {
                    Log.d("errorres",response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<PopularMovie> call, Throwable t) {
                Log.d("error",t.getMessage());
            }
        });
    }
    private List<Result> fetchMovie(Response<PopularMovie> response) {
        PopularMovie popularMovie = response.body();
        return popularMovie.getResults();
    }
    private void getTvseries(){
        String apikey="1a97f3b8d5deee1d649c0025f3acf75c";
        String shortby="vote_average.desc";
        String relaease="2020";
        Call<PopularTV> popularTVCall = apiInterface.gettvseries(apikey,shortby,relaease);
        popularTVCall.enqueue(new Callback<PopularTV>() {
            @Override
            public void onResponse(Call<PopularTV> call, Response<PopularTV> response) {
                if(response.isSuccessful()){
                    /*Log.d("tvresposne",response.body().getResults().get(0).getPosterPath());
                    String tvresposndata=response.body().getResults().get(0).getName()+" "
                            +response.body().getResults().get(0).getFirstAirDate()+" "
                            +response.body().getResults().get(0).getVoteCount()+" "
                            +response.body().getResults().get(0).getPosterPath();
                    Log.d("tvdataone",tvresposndata);*/
                    List<com.sabekur2017.codemasterstask.data.models.populartvseries.Result> tvlist=fetchTV(response);
                   // Log.d("tvlist",tvlist.get(1).getPosterPath());
                    linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    tvrecylcerview.setLayoutManager(linearLayoutManager);
                    tvSerieusAdapter = new TVSerieusAdapter(getApplicationContext(),tvlist);
                    tvrecylcerview.setAdapter(tvSerieusAdapter);
                    tvrecylcerview.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), tvrecylcerview, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            Result tvdata=movielist.get(position);
                            com.sabekur2017.codemasterstask.data.models.populartvseries.Result tvitem=tvlist.get(position);
                            Toast.makeText(MainActivity.this, "clicked"+tvitem.getId(), Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(MainActivity.this, TvSeriesDetailsActivity.class));
                            String tvid=String.valueOf(tvdata.getId());
                            Intent intent = new Intent(MainActivity.this, TvSeriesDetailsActivity.class);
                            intent.putExtra("tvstr", tvid);
                            startActivity(intent);

                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));
                }else {
                    Log.d("tverror",response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<PopularTV> call, Throwable t) {
                Log.d("tvexcption",t.getMessage());
            }
        });

    }
    private List<com.sabekur2017.codemasterstask.data.models.populartvseries.Result> fetchTV(Response<PopularTV> response) {
        PopularTV popularTV = response.body();
        return popularTV.getResults();
    }
    private void getTrending(){
        String apikey="1a97f3b8d5deee1d649c0025f3acf75c";
        Call<TrendingContent> trendingContentCall = apiInterface.gettrending(apikey);
        trendingContentCall.enqueue(new Callback<TrendingContent>() {
            @Override
            public void onResponse(Call<TrendingContent> call, Response<TrendingContent> response) {
                if(response.isSuccessful()){
                    Log.d("trendingresposne",response.body().getResults().toString());
                    List<com.sabekur2017.codemasterstask.data.models.trendingcontent.Result> trending=fetchTrending(response);
                    Log.d("trendinglist",String.valueOf(trending.size()));
                    trendingrecyclerview.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                    trendingAdapter=new TrendingAdapter(getApplicationContext(),trending);
                    trendingrecyclerview.setAdapter(trendingAdapter);
                }else {
                    Log.d("trendingerror",response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<TrendingContent> call, Throwable t) {
                Log.d("trendingexcption",t.getMessage());
            }
        });
    }
    private List<com.sabekur2017.codemasterstask.data.models.trendingcontent.Result> fetchTrending(Response<TrendingContent> response) {
        TrendingContent trendingContent = response.body();
        return trendingContent.getResults();
    }





}