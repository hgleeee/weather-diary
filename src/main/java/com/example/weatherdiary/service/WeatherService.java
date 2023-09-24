package com.example.weatherdiary.service;

import com.example.weatherdiary.domain.Weather;
import com.example.weatherdiary.dto.weather.WeatherRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface WeatherService {

    public Weather getWeatherInfo(WeatherRequestDto weatherRequestDto) throws IllegalAccessException, JsonProcessingException;
}
