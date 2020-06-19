package ru.timmson.invest.moex.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BondShould {

    @Test
    void calculateTotalValue() {
        final var bond = Bond.builder()
                .faceValue("1000.0")
                .currentValue("104.33")
                .couponPeriod("91")
                .couponValue("19.32")
                .couponCurrentValue("8.0")
                .maturityDate(LocalDate.now().plusYears(5).toString())
                .build();

        final var result = bond.getTotalValue();

        assertEquals(1051.3f, result);
    }

}
