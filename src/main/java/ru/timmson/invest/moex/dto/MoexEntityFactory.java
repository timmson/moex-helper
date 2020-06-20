package ru.timmson.invest.moex.dto;

import com.google.gson.GsonBuilder;
import lombok.extern.java.Log;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Log
public class MoexEntityFactory {

    private static final GsonBuilder gsonBuilder = new GsonBuilder();

    public static List<Map<String, String>> createMoexSecurityResponse(String source) {
        try {
            final var securities = gsonBuilder.create().fromJson(source, MoexSecurityResponse.class).getSecurities();
            final var mapper = new MoexEntityMapper(securities.getColumns());
            return securities
                    .getData()
                    .parallelStream()
                    .map(mapper::rowToMap)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.log(Level.SEVERE, "Error", e);
        }

        return Collections.emptyList();
    }

    protected static class MoexEntityMapper {
        private final List<String> columns;

        public MoexEntityMapper(List<String> columns) {
            this.columns = columns;
        }

        public Map<String, String> rowToMap(List<String> row) {
            final var map = new HashMap<String, String>();
            for (var i = 0; i < row.size(); i++) {
                map.put(columns.get(i), row.get(i));
            }
            return Collections.unmodifiableMap(map);
        }
    }
}
