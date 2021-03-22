package ru.timmson.invest.moex;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalMoexApiShould {

    @Test
    void returnBondsFromSecuritiesExampleJson() {
        final var fileName = "securities-example.json";

        final var result = new LocalMoexClient(fileName, "0", "0").getBonds();

        assertEquals(2, result.size());
    }
}
