package com.example.kolot.moxysimpleexample.api;

import com.example.kolot.moxysimpleexample.dto.ResponseForecastDto;
import com.example.kolot.moxysimpleexample.dto.ResponseWeatherDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kolot on 08.02.2018.
 */

public interface WeatherApi {
    @GET("/data/2.5/weather?units=metric&lang=ru")
     Call<ResponseWeatherDto> getWeather(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("appid") String appId);

    @GET("/data/2.5/forecast?units=metric&lang=ru")
    Call<ResponseForecastDto> getForecast(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("appid") String appId);
}
