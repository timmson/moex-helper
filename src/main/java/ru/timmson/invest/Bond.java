package ru.timmson.invest;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

@Getter
@ToString
class Bond {
    private final String name;
    private final String secId;
    private final float faceValue;
    private final float currentValue;
    private final int couponPeriod;
    private final float couponValue;
    private final float couponCurrentValue;
    private final LocalDate maturityDate;
    private final int remainingDays;
    private final int remainCouponsCount;
    private final float remainingTotalCouponValue;
    private final float profitValue;

    Bond(String name, String secId, float faceValue, float currentValue, int couponPeriod, float couponValue, float couponCurrentValue, LocalDate maturityDate) {
        this.name = name;
        this.secId = secId;
        this.faceValue = faceValue;
        this.currentValue = (currentValue != 0 ? currentValue / 100 : 1) * faceValue;
        this.couponPeriod = couponPeriod;
        this.couponValue = couponValue;
        this.couponCurrentValue = couponCurrentValue;
        this.maturityDate = maturityDate;
        this.remainingDays = Long.valueOf(ChronoUnit.DAYS.between(LocalDate.now(), maturityDate)).intValue();
        this.remainCouponsCount = couponPeriod > 0 ? (remainingDays / couponPeriod) : 0;
        this.remainingTotalCouponValue = remainCouponsCount * couponValue + couponCurrentValue;
        this.profitValue = calculateProfit();
    }

    public static BondBuilder builder() {
        return new BondBuilder();
    }

    protected float calculateProfit() {
        try {
            return (faceValue - currentValue + remainingTotalCouponValue) * 365 * 100 / (currentValue * remainingDays);
        } catch (Exception e) {
            System.out.println(secId);
            return 0;
        }
    }

    @ToString
    public static class BondBuilder {
        private String name;
        private String secId;
        private float faceValue;
        private float currentValue;
        private int couponPeriod;
        private float couponValue;
        private float couponCurrentValue;
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

        public BondBuilder faceValue(String faceValue) {
            this.faceValue = parseFloat(faceValue);
            return this;
        }

        public BondBuilder currentValue(String currentValue) {
            this.currentValue = parseFloat(Objects.requireNonNullElse(currentValue, "0"));
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

        public BondBuilder maturityDate(String maturityDate) {
            this.maturityDate = maturityDate != null && !maturityDate.equals("0000-00-00") ? LocalDate.parse(maturityDate) : LocalDate.now().plusYears(3);
            return this;
        }

        public Bond build() {
            return new Bond(name, secId, faceValue, currentValue, couponPeriod, couponValue, couponCurrentValue, maturityDate);
        }

    }
}
