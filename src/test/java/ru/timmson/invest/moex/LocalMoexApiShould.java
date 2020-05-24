package ru.timmson.invest.moex;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalMoexApiShould {

    @Test
    void returnBondsFromFile() {
        final var fileName = "securities-example.json";

        final var result = new LocalMoexClient(fileName).getBonds();

        assertEquals(7, result.size());
    }
}