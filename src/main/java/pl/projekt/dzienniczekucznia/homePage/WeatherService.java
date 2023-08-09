package pl.projekt.dzienniczekucznia.homePage;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.util.*;

@Service
public class WeatherService {
    public WeatherData getWeatherData() {
        try {
            URL url = new URL("https://api.open-meteo.com/v1/forecast?latitude=53.5879&longitude=17.859&hourly=temperature_2m&timezone=Europe%2FBerlin");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder resoult = new StringBuilder();
            String line;
            while((line = rd.readLine()) != null){
                resoult.append(line);
            }
            rd.close();

            Gson gson = new Gson();
            WeatherData weatherData = gson.fromJson(resoult.toString(), WeatherData.class);
            return weatherData;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Double> getWeatherTemperature(){
        WeatherData weatherData = getWeatherData();
        int targetHourIndex = LocalTime.now().getHour();
        if (weatherData !=null && weatherData.getHourly() != null){
            HourlyData hourlyData = weatherData.getHourly();
            if (targetHourIndex >= 0 && targetHourIndex < hourlyData.getTemperature_2m().size()){
                Double temperature = hourlyData.getTemperature_2m().get(targetHourIndex);
                return Optional.of(temperature);
            }
        }
        return Optional.empty();
    }

}