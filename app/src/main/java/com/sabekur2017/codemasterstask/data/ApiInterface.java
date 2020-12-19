package com.sabekur2017.codemasterstask.data;

import com.google.gson.JsonObject;
import com.sabekur2017.codemasterstask.data.models.popularmovie.PopularMovie;
import com.sabekur2017.codemasterstask.data.models.populartvseries.PopularTV;
import com.sabekur2017.codemasterstask.data.models.trendingcontent.TrendingContent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET(URLHelper.POPULAR_MOVIE)
    Call<PopularMovie> getpopularmoviedata(@Query("api_key") String apikey,

                                         @Query("sort_by") String vodeaverage,
                                         @Query("primary_release_year") String releaseyear);
    @GET(URLHelper.TV_SERIES)
    Call<PopularTV> gettvseries(@Query("api_key") String apikey,

                                @Query("sort_by") String vodeaverage,
                                @Query("primary_release_year") String releaseyear);
    @GET(URLHelper.TRENDING)
    Call<TrendingContent> gettrending(@Query("api_key") String apikey);

    @GET("movie/{id}")
    Call<JsonObject> getMovidedetails(
            @Path("id") String movieid,
            @Query("api_key") String apikey);
    @GET("tv/{id}")
    Call<JsonObject> gettvdetails(
            @Path("id") String tvId,
            @Query("api_key") String apikey);
}
