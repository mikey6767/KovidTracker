package com.example.kovidtracker;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitApi {
    @GET("all") // https://disease.sh/v3/covid-19/all
    Call<Statistics> getStatistics();
}