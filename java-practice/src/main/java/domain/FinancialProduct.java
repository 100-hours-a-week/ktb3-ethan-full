package domain;

public abstract class FinancialProduct {
    long balance;

    public  FinancialProduct(long initialBalance) {
        this.balance = initialBalance;
    }

    public boolean deposit(long amount) {
        if (amount > 0) {
            this.balance += amount;
            return true;
        }
        return false;
    }
    public boolean withdraw(long amount) {
        if (amount > 0 && this.balance >= amount) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    public abstract void updateValue();

    public String toString() {
        return "[잔액]   : " + this.balance + "원\n";
    }
}
