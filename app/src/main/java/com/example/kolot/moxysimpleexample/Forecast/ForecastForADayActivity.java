package com.example.kolot.moxysimpleexample.Forecast;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.kolot.moxysimpleexample.R;
import com.example.kolot.moxysimpleexample.adapters.RVAdapter;
import com.example.kolot.moxysimpleexample.dto.ResponseForecastDto;
import com.example.kolot.moxysimpleexample.networking.MyTracker;

public class ForecastForADayActivity extends AppCompatActivity implements ForecastView {

    private TextView dayTextView;
    private RecyclerView recyclerView;
    private MvpDelegate<ForecastForADayActivity> mvpDelegate;
    private String date;
    private LocationManager locationManager;
    private RVAdapter adapter;

    @InjectPresenter
    ForecastActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_for_aday);
        recyclerView = (RecyclerView) findViewById(R.id.forecast_recyclerview);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

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
        date = i.getExtras().getString("date");
        dayTextView.setText(day);
    }

    @Override
    public void setAdapter(ResponseForecastDto forecastDto) {
        adapter.setForecastDto(date, forecastDto);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void Gps() {
        MyTracker myTracker = new MyTracker(this);
        if (myTracker.checkEnabled()) {
            String[] coord = myTracker.getLocation().split("/");
            presenter.getCoordinates(coord[0], coord[1]);
        } else {
            Log.e("provider:", "unabled");
        }

    }

    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) this,
                    Manifest.permission.INTERNET)) {

            } else {
                ActivityCompat.requestPermissions((Activity) this,
                        new String[]{Manifest.permission.INTERNET}, 1);

            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions((Activity) this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

            }
        }
    }

}
