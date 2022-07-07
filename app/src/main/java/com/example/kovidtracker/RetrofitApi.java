package com.example.kovidtracker;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitApi {
    @GET("LATEST") // https://api.apify.com/v2/key-value-stores/6t65lJVfs3d8s6aKc/records/LATEST?disableRedirect=true
    Call<Statistics> getStatistics();
}