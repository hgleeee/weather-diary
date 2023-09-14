package com.example.weatherdiary.domain.weather;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class Weather {

    private RainType rainType;  // 강수 형태
    private double humidity;    // 습도
    private double rainAmount;  // 강수량
    private double temperature; // 기온

    /**
     * 일기장에 날씨가 한 줄로 들어가도록 할 것.
     * 습도는 습함, 건조함
     * @return 위의 내용을 담은 날씨 description
     */

    // TODO
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("강우/강설 상황 ").append(rainType.getCurrentState());
        sb.append(", 습도는 ").append(humidity);
        sb.append(", 강수량은 ").append(rainAmount);
        sb.append(", 기온은 ").append(temperature).append("입니다.");
        return sb.toString();
    }
}
