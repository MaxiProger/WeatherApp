package com.example.kolot.moxysimpleexample.viewPageActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.kolot.moxysimpleexample.R;
import com.example.kolot.moxysimpleexample.adapters.MainRVAdapter;
import com.example.kolot.moxysimpleexample.dto.ResponseWeatherDto;
import com.example.kolot.moxysimpleexample.networking.MyTracker;

public class ViewPagerActivity extends AppCompatActivity implements MainView, LocationListener {

    private ProgressDialog progressDialog;
    private TextView textView, clouds, name, temp;
    private ImageView imageView;
    private ConstraintLayout constraintLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private android.support.v7.widget.Toolbar toolbar;
    private CoordinatorLayout coordinatorLayout;
    private LocationManager locationManager;


    private MainRVAdapter adapter;
    @InjectPresenter
    ViewPagerPresenter presenter;

    MvpDelegate<? extends ViewPagerActivity> mvpDelegate;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        getMvpDelegate().onCreate(savedInstanceState);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_content);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constrait);
        textView = (TextView) findViewById(R.id.weather);
        name = (TextView) findViewById(R.id.country_name);
        temp = (TextView) findViewById(R.id.temperature);
        imageView = (ImageView) findViewById(R.id.image);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait.");

        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayout);

        adapter = new MainRVAdapter(this);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.onRefresh();
            }
        });

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET}, 1);

            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

            }
        }
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

        if(locationManager != null)
            locationManager.removeUpdates(this);
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
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void showWeather(ResponseWeatherDto response) {
        int idInt = getResources().getIdentifier("drawable/i" + response.getWeather().get(0).getIcon(), null, getPackageName());
        Drawable drawable = getResources().getDrawable(idInt);
        imageView.setImageDrawable(drawable);

        if (response.getWeather().get(0).getIcon().contains("n"))
            constraintLayout.setBackgroundResource(R.drawable.night);
        else if (response.getWeather().get(0).getIcon().contains("d"))
            constraintLayout.setBackgroundResource(R.drawable.day);
        toolbar.setTitle("Сейчас: " + response.getWeather().get(0).getDescription() + "\t" + String.valueOf((int) response.getMain().getTemp()));
        temp.setText(String.valueOf((int) response.getMain().getTemp()));
        name.setText(response.getSys().getCountry() + ", " + response.getName());
    }


    @Override
    public void showError(String error) {
        Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showDefaultIcon() {
        int idInt = getResources().getIdentifier("drawable/na", null, getPackageName());
        Drawable drawable = getResources().getDrawable(idInt);
        imageView.setImageDrawable(drawable);
        toolbar.setTitle("Погода");
        temp.setText("");
        name.setText("");
    }

    @Override
    public void Gps() {

        MyTracker myTracker = new MyTracker(this);
        if (myTracker.checkEnabled()) {
            String[] coord = myTracker.getLocation().split("/");
            presenter.getCoordinates(coord[0], coord[1]);
        } else {
            Log.e("provider:", "unabled");
            Snackbar.make(coordinatorLayout, "Can't get coordinates", Snackbar.LENGTH_LONG).show();
            hideProgress();
        }
    }



    @Override
    public void onRefresh() {
        adapter.getDays();
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

}
