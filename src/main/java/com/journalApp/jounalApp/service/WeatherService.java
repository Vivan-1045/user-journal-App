package com.journalApp.jounalApp.service;

import com.journalApp.jounalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    private final static String key = "634dc83f2861dc5d7071d023c8093b7a";
    private final static String API = "http://api.weatherstack.com/current?access_key=KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getTemprature(String city) throws InterruptedException {
        String finalAPI = API.replace("KEY",key).replace("CITY",city);
        try {
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
            return response.getBody();
        }catch (HttpClientErrorException.TooManyRequests ex){
            System.out.println("Rate limit hit. Try again later.");
            Thread.sleep(1000);
        }
        return null;
    }
}
