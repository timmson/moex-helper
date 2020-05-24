package ru.timmson.invest.tinkoff;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TinkoffApiShould {

    @Test
    void createClientForTokenAndDefaultLogger() {
        final var token = "XXX";

        final var result = TinkoffApi.createClient(token);

        assertNotNull(result);
    }
}
