package ru.timmson.invest.moex.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class MoexData {

    /*private final List<String> metadata;*/
    private final List<String> columns;
    private final List<List<String>> data;

    public List<Map<String, String>> toListOfMap() {
        return this.data.stream().map(this::rowToMap).collect(Collectors.toList());
    }

    protected Map<String, String> rowToMap(List<String> row) {
        return IntStream.range(0, row.size()).boxed().collect(Collectors.toMap(columns::get, (i) -> Optional.ofNullable(row.get(i)).orElse("")));
    }

}
