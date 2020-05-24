package ru.timmson.moex.dto;

import lombok.Data;

import java.util.List;

@Data
public class RawSecurities {
    private final List<String> columns;
    private final List<List<String>> data;
}
