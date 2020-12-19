package com.sabekur2017.codemasterstask.data;

import com.google.gson.GsonBuilder;
import com.sabekur2017.codemasterstask.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
   // private static final String base_url="https://api.themoviedb.org/3/";
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory
                            .create(new GsonBuilder()
                                    .setLenient()
                                    .serializeNulls()
                                    .create()));


    private static Retrofit retrofit = builder.build();

    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass) {
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.writeTimeout(60, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG && !httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);
        }
        builder.client(httpClient.build());
        retrofit = builder.build();
        return retrofit.create(serviceClass);
    }
}
