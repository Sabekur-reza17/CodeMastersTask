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

public class MovieDetailsActivity extends AppCompatActivity {
    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w780";
    ApiInterface apiInterface;
    ImageView back;
    TextView ttilel,tovervie,ttagline,tvoteavrage,tpopularity;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        apiInterface= APIClient.createService(ApiInterface.class);
        progressBar=findViewById(R.id.move_progress);
        back=findViewById(R.id.movede_back);
        ttilel=findViewById(R.id.orgtile);
        ttagline=findViewById(R.id.tagline);
        tovervie=findViewById(R.id.overvie);
        tvoteavrage=findViewById(R.id.voteaverage);
        tpopularity=findViewById(R.id.poulariry);

        Intent intent = getIntent();
        String str = intent.getStringExtra("movieidstr");
        Log.d("movieiddat",str);
        if(Networkcheck.isConnectedToInternet(this)){
            getMOviedeatils(str);
        }else {
            Toast.makeText(this, "check the internet connection", Toast.LENGTH_SHORT).show();
        }

    }
    private void getMOviedeatils(String moviid){

        String apikey="1a97f3b8d5deee1d649c0025f3acf75c";
        Call<JsonObject> movidedetails=apiInterface.getMovidedetails(moviid,apikey);
        showLoading();
        movidedetails.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    JsonObject movieres=response.body();
                    if(movieres !=null){
                        try {
                            String imageulr=BASE_URL_IMG+movieres.get("backdrop_path").getAsString();
                            RequestOptions options = new RequestOptions()
                                    .placeholder(R.drawable.ic_launcher_background)
                                    .error(R.drawable.ic_launcher_background);
                            Glide.with(getApplicationContext()).load(imageulr).apply(options).into(back);
                            ttilel.setText("Title :"+movieres.get("original_title").getAsString());
                            ttagline.setText("Tagline :"+movieres.get("tagline").getAsString());
                            tovervie.setText("Overview :"+movieres.get("overview").getAsString());
                            tvoteavrage.setText("Rating :"+movieres.get("vote_average").getAsString());
                            tpopularity.setText("Popularity :"+movieres.get("popularity").getAsString());
                            hideLoading();
                        }catch (Exception e){
                            Toast.makeText(MovieDetailsActivity.this, "no data found", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(MovieDetailsActivity.this, "no data found", Toast.LENGTH_SHORT).show();
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