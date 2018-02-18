package com.example.kolot.moxysimpleexample.viewPageActivity;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.kolot.moxysimpleexample.dto.ResponseWeatherDto;

/**
 * Created by kolot on 03.02.2018.
 */

public interface MainView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showProgress();
    void hideProgress();
    void showWeather(ResponseWeatherDto response);
    void showError(String error);
    void showDefaultIcon();
    void Gps();
    void onRefresh();
}
