package ru.timmson.invest.moex.model;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProfitableBondShould {

    private static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of("0", "0", 10.1131f),
                Arguments.of("0.003", "0", -3.2392f),
                Arguments.of("0", "0.13", -5.1973f),
                Arguments.of("0.003", "0.13", -18.5050f)
        );
    }

    @ParameterizedTest
    @MethodSource("data")
    void calculateProfitWithoutBrokerFee(String feeValue, String taxValue, float profitValue) {
        final var profitableBond = getProfitableBond(feeValue, taxValue);

        final var result = profitableBond.getProfitValue();

        assertEquals(profitValue, result, 0.0001f);
    }

    @NotNull
    private ProfitableBond getProfitableBond(String feeValue, String taxValue) {
        return new ProfitableBond(
                Bond.builder()
                        .faceValue("87.0")
                        .currentValue("99.979885", "0")
                        .couponPeriod("91")
                        .couponValue("2.3")
                        .couponCurrentValue("2.12")
                        .maturityDate("2021-03-26")
                        .feeValue(feeValue)
                        .taxValue(taxValue)
                        .build(),
                LocalDate.parse("2021-03-18"));
    }


}
