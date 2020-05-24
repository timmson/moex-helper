package ru.timmson.invest.moex;

import org.junit.jupiter.api.Test;
import ru.timmson.invest.moex.dto.MoexSecurity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalMoexApiShould {

    @Test
    void returnBondsFromFile() {
        final var fileName = "securities-example.json";

        final Optional<MoexSecurity> result = new LocalMoexApi(fileName).getBonds();

        assertEquals(7, result.orElseThrow().getData().size());
    }
}