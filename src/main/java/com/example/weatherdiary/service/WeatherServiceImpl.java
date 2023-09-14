package com.example.weatherdiary.service;

import com.example.weatherdiary.domain.weather.RainType;
import com.example.weatherdiary.domain.weather.Weather;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Override
    public Weather getWeatherInfo(WeatherRequestDto weatherRequestDto) throws IllegalAccessException, JsonProcessingException {
        HttpEntity<?> entity = getHttpEntity();
        ResponseEntity<?> responseEntity = restTemplate.exchange(getUri(weatherRequestDto), HttpMethod.GET, entity, Object.class);
        return transformResponseEntityToWeather(responseEntity);
    }

    private Weather transformResponseEntityToWeather(ResponseEntity<?> responseEntity) throws JsonProcessingException {
        Weather weather = new Weather();
        String jsonStr = (String)responseEntity.getBody();
        Map<String, Object> jsonMap = objectMapper.readValue(jsonStr, new TypeReference<>() {});
        jsonMap = objectMapper.readValue((String)jsonMap.get("items"), new TypeReference<>() {});
        List<WeatherResponseDto> items =
                objectMapper.readValue((String) jsonMap.get("item"), new TypeReference<>() {});

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

    private HttpEntity<?> getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }

    private String getUri(WeatherRequestDto weatherRequestDto) throws IllegalAccessException {
        String url = KmaApiConst.url;
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url);
        for (Field field : weatherRequestDto.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            uriComponentsBuilder.queryParam(field.getName(), field.get(weatherRequestDto));
        }
        return uriComponentsBuilder.build(false).toString();
    }
}
