package ru.timmson.invest.moex.dto;

import com.google.gson.GsonBuilder;
import lombok.extern.java.Log;
import ru.timmson.invest.moex.model.Security;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Log
public class MoexEntityFactory {

    private static final GsonBuilder gsonBuilder = new GsonBuilder();

    public static List<Security> createMoexSecurityResponse(String source) {
        try {
            final var s = gsonBuilder.create().fromJson(source, MoexSecurityResponse.class);
            List<Map<String, String>> info = s.getSecurities().toListOfMap();
            List<Map<String, String>> marketData = s.getMarketdata().toListOfMap();
            return IntStream.range(0, info.size()).mapToObj((i) -> Security.of(info.get(i), marketData.get(i))).collect(Collectors.toList());
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error", e);
        }
        return Collections.emptyList();
    }
}
