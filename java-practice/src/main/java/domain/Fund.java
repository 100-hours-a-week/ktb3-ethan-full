package domain;

import dto.FundUpdateResult;
import dto.UpdateResult;

import java.util.Random;

public class Fund extends InvestmentProduct {
    private final double MAX_PROFIT_RATE_PER_RISK;
    private final double MAX_LOSS_RATE_PER_RISK;

    public Fund(long initialValue, String productName, int riskLevel) {
        super(initialValue, productName, riskLevel);
        // riskLevel에 따라 변동폭을 인스턴스 변수로 설정
        this.MAX_PROFIT_RATE_PER_RISK = riskLevel * 4.0;
        this.MAX_LOSS_RATE_PER_RISK = riskLevel * 3.0;
    }

    @Override
    public UpdateResult updateValue() {
        if (balance == 0) {
            return new FundUpdateResult(this.productName, 0, 0, 0, false);
        }

        double rateRange = MAX_PROFIT_RATE_PER_RISK + MAX_LOSS_RATE_PER_RISK;
        final double rate = (new Random().nextDouble() * rateRange - MAX_LOSS_RATE_PER_RISK) / 100.0;
        final long change = (long) (balance * rate);

        balance += change;

        boolean isBankrupt = false;
        if (balance <= 0) {
            balance = 0;
            isBankrupt = true;
        }

        return new FundUpdateResult(this.productName, change, rate, this.balance, isBankrupt);
    }

    public long getBalance() {
        return this.balance;
    }

    @Override
    public String toString() {
        return "-----< 펀드 >-----\n"
                + super.toString();
    }
}
