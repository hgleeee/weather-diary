package com.example.weatherdiary.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder

public class Weather {
    private final double chanceOfRain;   // 강수 확률 (%)
    private final double amountOfRain;   // 강수량 (mm)
    private final double humidity;       // 습도 (%)
    private final double highestTemp;    // 최고 기온 (C)
    private final double lowestTemp;     // 최저 기온 (C)

    /**
     * 일기장에 날씨가 한 줄로 들어가도록 할 것.
     * 강수 확률과 강수량을 가지고 맑음, 비올 것 같이 흐림, 비, 눈 등을 판별
     * 습도는 습함, 건조함 / 일교차가 크다 등을 표기할 예정
     * @return 위의 내용을 담은 날씨 description
     */
    // TODO
    @Override
    public String toString() {
        return "";
    }
}
