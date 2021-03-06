package ru.timmson.invest.moex.model;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

@Getter
@ToString
public class Bond {
    protected final String name;
    protected final String secId;
    protected final String sector;
    protected final String currency;
    protected final float faceValue;
    protected final float currentValue;
    protected final int couponPeriod;
    protected final float couponValue;
    protected final float couponCurrentValue;
    protected final float totalValue;
    protected final LocalDate maturityDate;

    Bond(String name,
         String secId,
         String sector,
         String currency,
         float faceValue,
         float currentValue,
         int couponPeriod,
         float couponValue,
         float couponCurrentValue,
         float totalValue,
         LocalDate maturityDate) {
        this.name = name;
        this.secId = secId;
        this.sector = sector;
        this.currency = currency;
        this.faceValue = faceValue;
        this.currentValue = currentValue;
        this.couponPeriod = couponPeriod;
        this.couponValue = couponValue;
        this.couponCurrentValue = couponCurrentValue;
        this.totalValue = totalValue;
        this.maturityDate = maturityDate;
    }

    public static BondBuilder builder() {
        return new BondBuilder();
    }

    @ToString
    public static class BondBuilder {
        private String name;
        private String secId;
        private String sector;
        private String currency;
        private float faceValue;
        private float currentValue;
        private int couponPeriod;
        private float couponValue;
        private float couponCurrentValue;
        private float totalValue;
        private float feeValue;
        private float taxValue;
        private LocalDate maturityDate;

        BondBuilder() {
        }

        public BondBuilder name(String name) {
            this.name = name;
            return this;
        }

        public BondBuilder secId(String secId) {
            this.secId = secId;
            return this;
        }

        public BondBuilder sector(String sector) {
            this.sector = sector;
            return this;
        }

        public BondBuilder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public BondBuilder faceValue(String faceValue) {
            this.faceValue = parseFloat(faceValue);
            return this;
        }

        public BondBuilder currentValue(String lastValue, String currentValue) {
            var currentValueTmp = 100f;
            if (lastValue != null && !lastValue.isBlank()) {
                currentValueTmp = parseFloat(lastValue);
            } else if (currentValue != null && !currentValue.isBlank()) {
                currentValueTmp = parseFloat(currentValue);
            }
            this.currentValue = (currentValueTmp != 0 ? currentValueTmp / 100 : 1) * faceValue;
            return this;
        }

        public BondBuilder couponPeriod(String couponPeriod) {
            this.couponPeriod = parseInt(couponPeriod);
            return this;
        }

        public BondBuilder couponValue(String couponValue) {
            this.couponValue = parseFloat(couponValue);
            return this;
        }

        public BondBuilder couponCurrentValue(String couponCurrentValue) {
            this.couponCurrentValue = parseFloat(couponCurrentValue);
            return this;
        }

        public BondBuilder feeValue(String feeValue) {
            this.feeValue = parseFloat(feeValue);
            return this;
        }

        public BondBuilder taxValue(String feeValue) {
            this.taxValue = parseFloat(feeValue);
            return this;
        }

        public BondBuilder maturityDate(String maturityDate) {
            final var defaultMaturityDate = LocalDate.now().plusYears(5);
            if (maturityDate == null || maturityDate.equals("0000-00-00")) {
                this.maturityDate = defaultMaturityDate;
            } else {
                final var maturityDateCandidate = LocalDate.parse(maturityDate);
                this.maturityDate = ChronoUnit.YEARS.between(LocalDate.now(), maturityDateCandidate) > 5 ? defaultMaturityDate : maturityDateCandidate;
            }
            return this;
        }

        //51,178045
        //1.1571001
        public Bond build() {
            this.totalValue = this.couponCurrentValue + this.currentValue * (1 + this.feeValue);
            return new Bond(name, secId, sector, currency, faceValue, currentValue, couponPeriod, couponValue * (1 - this.taxValue), couponCurrentValue, totalValue, maturityDate);
        }
    }
}
