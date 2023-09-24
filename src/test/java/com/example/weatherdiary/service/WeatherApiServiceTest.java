package com.example.weatherdiary.service;

import com.example.weatherdiary.domain.RainType;
import com.example.weatherdiary.domain.Weather;
import com.example.weatherdiary.dto.weather.WeatherRequestDto;
import com.example.weatherdiary.dto.weather.WeatherResponseDto;
import com.example.weatherdiary.utils.KmaApiConst;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherApiServiceTest {

    @InjectMocks
    private WeatherApiService weatherApiService;

    @Mock
    private RestTemplate restTemplate;

    @Spy
    private ObjectMapper objectMapper;

    private WeatherRequestDto weatherRequestDto;

    private String base_date, base_time;
    private int nx, ny;
    private String jsonResponse;

    @BeforeEach
    void init() {
        base_date = "20230101";
        base_time = "1200";
        nx = 55;
        ny = 130;
        weatherRequestDto = new WeatherRequestDto(KmaApiConst.credentialKey,
                            "JSON", base_date, base_time, nx, ny);

        jsonResponse = "{\n" +
                "    \"response\": {\n" +
                "        \"header\": {\n" +
                "            \"resultCode\": \"00\",\n" +
                "            \"resultMsg\": \"NORMAL_SERVICE\"\n" +
                "        },\n" +
                "        \"body\": {\n" +
                "            \"dataType\": \"JSON\",\n" +
                "            \"items\": {\n" +
                "                \"item\": [\n" +
                "                    {\n" +
                "                        \"baseDate\": \"20230101\",\n" +
                "                        \"baseTime\": \"1200\",\n" +
                "                        \"category\": \"PTY\",\n" +
                "                        \"nx\": 55,\n" +
                "                        \"ny\": 130,\n" +
                "                        \"obsrValue\": \"1\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"baseDate\": \"20230101\",\n" +
                "                        \"baseTime\": \"1200\",\n" +
                "                        \"category\": \"REH\",\n" +
                "                        \"nx\": 55,\n" +
                "                        \"ny\": 130,\n" +
                "                        \"obsrValue\": \"30\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"baseDate\": \"20230101\",\n" +
                "                        \"baseTime\": \"1200\",\n" +
                "                        \"category\": \"RN1\",\n" +
                "                        \"nx\": 55,\n" +
                "                        \"ny\": 130,\n" +
                "                        \"obsrValue\": \"10\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"baseDate\": \"20230101\",\n" +
                "                        \"baseTime\": \"1200\",\n" +
                "                        \"category\": \"T1H\",\n" +
                "                        \"nx\": 55,\n" +
                "                        \"ny\": 130,\n" +
                "                        \"obsrValue\": \"20.0\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"baseDate\": \"20230101\",\n" +
                "                        \"baseTime\": \"1200\",\n" +
                "                        \"category\": \"UUU\",\n" +
                "                        \"nx\": 55,\n" +
                "                        \"ny\": 130,\n" +
                "                        \"obsrValue\": \"-1.6\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"baseDate\": \"20230101\",\n" +
                "                        \"baseTime\": \"1200\",\n" +
                "                        \"category\": \"VEC\",\n" +
                "                        \"nx\": 55,\n" +
                "                        \"ny\": 130,\n" +
                "                        \"obsrValue\": \"95\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"baseDate\": \"20230101\",\n" +
                "                        \"baseTime\": \"1200\",\n" +
                "                        \"category\": \"VVV\",\n" +
                "                        \"nx\": 55,\n" +
                "                        \"ny\": 130,\n" +
                "                        \"obsrValue\": \"0.1\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"baseDate\": \"20230101\",\n" +
                "                        \"baseTime\": \"1200\",\n" +
                "                        \"category\": \"WSD\",\n" +
                "                        \"nx\": 55,\n" +
                "                        \"ny\": 130,\n" +
                "                        \"obsrValue\": \"1.7\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            \"pageNo\": 1,\n" +
                "            \"numOfRows\": 10,\n" +
                "            \"totalCount\": 8\n" +
                "        }\n" +
                "    }\n" +
                "}";
    }

    @Test
    void getUri() throws IllegalAccessException {
        String uri = weatherApiService.getUri("http://www.test.com", weatherRequestDto);

        Assertions.assertEquals("http://www.test.com?serviceKey=" + KmaApiConst.credentialKey
                + "&dataType=JSON&base_date=20230101&base_time=1200&nx=55&ny=127", uri);
    }

    @Test
    void transformResponseEntityToObject() throws JsonProcessingException, IllegalAccessException {
        // given
        List<WeatherResponseDto> items = new ArrayList<>();
        items.add(new WeatherResponseDto(base_date, base_time, "PTY", nx, ny, 1.0));
        items.add(new WeatherResponseDto(base_date, base_time, "REH", nx, ny, 30.0));
        items.add(new WeatherResponseDto(base_date, base_time, "T1H", nx, ny, 20.0));
        items.add(new WeatherResponseDto(base_date, base_time, "RN1", nx, ny, 10.0));

        // when
        when(weatherApiService.callApi(weatherRequestDto)).thenReturn(new ResponseEntity<>(jsonResponse, HttpStatus.OK));

        // then
        Weather weather = weatherApiService.transformResponseEntityToObject(weatherRequestDto);
        assertEquals(weather.getRainType(), RainType.RAIN);
        assertEquals(weather.getHumidity(), 30.0d);
        assertEquals(weather.getTemperature(), 20.0d);
        assertEquals(weather.getRainAmount(), 10.0d);
    }
}