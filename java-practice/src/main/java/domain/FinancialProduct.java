package domain;

import dto.UpdateResult;

public abstract class FinancialProduct {
    private long balance;

    public FinancialProduct(long initialBalance) {
        this.balance = initialBalance;
    }
	public long getBalance() {
		return this.balance;
	}

    public boolean deposit(long amount) {
        if (amount > 0) {
            this.balance += amount;
            return true;
        }
        return false;
    }
    public boolean withdraw(long amount) {
        if (amount > 0 && amount <= this.balance) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    public abstract UpdateResult updateValue();

    public String toString() {
        return "[잔액]   : " + this.balance + "원\n";
    }
}
