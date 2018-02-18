package com.example.kolot.moxysimpleexample;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.kolot.moxysimpleexample.dto.ResponseForecastDto;
import com.example.kolot.moxysimpleexample.networking.GPSTracker;
import com.example.kolot.moxysimpleexample.networking.WeatherRequest;

/**
 * Created by kolot on 18.02.2018.
 */
@InjectViewState
public class ForecastActivityPresenter extends MvpPresenter <ForecastView> implements WeatherRequest.DataInteractor<ResponseForecastDto>{

    public ForecastActivityPresenter(){
        getViewState().getMainIntent();
        getViewState().Gps();
    }

    @Override
    public void onData(ResponseForecastDto response) {
        getViewState().setAdapter(response);
    }

    @Override
    public void onError(String message) {

    }

    public void getCoordinates(GPSTracker tracker) {
        WeatherRequest weatherRequest = new WeatherRequest();
        weatherRequest.setCaller(this);
        weatherRequest.setCaller(this);
        weatherRequest.getForecast(String.valueOf(tracker.getLatitude()), String.valueOf(tracker.getLongitude()));
    }
}
