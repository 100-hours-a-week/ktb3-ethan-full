package service;

import domain.Fund;
import dto.FundCreationStatus;
import dto.FundLiquidationResult;
import dto.FundLiquidationStatus;
import dto.FundUpdateResult;
import msg.FundMsg;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InvestmentService {
    private List<Fund> funds;
    private AccountService accountService;

    public InvestmentService(AccountService accountService) {
        this.funds = new ArrayList<>();
        this.accountService = accountService;
    }

    public FundCreationStatus createFund(String fundName, long amount, int riskLevel) {
        if (amount <= 0) {
            return FundCreationStatus.INVALID_AMOUNT;
        }
        if (accountService.withdrawForInvestment(amount)) {
            funds.add(new Fund(amount, fundName, riskLevel));
            return FundCreationStatus.SUCCESS;
        } else {
            return FundCreationStatus.INSUFFICIENT_FUNDS;
        }
    }

    public FundLiquidationResult liquidateFund(int index) {
        if (index >= 0 && index < funds.size()) {
            Fund fund = funds.remove(index);
            final long returnAmount = fund.getBalance();
            accountService.depositFromInvestment(returnAmount);
            // 성공 시, 상태와 결과 데이터를 DTO에 담아 반환
            return new FundLiquidationResult(FundLiquidationStatus.SUCCESS, fund.getProductName(), returnAmount);
        } else {
            // 실패 시, 실패 상태만 담아 반환
            return new FundLiquidationResult(FundLiquidationStatus.INVALID_INDEX, null, 0);
        }
    }

    public String showAllFunds() {
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

    public List<FundUpdateResult> updateAllFundValues() {
        return funds.stream()
                .map(fund -> (FundUpdateResult) fund.updateValue())
                .collect(Collectors.toList());
    }
}
