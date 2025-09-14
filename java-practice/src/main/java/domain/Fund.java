package domain;

import java.util.Random;

public class Fund extends InvestmentProduct {
    public Fund(long initialValue, String productName, int riskLevel) {
        super(initialValue, productName, riskLevel);
    }

    @Override
    public void updateValue() {
        // -(risk * 7)% ~ +(risk * 10)% 사이의 무작위 월 수익률 시뮬레이션

        if (balance == 0) {
            System.out.printf("[!!!] '%s' 펀드의 잔액이 0원입니다. 변동사항 없습니다.\n", this.productName);
            return;
        }
        final double rate = ((new Random()).nextDouble() * 17 - 7) / 100.0;
        long change = (long) (balance * rate);

        balance += change;

        if (balance <= 0) {
            System.out.printf("[!!!] %s 펀드의 가치가 0원이 되었습니다. (전액 손실)\n", this.productName);
            balance = 0;
        } else {
            System.out.printf("[알림] %s 펀드의 가치가 %,d원 변동했습니다. (수익률: %.2f%%)\n",
                    this.productName, change, rate * 100);
        }
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
