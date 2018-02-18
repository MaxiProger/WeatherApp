package com.example.kolot.moxysimpleexample.dto;

import java.util.ArrayList;

/**
 * Created by kolot on 09.02.2018.
 */

 public class ForecastDto {
    private MainDto main;
    private ArrayList<WeatherDto >weather;
    private CloudsDto clouds;
    private SysDto sys;

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    private String dt_txt;

    public MainDto getMain() {
        return main;
    }

    public ArrayList<WeatherDto > getWeather() {
        return weather;
    }

    public CloudsDto getClouds() {
        return clouds;
    }

    public SysDto getSys() {
        return sys;
    }

    public String getDt_txt() {
        return dt_txt;
    }
}
