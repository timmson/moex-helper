package ru.timmson.invest.moex.dto;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.util.Optional;

public class MoexSecurityFactory {
    private static final GsonBuilder gsonBuilder = new GsonBuilder();

    public static Optional<MoexSecurityResponse> createMoexSecurityResponse(String source) {
        try {
            return Optional.of(gsonBuilder.create().fromJson(source, MoexSecurityResponse.class));
        } catch (JsonSyntaxException e) {
            //e.printStackTrace();
        }
        return Optional.empty();
    }
}
