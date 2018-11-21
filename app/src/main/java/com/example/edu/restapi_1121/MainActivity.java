package com.example.edu.restapi_1121;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String weather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button)findViewById(R.id.buttonLondon)).setOnClickListener(this);
    }

    class OpenWeatherAPITask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {

            String id = params[0];
            String uriString = "http://api.openweathermap.org/data/2.5/weather"+"?q="+id+"&appid=d756ccfbeec60091522c50297705940b";

            URL url = null;
            String bweather = null;
            try {
                url = new URL(uriString);
                HttpURLConnection urlConnection = null;
                urlConnection = (HttpURLConnection)url.openConnection();
                InputStream in = null;
                in = urlConnection.getInputStream();
                byte[] buffer = new byte[1000];
                in.read(buffer);
                bweather = new String(buffer);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bweather;
        }
    }


    @Override
    public void onClick(View v) {
        OpenWeatherAPITask task = new OpenWeatherAPITask();
        try {
            weather = task.execute("London").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TextView textView = findViewById(R.id.textViewResult);
        textView.setText(weather);
    }
}
