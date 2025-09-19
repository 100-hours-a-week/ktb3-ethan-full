package command;

import dto.AccountUpdateResult;
import dto.FundUpdateResult;
import msg.AccountMsg;
import msg.FundMsg;
import service.FinancialService;

import java.util.List;

public class PassOneMonthCommand implements Command {
    private final FinancialService financialService;

    public PassOneMonthCommand(FinancialService financialService) {
        this.financialService = financialService;
    }

    @Override
    public boolean execute() {
        StringBuilder sb = new StringBuilder();
        sb.append(FundMsg.MONTH_PASSED_HEADER.get());

        AccountUpdateResult accountResult = financialService.updateSavingsAccount();
        sb.append(AccountMsg.UPDATE_INTEREST.format(
                accountResult.bankName(),
                accountResult.interest(),
                accountResult.finalBalance()));

        List<FundUpdateResult> fundResults = financialService.updateAllFunds();
        for (FundUpdateResult result : fundResults) {
            if (result.changeAmount() == 0 && result.finalBalance() == 0 && !result.isBankrupt()) {
                sb.append(FundMsg.NO_CHANGE.format(result.productName()));
            } else if (result.isBankrupt()) {
                sb.append(FundMsg.BANKRUPT.format(result.productName()));
            } else {
                sb.append(FundMsg.UPDATE.format(
                        result.productName(),
                        result.changeAmount(),
                        result.rate() * 100,
                        result.finalBalance()));
            }
        }
        sb.append(FundMsg.MONTH_PASSED_FOOTER.get());

        System.out.println(sb);
        return true;
    }
}
