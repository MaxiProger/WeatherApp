package com.example.kolot.moxysimpleexample.viewPageActivity;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.kolot.moxysimpleexample.dto.ResponseWeatherDto;
import com.example.kolot.moxysimpleexample.networking.GPSTracker;
import com.example.kolot.moxysimpleexample.networking.WeatherRequest;

/**
 * Created by kolot on 15.02.2018.
 */
@InjectViewState
public class ViewPagerPresenter extends MvpPresenter<MainView> implements WeatherRequest.DataInteractor<ResponseWeatherDto> {
    public ViewPagerPresenter() {
        getViewState().showProgress();
        getViewState().Gps();
    }

    public void getCoordinates(GPSTracker tracker) {
        WeatherRequest weatherRequest = new WeatherRequest();
        weatherRequest.setCaller(this);
        weatherRequest.setCaller(this);
        weatherRequest.getWeather(String.valueOf(tracker.getLatitude()), String.valueOf(tracker.getLongitude()));
    }

    @Override
    public void onData(ResponseWeatherDto response) {
        getViewState().hideProgress();
        getViewState().showWeather(response);
    }

    @Override
    public void onError(String message) {
        getViewState().hideProgress();
        getViewState().showDefaultIcon();
        getViewState().showError(message);
    }

    public void onRefresh() {
        getViewState().showProgress();
        getViewState().Gps();
        getViewState().onRefresh();
    }
}
