package service;

import dto.*;
import msg.AccountMsg;
import msg.FundMsg;

import java.util.List;

public class FinancialService {
    private final PlayerService playerService;
    private final AccountService accountService;
    private final InvestmentService investmentService;

    public FinancialService() {
        this.playerService = new PlayerService();
        this.accountService = new AccountService(playerService);
        this.investmentService = new InvestmentService(accountService);
    }

	public synchronized String showSavingsAccountInfo() {
		return accountService.showAccountInfo();
	}

    public synchronized long getPlayerCash() {
        return playerService.getCash();
    }

    public synchronized TransactionStatus depositToSavings(long amount) {
        return accountService.deposit(amount);
    }

    public synchronized TransactionStatus withdrawFromSavings(long amount) {
        return accountService.withdraw(amount);
    }

	public synchronized String showAllFunds() {
		return investmentService.showAllFunds();
	}

    public synchronized FundCreationStatus createFund(String fundName, long amount, int riskLevel) {
        return investmentService.createFund(fundName, amount, riskLevel);
    }

    public synchronized FundLiquidationResult liquidateFund(int index) {
        return investmentService.liquidateFund(index);
    }

    public synchronized String passOneWeek() {
        List<FundUpdateResult> fundUpdateResults = investmentService.updateAllFundValues();
		StringBuilder sb = new StringBuilder();
        for (FundUpdateResult result : fundUpdateResults) {
            String message;
            if (result.isBankrupt()) {
                message = FundMsg.BANKRUPT.format(result.productName());
            } else if (result.changeAmount() == 0) {
                message = FundMsg.NO_CHANGE.format(result.productName());
            } else {
                message = FundMsg.UPDATE.format(result.productName(), result.changeAmount(), result.rate() * 100, result.finalBalance());
            }
			sb.append(message);
        }
		return sb.toString();
	}

    public synchronized String processMonthlyInterest() {
        AccountUpdateResult result = accountService.updateValue();
		if (result != null && result.interest() > 0) {
			return AccountMsg.UPDATE_INTEREST.format(result.bankName(), result.interest(), result.finalBalance());
		}
		return "";
    }
}
