package com.example.kolot.moxysimpleexample.networking;

import android.util.Log;

import com.example.kolot.moxysimpleexample.api.WeatherApi;
import com.example.kolot.moxysimpleexample.dto.ResponseForecastDto;
import com.example.kolot.moxysimpleexample.dto.ResponseWeatherDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kolot on 08.02.2018.
 */

public class WeatherRequest {
    private static final String BASE_URl = "http://api.openweathermap.org/";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    WeatherApi weatherApi = retrofit.create(WeatherApi.class);

    public interface DataInteractor<T> {
        void onData(T response);

        void onError(String message);
    }

    private DataInteractor caller;

    public void setCaller(DataInteractor caller) {
        this.caller = caller;
    }

    public void getWeather(String lat, String lon) {
        weatherApi.getWeather(lat, lon, "59fa177e5bdb6187c8a3d4ec16ff6a7d")
                .enqueue(new Callback<ResponseWeatherDto>() {
                    @Override
                    public void onResponse(Call<ResponseWeatherDto> call, Response<ResponseWeatherDto> response) {

                        caller.onData(response.body());
                    }

                    @Override
                    public void onFailure(Call<ResponseWeatherDto> call, Throwable t) {
                        caller.onError(t.getMessage());
                    }
                });
    }

    public void getForecast( String lat, String lon) {

        weatherApi.getForecast(lat, lon, "59fa177e5bdb6187c8a3d4ec16ff6a7d")
                .enqueue(new Callback<ResponseForecastDto>() {
                    @Override
                    public void onResponse(Call<ResponseForecastDto> call, Response<ResponseForecastDto> response) {
                        caller.onData(response.body());
                    }

                    @Override
                    public void onFailure(Call<ResponseForecastDto> call, Throwable t) {
                        caller.onError(t.getMessage());                    }
                });

    }

}
