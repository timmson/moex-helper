package ru.timmson.invest.moex;

import org.junit.jupiter.api.Test;
import ru.timmson.invest.moex.dto.MoexEntityFactory;

import java.util.Objects;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoexDataFactoryShould {

    @Test
    void createMoexSecuritiesFromJson() {

        final var scanner = new Scanner(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("securities-example.json")));
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
        }

        final var source = sb.toString();

        final var result = MoexEntityFactory.createMoexSecurityResponse(source);

        assertEquals(2, result.size());
    }

    @Test
    void NotCreateMoexSecuritiesFromInvalidJson() {
        final var source = "---";
        final var result = MoexEntityFactory.createMoexSecurityResponse(source);

        assertEquals(0, result.size());
    }
}
