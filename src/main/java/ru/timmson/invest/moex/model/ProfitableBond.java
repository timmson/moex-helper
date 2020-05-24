package ru.timmson.invest.moex.model;

import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Getter
@ToString
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
            return (faceValue - currentValue + remainingTotalCouponValue) * 365 * 100 / (currentValue * remainingDays);
        } catch (Exception e) {
            System.out.println(secId);
            return 0;
        }
    }

    @Override
    public int compareTo(@NotNull ProfitableBond o) {
        return Float.compare(profitValue, o.profitValue);
    }
}
