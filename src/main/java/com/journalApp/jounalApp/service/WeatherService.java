package com.journalApp.jounalApp.service;

import com.journalApp.jounalApp.api.response.WeatherResponse;
import com.journalApp.jounalApp.cache.app_Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    @Value("${weather_api_key}")
    private String key;

    @Autowired
    private app_Cache appCache;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getTemprature(String city) throws InterruptedException {
        WeatherResponse res = redisService.getRes("weather_of_" + city, WeatherResponse.class);
        if(res != null){
            return res;
        }
        else{

            String finalAPI = "http://api.weatherstack.com/current?access_key=<KEY>&query=<CITY>".replace("<KEY>",key).replace("<CITY>",city);
            try {
                ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
                WeatherResponse body = response.getBody();
                if(body != null){
                    redisService.set("weather_of_" + city,body,300L);
                }
                return body;
            }catch (HttpClientErrorException.TooManyRequests ex){
                System.out.println("Rate limit hit. Try again later.");
                Thread.sleep(1000);
            }
            return null;
//            String finalAPI = appCache.CACHE.get("weather_api").replace("<KEY>",key).replace("<CITY>",city);
//            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
//            if (response != null){
//                redisService.set("weather_of_" + city,response,300L);
//            }
//            return response.getBody();
        }

    }
}
