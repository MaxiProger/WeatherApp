package com.example.kolot.moxysimpleexample.dto;

import java.util.ArrayList;

/**
 * Created by kolot on 09.02.2018.
 */

public class ResponseForecastDto extends ResponseWeatherDto {
    private ArrayList<ForecastDto> list;
    private CityDto city;

    public CityDto getCity() {
        return city;
    }

    public ArrayList<ForecastDto> getList() {
        return list;
    }
}
