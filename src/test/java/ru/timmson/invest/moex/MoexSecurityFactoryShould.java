package ru.timmson.invest.moex;

import org.junit.jupiter.api.Test;
import ru.timmson.invest.moex.dto.MoexSecurityFactory;
import ru.timmson.invest.moex.dto.MoexSecurityResponse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoexSecurityFactoryShould {

    @Test
    void createMoexSecuritiesFromJson() throws FileNotFoundException {
        final var scanner = new Scanner(new FileReader("securities-example.json"));
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
        }

        final var source = sb.toString();

        final Optional<MoexSecurityResponse> result = MoexSecurityFactory.createMoexSecurityResponse(source);

        assertEquals(7, result.orElseThrow().getSecurities().getData().size());
    }

    @Test
    void NotCreateMoexSecuritiesFromInvalidJson() throws FileNotFoundException {
        final var source = "---";
        final Optional<MoexSecurityResponse> result = MoexSecurityFactory.createMoexSecurityResponse(source);

        assertTrue(result.isEmpty());
    }
}
