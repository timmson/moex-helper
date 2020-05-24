package ru.timmson.invest.moex.model;

import lombok.Getter;
import lombok.extern.java.Log;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

@Log
@Getter
public class ProfitableBond extends Bond implements Comparable<ProfitableBond> {

    protected final float remainingTotalCouponValue;
    protected final float profitValue;

    public ProfitableBond(Bond b) {
        super(b.name, b.secId, b.faceValue, b.currentValue, b.couponPeriod, b.couponValue, b.couponCurrentValue, b.maturityDate);
        this.remainingTotalCouponValue = remainCouponsCount * couponValue + couponCurrentValue;
        this.profitValue = calculateProfit();
    }

    public static ProfitableBond create(Bond b) {
        return new ProfitableBond(b);
    }

    protected float calculateProfit() {
        try {
            return (this.faceValue - this.currentValue + this.remainingTotalCouponValue) * 365 * 100 / (this.currentValue * this.remainingDays);
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
