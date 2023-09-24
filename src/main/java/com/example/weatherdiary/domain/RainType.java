package com.example.weatherdiary.domain;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum RainType {

    NO(0, "비, 눈 없음"),
    RAIN(1, "비"),
    RAIN_SNOW(2, "비, 눈"),
    SNOW(3, "눈"),
    RAIN_DROP(4, "빗방울"),
    RAIN_SNOW_DROP(5, "빗방울, 눈날림"),
    SNOW_DROP(6, "눈날림");

    private final int code;
    private final String currentState;

    RainType(int code, String currentState) {
        this.code = code;
        this.currentState = currentState;
    }

    private static final Map<Integer, RainType> map =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(RainType::getCode, Function.identity())));

    public static RainType find(int code) {
        return map.get(code);
    }
}
