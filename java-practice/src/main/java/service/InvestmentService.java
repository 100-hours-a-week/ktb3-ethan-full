package service;

import domain.Fund;
import domain.RiskLevel;
import dto.FundCreationStatus;
import dto.FundLiquidationResult;
import dto.FundLiquidationStatus;
import dto.FundUpdateResult;
import msg.FundMsg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class InvestmentService {
    private final List<Fund> funds;
    private final AccountService accountService;
    private final Random random;

    public InvestmentService(AccountService accountService) {
        this.funds = new ArrayList<>();
        this.accountService = accountService;
        this.random = new Random();
    }

    public synchronized FundCreationStatus createFund(String fundName, long amount, int riskLevel) {
        if (amount <= 0) {
            return FundCreationStatus.INVALID_AMOUNT;
        }

        RiskLevel level;
        try {
            level = RiskLevel.of(riskLevel);
        } catch (IllegalArgumentException e) {
            return FundCreationStatus.INVALID_RISK_LEVEL;
        }

        if (accountService.withdrawForInvestment(amount)) {
            funds.add(new Fund(amount, fundName, level, random)); // random 인스턴스 전달
            return FundCreationStatus.SUCCESS;
        } else {
            return FundCreationStatus.INSUFFICIENT_FUNDS;
        }
    }

    public synchronized FundLiquidationResult liquidateFund(int index) {
        if (index >= 0 && index < funds.size()) {
            Fund fund = funds.remove(index);
            final long returnAmount = fund.getBalance();
            accountService.depositFromInvestment(returnAmount);
            return new FundLiquidationResult(FundLiquidationStatus.SUCCESS, fund.getProductName(), returnAmount);
        } else {
            return new FundLiquidationResult(FundLiquidationStatus.INVALID_INDEX, null, 0);
        }
    }

    public synchronized String showAllFunds() {
        StringBuilder sb = new StringBuilder();
        sb.append(FundMsg.HAVE_FUND_HEADER.get());

        if (funds.isEmpty()) {
            sb.append(FundMsg.NOT_HAVE_FUND.get());
        } else {
            for (int i = 0; i < funds.size(); ++i) {
                final Fund fund = funds.get(i);
                sb.append(FundMsg.HAVE_FUND_INFO.format(i + 1, fund.getProductName(), fund.getBalance()));
            }
        }
        sb.append(FundMsg.HAVE_FUND_FOOTER.get());
        return sb.toString();
    }

    public synchronized List<FundUpdateResult> updateAllFundValues() {
        return funds.stream()
                .map(fund -> (FundUpdateResult) fund.updateValue())
                .collect(Collectors.toList());
    }
}
