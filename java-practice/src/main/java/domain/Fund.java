package domain;

import dto.FundUpdateResult;
import dto.UpdateResult;

import java.util.Random;

public class Fund extends InvestmentProduct {

    private final Random random;

    public Fund(long initialValue, String productName, RiskLevel riskLevel, Random random) {
        super(initialValue, productName, riskLevel);
        this.random = random;
    }

    @Override
    public UpdateResult updateValue() {
        long currentBalance = getBalance();
        if (currentBalance == 0) {
            return new FundUpdateResult(this.productName, 0, 0, 0, false);
        }

        double maxProfitRate = this.riskLevel.getMaxProfitRate();
        double maxLossRate = this.riskLevel.getMaxLossRate();

        double rateRange = maxProfitRate + maxLossRate;

        final double rate = (this.random.nextDouble() * rateRange - maxLossRate) / 100.0;
        final long change = (long) (currentBalance * rate);

        boolean isBankrupt = false;
		deposit(change);

        return new FundUpdateResult(this.productName, change, rate, getBalance(), isBankrupt);
    }

    @Override
    public String toString() {
        return "-----< 펀드 >-----\n"
                + super.toString();
    }
}
