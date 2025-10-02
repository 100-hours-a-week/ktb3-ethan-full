package domain;

import dto.AccountUpdateResult;
import dto.UpdateResult;

public class SavingAccount extends BankAccount {
    private final double interestRate;

    public SavingAccount(long initialBalance, String bankName, String accountNumber, double interestRate) {
        super(initialBalance, bankName, accountNumber);
        this.interestRate = interestRate;
    }

    @Override
    public UpdateResult updateValue() {
        long interest = (long) (getBalance() * interestRate / 12);
		deposit(interest);

        return new AccountUpdateResult(this.bankName, interest, getBalance());
    }

    @Override
    public String toString() {
        return "-----< 일반 예금 >-----\n"
                + super.toString()
                + "[이자율] : " + (interestRate * 100) + "%\n";
    }
}
