package ru.timmson.invest.moex.dto;

import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoexEntityFactoryShould {

    @Test
    void createMoexSecurityResponseFromJSON() {
        final var scanner = new Scanner(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("securities-example.json")));
        StringBuilder source = new StringBuilder();
        while (scanner.hasNextLine()) {
            source.append(scanner.nextLine());
        }

        final var result = MoexEntityFactory.createMoexSecurityResponse(source.toString());

        assertEquals("TQOD", result.get(0).getInfo().get("BOARDID"));
    }
}
