package com.example.kolot.moxysimpleexample;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.kolot.moxysimpleexample.adapters.RVAdapter;
import com.example.kolot.moxysimpleexample.dto.ResponseForecastDto;
import com.example.kolot.moxysimpleexample.networking.GPSTracker;

public class ForecastForADayActivity extends AppCompatActivity implements ForecastView {

    private TextView dayTextView;
    private RecyclerView recyclerView;
    private MvpDelegate<ForecastForADayActivity> mvpDelegate;
    private String date;
    private RVAdapter adapter;

    @InjectPresenter
    ForecastActivityPresenter  presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_for_aday);
        recyclerView = (RecyclerView) findViewById(R.id.forecast_recyclerview);

        getMvpDelegate().onCreate(savedInstanceState);
        dayTextView = (TextView) findViewById(R.id.forecast_textview_dayName);

        adapter = new RVAdapter(this);

        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayout);

        adapter = new RVAdapter(this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        getMvpDelegate().onAttach();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getMvpDelegate().onDetach();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMvpDelegate().onAttach();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getMvpDelegate().onDetach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        getMvpDelegate().onDestroyView();

        if (isFinishing()) {
            getMvpDelegate().onDestroy();
        }
    }



    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        getMvpDelegate().onSaveInstanceState(outState);
        getMvpDelegate().onDetach();
    }


    public MvpDelegate getMvpDelegate() {
        if (mvpDelegate == null) {
            mvpDelegate = new MvpDelegate<>(this);
        }
        return mvpDelegate;
    }

    @Override
    public void getMainIntent() {
        Intent i = getIntent();
        String day = i.getExtras().getString("day");
        date  = i.getExtras().getString("date");
        dayTextView.setText(day);
    }

    @Override
    public void setAdapter(ResponseForecastDto forecastDto) {
        adapter.setForecastDto(date, forecastDto);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void Gps() {
        GPSTracker tracker = new GPSTracker(this);
        if (!tracker.canGetLocation()) {
            return ;
        } else {
            presenter.getCoordinates(tracker);
        }
    }

}
