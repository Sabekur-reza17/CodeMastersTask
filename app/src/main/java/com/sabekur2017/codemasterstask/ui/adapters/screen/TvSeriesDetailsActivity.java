package com.sabekur2017.codemasterstask.ui.adapters.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sabekur2017.codemasterstask.R;
import com.sabekur2017.codemasterstask.data.APIClient;
import com.sabekur2017.codemasterstask.data.ApiInterface;
import com.sabekur2017.codemasterstask.utils.Networkcheck;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvSeriesDetailsActivity extends AppCompatActivity {
    private static final String BASE_URL_BACK = "https://image.tmdb.org/t/p/w780";
    ApiInterface apiInterface;
    ImageView tvback;
    TextView tvttilel,tvtovervie,tvttagline,tvtvoteavrage,tvtpopularity;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_series_details);
        apiInterface= APIClient.createService(ApiInterface.class);
        tvback=findViewById(R.id.tv_back);
        progressBar=findViewById(R.id.tv_progress);
        tvttilel=findViewById(R.id.tvorgtile);
        tvtovervie  =findViewById(R.id.tvovervie);
        tvttagline=findViewById(R.id.tvtagline);
        tvtvoteavrage=findViewById(R.id.tvovervie);
        tvtpopularity=findViewById(R.id.tvpoulariry);
        Intent intent = getIntent();
        String strda = intent.getStringExtra("tvstr");
        Log.d("tvstr",strda);
       // String id="82856";
        if(Networkcheck.isConnectedToInternet(this)){
            getTvdeatils(strda);
        }else {
            Toast.makeText(this, "check the internet connection", Toast.LENGTH_SHORT).show();
        }
        getTvdeatils(strda);
    }
    private void getTvdeatils(String tvId){

        String apikey="1a97f3b8d5deee1d649c0025f3acf75c";
        Call<JsonObject> movidedetails=apiInterface.gettvdetails(tvId,apikey);
        showLoading();
        movidedetails.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    JsonObject dta=response.body();
                    if(dta !=null){
                        try {
                            Log.d("tvid",dta.get("id").getAsString());
                            Log.d("tvtilel",dta.get("name").getAsString());
                            String imageulr=BASE_URL_BACK+dta.get("backdrop_path").getAsString();
                            RequestOptions options = new RequestOptions()
                                    .placeholder(R.drawable.ic_launcher_background)
                                    .error(R.drawable.ic_launcher_background);
                            Glide.with(getApplicationContext()).load(imageulr).apply(options).into(tvback);
                            tvttilel.setText("Title :"+dta.get("name").getAsString());
                            tvttagline.setText("Tagline :"+dta.get("tagline").getAsString());
                            tvtovervie.setText("Overview :"+dta.get("overview").getAsString());
                            tvtvoteavrage.setText("Rating :"+dta.get("vote_average").getAsString());
                            tvtpopularity.setText("Popularity :"+dta.get("popularity").getAsString());
                            hideLoading();
                        }catch (Exception e){
                            Toast.makeText(TvSeriesDetailsActivity.this, "no data found", Toast.LENGTH_SHORT).show();
                        }


                    }else {
                        Toast.makeText(TvSeriesDetailsActivity.this, "no data found", Toast.LENGTH_SHORT).show();
                    }


                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }
    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);

    }

    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }
}