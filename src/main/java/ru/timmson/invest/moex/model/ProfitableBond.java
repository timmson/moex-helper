package ru.timmson.invest.moex.model;

import lombok.Getter;
import lombok.extern.java.Log;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;

@Log
@Getter
public class ProfitableBond extends Bond implements Comparable<ProfitableBond> {

    protected final int remainingDays;
    protected final int remainCouponsCount;
    protected final float remainingTotalCouponValue;
    protected final float profitValue;

    public ProfitableBond(Bond b) {
        this(b, LocalDate.now());
    }

    public ProfitableBond(Bond b, LocalDate onDate) {
        super(b.name, b.secId, b.sector, b.currency, b.faceValue, b.currentValue, b.couponPeriod, b.couponValue, b.couponCurrentValue, b.totalValue, b.maturityDate);
        this.remainingDays = Long.valueOf(ChronoUnit.DAYS.between(onDate, maturityDate)).intValue();
        this.remainCouponsCount = couponPeriod > 0 ? (remainingDays / couponPeriod) + 1 : 0;
        this.remainingTotalCouponValue = remainCouponsCount * couponValue;
        this.profitValue = calculateProfit();
    }

    public static ProfitableBond create(Bond b) {
        return new ProfitableBond(b);
    }

    protected float calculateProfit() {
        try {
            return ((this.faceValue + this.remainingTotalCouponValue - this.totalValue) / this.totalValue) * 100f * (365f / this.remainingDays);
        } catch (Exception e) {
            log.log(Level.SEVERE, this.secId, e);
        }
        return 0;
    }

    @Override
    public int compareTo(@NotNull ProfitableBond o) {
        return Float.compare(profitValue, o.profitValue);
    }

    public String toString() {
        return "ProfitableBond(" + super.toString() + ", profitValue=" + this.getProfitValue() + ")";
    }
}
