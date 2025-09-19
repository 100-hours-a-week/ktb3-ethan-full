package service;

import dto.*;

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

	public String showSavingsAccountInfo() {
		return accountService.showAccountInfo();
	}

    public long getPlayerCash() {
        return playerService.getCash();
    }

    public TransactionStatus depositToSavings(long amount) {
        return accountService.deposit(amount);
    }

    public TransactionStatus withdrawFromSavings(long amount) {
        return accountService.withdraw(amount);
    }

	public String showAllFunds() {
		return investmentService.showAllFunds();
	}






    public FundCreationStatus createFund(String fundName, long amount, int riskLevel) {
        return investmentService.createFund(fundName, amount, riskLevel);
    }

    public FundLiquidationResult liquidateFund(int index) {
        return investmentService.liquidateFund(index);
    }



    public AccountUpdateResult updateSavingsAccount() {
        return accountService.updateValue();
    }

    public List<FundUpdateResult> updateAllFunds() {
        return investmentService.updateAllFundValues();
    }
}
