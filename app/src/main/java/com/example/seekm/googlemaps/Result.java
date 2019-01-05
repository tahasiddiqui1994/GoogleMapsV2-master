package com.example.seekm.googlemaps;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import static android.os.Build.VERSION_CODES.P;

public class Result extends AppCompatActivity {

    TextView longitude,latitude;
    final static String TAG = "RESULT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        latitude = (TextView)findViewById(R.id.latitude);
        longitude = (TextView)findViewById(R.id.longitude);


        Intent intent = getIntent();
        String Lat = intent.getStringExtra("Latitude");
        String Long = intent.getStringExtra("Longitude");
        latitude.setText("Latitude: " + Lat);
        longitude.setText("Longitude: " + Long);
        final String API = getResources().getString(R.string.weather_api);

        String url = "http://api.worldweatheronline.com/premium/v1/weather.ashx?key=" + API + "&q=" + Lat + "," + Long + "&num_of_days=5&tp=3&format=json";


        Ion.with(this)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject data) {
                        try{
                            JsonObject details = (JsonObject) data.getAsJsonObject("data");
//                            JsonArray weather = (JsonArray) details.getAsJsonArray("weather");
                            JsonArray current_condition = (JsonArray) details.getAsJsonArray("current_condition");
                            //JsonArray array = (JsonArray) current_condition.get(0);
                            JsonObject zeroElement = (JsonObject) current_condition.get(4);
                            //JsonArray url = (JsonArray) zeroElement.getAsJsonArray("weatherIconUrl") ;
                            //JsonObject value = (JsonObject) url.get(0);
//                            JsonObject url =  (JsonObject) weatherIconUrl.get(0);
                            Toast.makeText(Result.this,zeroElement.toString(),Toast.LENGTH_LONG).show();
                            System.out.println("sssssssssss" + zeroElement);
                        }catch (Exception error){

                            Log.d(TAG, "onCompleted: " + error.getMessage() + error.getStackTrace());
                        }

                    }
                });
    }
}
