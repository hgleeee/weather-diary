package com.example.weatherdiary.service;

import com.example.weatherdiary.dto.weather.WeatherRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

public interface ApiService {

    String getUri(String url, Object requestDto) throws IllegalAccessException;

    Object transformResponseEntityToObject(Object requestDto) throws JsonProcessingException, IllegalAccessException;
}
