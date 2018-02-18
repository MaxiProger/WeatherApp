package com.example.kolot.moxysimpleexample;

import com.arellomobile.mvp.MvpView;
import com.example.kolot.moxysimpleexample.dto.ResponseForecastDto;

/**
 * Created by kolot on 18.02.2018.
 */

public interface ForecastView extends MvpView {
    void getMainIntent();
    void setAdapter(ResponseForecastDto forecastDto);
    void Gps();
}
