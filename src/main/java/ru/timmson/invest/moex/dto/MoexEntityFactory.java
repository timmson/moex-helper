package ru.timmson.invest.moex.dto;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import lombok.extern.java.Log;

import java.util.Optional;
import java.util.logging.Level;

@Log
public class MoexEntityFactory {

    private static final GsonBuilder gsonBuilder = new GsonBuilder();

    public static Optional<MoexSecurityResponse> createMoexSecurityResponse(String source) {
        try {
            return Optional.of(gsonBuilder.create().fromJson(source, MoexSecurityResponse.class));
        } catch (JsonSyntaxException e) {
            log.log(Level.SEVERE, "Error", e);
        }
        return Optional.empty();
    }
}
