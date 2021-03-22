package ru.timmson.invest.moex.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Security {

    Map<String, String> info;
    Map<String, String> marketData;

    public static Security of(Map<String, String> info, Map<String, String> marketData) {
        return new Security(info, marketData);
    }

}
