package com.journalApp.jounalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class WeatherResponse {
    private Current current;

    @Getter
    @Setter
    public class Current{
        @JsonProperty("observation_time")
        private String observationTime;
        public int temperature;
        @JsonProperty("weather_descriptions")
        public ArrayList<String> weatherDescriptions;
        @JsonProperty("wind_speed")
        private int windSpeed;
        @JsonProperty("wind_dir")
        private String windDir;
        @JsonProperty("feelslike")
        private int feelsLike;
        private int visibility;
        @JsonProperty("is_day")
        private String isDay;
    }


}
