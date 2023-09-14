package com.example.weatherdiary.dto.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherRequestDto {

    private String serviceKey;
    private String dataType;
    private String base_date;
    private String base_time;
    private int nx;
    private int ny;
}
