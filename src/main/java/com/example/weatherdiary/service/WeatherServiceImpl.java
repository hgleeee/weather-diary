package com.example.weatherdiary.service;

import com.example.weatherdiary.domain.Weather;
import com.example.weatherdiary.dto.weather.WeatherRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final ApiService apiService;

    @Override
    public Weather getWeatherInfo(WeatherRequestDto weatherRequestDto) throws IllegalAccessException, JsonProcessingException {
        return (Weather) apiService.transformResponseEntityToObject(weatherRequestDto);
    }
}
