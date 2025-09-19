package command;

import service.FinancialService;

public class ShowSavingsInfoCommand implements Command {
    private final FinancialService financialService;

    public ShowSavingsInfoCommand(FinancialService financialService) {
        this.financialService = financialService;
    }

    @Override
    public boolean execute() {
		System.out.println(financialService.showSavingsAccountInfo());
        return true;
    }
}
