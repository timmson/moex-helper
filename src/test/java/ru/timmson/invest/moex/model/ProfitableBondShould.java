package ru.timmson.invest.moex.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProfitableBondShould {

    @Test
    void calculateProfit() {
        final var profitableBond = new ProfitableBond(
                Bond.builder()
                        .faceValue("1000.0")
                        .currentValue("104.33")
                        .couponPeriod("91")
                        .couponValue("19.32")
                        .couponCurrentValue("8.0")
                        .maturityDate(LocalDate.now().plusYears(5).toString())
                        .build(), LocalDate.parse("2020-05-24")
        );

        final var result = profitableBond.getProfitValue();

        assertEquals(6.8462815f, result);
    }
}
