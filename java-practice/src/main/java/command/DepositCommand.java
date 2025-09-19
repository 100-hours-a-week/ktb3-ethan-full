package command;

import dto.TransactionStatus;
import msg.AccountMsg;
import service.FinancialService;
import java.util.Scanner;

public class DepositCommand implements Command {
    private final FinancialService financialService;
    private final Scanner sc;

    public DepositCommand(FinancialService financialService, Scanner sc) {
        this.financialService = financialService;
        this.sc = sc;
    }

    @Override
    public boolean execute() {
        System.out.print(AccountMsg.DEPOSIT_PROMPT.format(financialService.getPlayerCash()));
        final long amount = sc.nextLong();

        TransactionStatus status = financialService.depositToSavings(amount);

		String out = switch (status) {
			case SUCCESS ->
				AccountMsg.DEPOSIT_SUCCESS.format(amount);
			case INSUFFICIENT_CASH ->
				AccountMsg.DEPOSIT_FAIL_INSUFFICIENT_MONEY.format(financialService.getPlayerCash());
			case INVALID_AMOUNT ->
				AccountMsg.DEPOSIT_FAIL_NEGATIVE_AMOUNT.get();
			default ->
				AccountMsg.DEPOSIT_FAIL_UNKNOWN.get();
		};

		System.out.print(out);
        return true;
    }
}
