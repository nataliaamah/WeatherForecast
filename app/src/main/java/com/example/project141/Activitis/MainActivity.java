package com.example.project141.Activitis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project141.Adapters.HourlyAdapters;
import com.example.project141.Domains.Hourly;
import com.example.project141.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterHourly;
    private RecyclerView recyclerView;

    final String App_id = "ce972698021a576848adc796b307085d";
    final String weather_url = "https://home.openweathermap.org/data/2.5/weather";
    final long min_time = 5000;
    final float min_distance = 1000;
    final int request_code = 101;

    String locationProvider = LocationManager.GPS_PROVIDER;

    TextView location, description, temperature, tempHL, amountRain, windSpeed, humidity;
    ImageView imageView;
    ImageView search;
    LinearLayout cityFind;
    LocationManager mLocationManager;
    LocationListener mLocationListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerview();
        setVariable();

        // set date
        String date = new SimpleDateFormat("E MMM dd | HH.mm a", Locale.getDefault()).format(new Date());
        TextView timeDate = findViewById(R.id.timeDate);
        timeDate.setText(date);

        location = findViewById(R.id.location);
        description = findViewById(R.id.description);
        temperature = findViewById(R.id.temperature);
        tempHL = findViewById(R.id.tempHL);
        amountRain = findViewById(R.id.amountRain);
        windSpeed = findViewById(R.id.windSpeed);
        humidity = findViewById(R.id.humidityPercent);
        search = findViewById(R.id.search);
        cityFind = findViewById(R.id.cityFind);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onResume();
            }
        });

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Intent mIntent = getIntent();
        String city = getText()
        if(city!=null)
        {
            getWeatherForNewCity(city);
        }
        else  getWeatherForCurrentLocation();
    }

    private void getWeatherForNewCity(String city)
    {
        RequestParams params=new RequestParams();
        params.put("q",city);
        params.put("appid",App_id);
        letsdoSomeNetworking(params);

    }

    private void getWeatherForCurrentLocation() {

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

            }
        };


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},request_code);
            return;
        }
        mLocationManager.requestLocationUpdates(locationProvider, min_time, min_distance, mLocationListener);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if(requestCode==request_code)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(MainActivity.this,"Location get Successfully",Toast.LENGTH_SHORT).show();
                getWeatherForCurrentLocation();
            }
            else
            {
                //user denied the permission
            }
        }


    }



    private  void letsdoSomeNetworking(RequestParams params)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(weather_url,params,new JsonHttpResponseHandler()
        {

            public void onSuccess(int statusCode, PreferenceActivity.Header[] headers, JSONObject response) {

                Toast.makeText(MainActivity.this,"Data Get Success",Toast.LENGTH_SHORT).show();

                weatherData weatherD=weatherData.fromJson(response);
                updateUI(weatherD);


                // super.onSuccess(statusCode, headers, response);
            }



            public void onFailure(int statusCode, PreferenceActivity.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });



    }

    private  void updateUI(weatherData weather){
        temperature.setText(weather.getmTemperature()+"℃");
        location.setText(weather.getMcity());
        description.setText(weather.getmWeatherType());
        int resourceID = getResources().getIdentifier(weather.getMicon(),"drawable",getPackageName());
        imageView.setImageResource(resourceID);
        tempHL.setText(weather.getTempHL());
        windSpeed.setText(weather.getWindSpeed());
        amountRain.setText(weather.getAmountRain());
        humidity.setText(weather.getHumidity()+"%");


    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mLocationManager!=null)
        {
            mLocationManager.removeUpdates(mLocationListener);
        }
    }



    private void setVariable() {
        TextView next7dayBtn = findViewById(R.id.nextBtn);
        next7dayBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, FutureActivity.class)));
    }

    private void initRecyclerview() {
        ArrayList<Hourly> items = new ArrayList<>();

        items.add(new Hourly("9 pm", 28, "cloudy"));
        items.add(new Hourly("11 pm", 29, "sunny"));
        items.add(new Hourly("12 pm", 30, "wind"));
        items.add(new Hourly("1 am", 29, "rainy"));
        items.add(new Hourly("2 am", 27, "storm"));

        recyclerView = findViewById(R.id.listView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adapterHourly = new HourlyAdapters(items);
        recyclerView.setAdapter(adapterHourly);
    }


}