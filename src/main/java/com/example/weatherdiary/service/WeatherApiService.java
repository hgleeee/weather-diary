package com.example.weatherdiary.service;

import com.example.weatherdiary.domain.RainType;
import com.example.weatherdiary.domain.Weather;
import com.example.weatherdiary.dto.weather.WeatherRequestDto;
import com.example.weatherdiary.dto.weather.WeatherResponseDto;
import com.example.weatherdiary.utils.KmaApiConst;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WeatherApiService implements ApiService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public String getUri(String url, Object requestDto) throws IllegalAccessException {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url);
        WeatherRequestDto weatherRequestDto = (WeatherRequestDto) requestDto;
        for (Field field : weatherRequestDto.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            uriComponentsBuilder.queryParam(field.getName(), field.get(weatherRequestDto));
        }
        return uriComponentsBuilder.build(false).toString();
    }

    @Override
    public Weather transformResponseEntityToObject(Object weatherRequestDto) throws JsonProcessingException, IllegalAccessException {
        Weather weather = new Weather();
        String jsonStr = callApi(weatherRequestDto).getBody();
        Map<String, Object> jsonMap = objectMapper.readValue(jsonStr, new TypeReference<>() {});
        jsonMap = (Map<String, Object>) jsonMap.get("response");
        jsonMap = (Map<String, Object>) jsonMap.get("body");
        jsonMap = (Map<String, Object>) jsonMap.get("items");
        System.out.println(jsonMap.get("item"));
        List<WeatherResponseDto> items = objectMapper.readValue(objectMapper.writeValueAsString(jsonMap.get("item")), new TypeReference<>() {});

        for (WeatherResponseDto item : items) {
            switch (item.getCategory()) {
                case "PTY" : {
                    weather.setRainType(RainType.find((int)item.getObsrValue()));
                    break;
                }
                case "REH" : {
                    weather.setHumidity(item.getObsrValue());
                    break;
                }
                case "T1H" : {
                    weather.setTemperature(item.getObsrValue());
                    break;
                }
                case "RN1" : {
                    weather.setRainAmount(item.getObsrValue());
                    break;
                }
            }
        }
        return weather;
    }

    protected ResponseEntity<String> callApi(Object requestDto) throws IllegalAccessException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", MediaType.APPLICATION_JSON_VALUE);
        return restTemplate.exchange(getUri(KmaApiConst.url, requestDto), HttpMethod.GET, new HttpEntity<>(headers), String.class);
    }
}
