package com.example.kolot.moxysimpleexample.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kolot.moxysimpleexample.R;
import com.example.kolot.moxysimpleexample.dto.ForecastDto;
import com.example.kolot.moxysimpleexample.dto.ResponseForecastDto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kolot on 09.02.2018.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView time, weather;
        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

            time = (TextView) itemView.findViewById(R.id.time);
            weather = (TextView) itemView.findViewById(R.id.weather_forecast);
            image = (ImageView) itemView.findViewById(R.id.imageForecast);

        }
    }

    private Context context;
    private ArrayList<ForecastDto> forecastDto = new ArrayList<>();

    public RVAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ForecastDto forecast = forecastDto.get(position);

        String icon = "drawable/i" + forecast.getWeather().get(0).getIcon();
        int idInt = context.getResources().getIdentifier(icon, null, context.getPackageName());
        Drawable drawable = context.getResources().getDrawable(idInt);

        holder.image.setImageDrawable(drawable);
        holder.weather.setText(String.valueOf((int) forecast.getMain().getTemp()) + ", " + forecast.getWeather().get(0).getDescription());
        holder.time.setText(String.valueOf(forecast.getDt_txt()));

    }



    public void setForecastDto(String date, ResponseForecastDto forecastDto) {
        Log.e("setAdapter: ", "setted");

        ArrayList<ForecastDto> resultList = new ArrayList<>();

        SimpleDateFormat ctime = new SimpleDateFormat("HH:MM:SS");
        String currentTime = ctime.format(new Date());

        for (ForecastDto dto: forecastDto.getList()){
            if(dto.getDt_txt().contains(date)) {
                String s = dto.getDt_txt();
                dto.setDt_txt(s.replace(date + " ", ""));
                resultList.add(dto);
            }
        }

        this.forecastDto = resultList;
    }

    @Override
    public int getItemCount() {
        return forecastDto.size();
    }

}
