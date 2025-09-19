package service;

import domain.SavingAccount;
import dto.AccountUpdateResult;
import dto.TransactionStatus;

public class AccountService {
    private final SavingAccount account;
    private final PlayerService playerService;

    public AccountService(PlayerService playerService) {
        this.account = new SavingAccount(0, "오라클 은행", "21-4748-3647", 0.03);
        this.playerService = playerService;
    }

	public String showAccountInfo() {
		return account.toString();
	}

    public TransactionStatus deposit(long amount) {
        if (amount <= 0) {
            return TransactionStatus.INVALID_AMOUNT;
        }
        if (playerService.spendCash(amount) && account.deposit(amount)) {
            return TransactionStatus.SUCCESS;
        } else {
            return TransactionStatus.INSUFFICIENT_CASH;
        }
    }

    public TransactionStatus withdraw(long amount) {
        if (amount <= 0) {
            return TransactionStatus.INVALID_AMOUNT;
        }
        if (account.withdraw(amount) && playerService.earnCash(amount)) {
            return TransactionStatus.SUCCESS;
        } else {
            return TransactionStatus.INSUFFICIENT_FUNDS;
        }
    }


    
    public boolean withdrawForInvestment(long amount) {
        return account.withdraw(amount);
    }

    public void depositFromInvestment(long amount) {
        account.deposit(amount);
    }



    public AccountUpdateResult updateValue() {
        return (AccountUpdateResult) account.updateValue();
    }
}
