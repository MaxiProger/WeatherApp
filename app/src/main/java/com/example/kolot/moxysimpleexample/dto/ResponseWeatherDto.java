package com.example.kolot.moxysimpleexample.dto;

import java.util.ArrayList;

/**
 * Created by kolot on 08.02.2018.
 */

public class ResponseWeatherDto {
    private ArrayList<WeatherDto> weather;
    private MainDto main;
    private int visibility;
    private CloudsDto clouds;
    private SysDto sys;
    private String name;

    public ArrayList<WeatherDto> getWeather() {
        return weather;
    }

    public MainDto getMain() {
        return main;
    }

    public int getVisibility() {
        return visibility;
    }

    public CloudsDto getClouds() {
        return clouds;
    }

    public SysDto getSys() {
        return sys;
    }

    public String getName() {
        return name;
    }
}
