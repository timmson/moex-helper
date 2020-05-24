package ru.timmson.invest.moex.dto;

import lombok.Data;

import java.util.List;

@Data
public class MoexSecurity {
    private final List<String> columns;
    private final List<List<String>> data;
}
