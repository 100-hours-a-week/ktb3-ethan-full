package command;

import service.FinancialService;

public class ShowAllFundsCommand implements Command {
    private final FinancialService financialService;

    public ShowAllFundsCommand(FinancialService financialService) {
        this.financialService = financialService;
    }

    @Override
    public boolean execute() {
		System.out.print(financialService.showAllFunds());;
        return true;
    }
}
