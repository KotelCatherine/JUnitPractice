package ru.cotel.catherine;

/*
a7b76e3c606d48effb4e257c3ff96b01
 */

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    private static final String API_KEY = "a7b76e3c606d48effb4e257c3ff96b01";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    public static void main(String[] args) {

        JFrame frame = new JFrame("Weather");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 300);
        frame.setLayout(new BorderLayout());

        JTextField cityInput = new JTextField();
        frame.add(cityInput, BorderLayout.NORTH);

        JTextArea weatherOutPut = new JTextArea();
        weatherOutPut.setEditable(false);
        frame.add(new JScrollPane(weatherOutPut), BorderLayout.CENTER);

        JButton fetchWeatherButton = new JButton("See");
        frame.add(fetchWeatherButton, BorderLayout.SOUTH);

        fetchWeatherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = cityInput.getText().trim();
                if (city.isEmpty()) {
                    weatherOutPut.setText("Enter city name!");
                    return;
                }else {
                    weatherOutPut.setText("Cant enter ");
                }
                try {
                    String weatherData = getWeatherData(city);
                    if (weatherData != null) {
                        weatherOutPut.setText(parsAndDisplayWeather(weatherData));
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        frame.setVisible(true);

    }

    private static String parsAndDisplayWeather(String weatherData) {
        try {
            JSONObject jsObject = new JSONObject(weatherData);
            String cityName = jsObject.getString("name");
            JSONObject main = jsObject.getJSONObject("main");
            double temperature = main.getDouble("temp");
            double fellsLike = main.getDouble("feels_like");
            int humidity = main.getInt("humidity");
            JSONObject weather = jsObject.getJSONArray("weather").getJSONObject(0);
            String description = weather.getString("description");
            return "City: " + cityName +
                    "\nTemperature: " + temperature +
                    "\nFeels like: " + fellsLike+
                    "\nHumidity: " + humidity + "%" +
                    "\nDescription: " + description;
        }catch (Exception e){
            return "Error data";
        }
    }

    private static String getWeatherData(String city) throws Exception {
        OkHttpClient client = new OkHttpClient();
        String url = BASE_URL + "?q=" + city + "&appid=" + API_KEY + "&units=metric&lang=ru";

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            } else {
                System.out.println("Error" + response.code() + " " + response.message());
                return null;
            }
        }
    }

}