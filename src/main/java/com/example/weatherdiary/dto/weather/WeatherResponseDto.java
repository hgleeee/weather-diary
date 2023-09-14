package com.example.weatherdiary.dto.weather;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class WeatherResponseDto {

    private String baseDate;
    private String baseTime;
    private String category;
    private int nx;
    private int ny;
    private double obsrValue;
}
