package com.example.kolot.moxysimpleexample.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kolot.moxysimpleexample.ForecastForADayActivity;
import com.example.kolot.moxysimpleexample.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kolot on 18.02.2018.
 */

public class MainRVAdapter extends RecyclerView.Adapter<MainRVAdapter.ViewHolder> {

    private ArrayList<String> days = new ArrayList<>();
    private ArrayList<String> datesArrayList = new ArrayList<>();
    private Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_days, parent, false));
    }

    public MainRVAdapter(Context context){
        this.context = context;
        getDays();
    }

    //Полчаю 2 списка: дни и даты на 7 дней

    public void getDays(){
        days.clear();
        datesArrayList.clear();
        for (int i = 0; i<6; i++){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, +i);
            Date date = calendar.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE/yyy-MM-dd");
            String dateString = dateFormat.format(date);
            String[]dateArray = dateString.split("/");
            if (i==0)
                dateArray[0] = "сегодня, " + dateArray[0];
            if(i==1)
                dateArray[0] = "завтра, " + dateArray[0];
            days.add(dateArray[0]);
            datesArrayList.add(dateArray[1]);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.textView.setText(days.get(position));
        holder.date.setText(datesArrayList.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent i = new Intent( context, ForecastForADayActivity.class);

                View sharedView = holder.cardView;
                String transitionName = context.getString(R.string.cardViewday);

                Log.e("send date: ", datesArrayList.get(position));

                i.putExtra("day", days.get(position));
                i.putExtra("date", datesArrayList.get(position));
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) context, sharedView, transitionName);
                context.startActivity(i, activityOptions.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView textView, date;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.main_cardview_days);
            textView = (TextView) itemView.findViewById(R.id.main_textview_dayName);
            date = (TextView) itemView.findViewById(R.id.main_textview_date);
        }
    }
}
