package com.sih2020.abhyuday.api;

import com.sih2020.abhyuday.models.Places;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("json")
    Call<Places> getHospitals(@Query("location") String location, @Query("radius") int radius, @Query("types") String types, @Query("rankBy") String rankBy, @Query("key") String key);

}
