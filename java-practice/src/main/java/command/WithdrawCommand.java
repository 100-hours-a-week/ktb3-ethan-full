package command;

import dto.TransactionStatus;
import msg.AccountMsg;
import service.FinancialService;
import java.util.Scanner;

public class WithdrawCommand implements Command {
    private final FinancialService financialService;
    private final Scanner sc;

    public WithdrawCommand(FinancialService financialService, Scanner sc) {
        this.financialService = financialService;
        this.sc = sc;
    }

    @Override
    public boolean execute() {
        System.out.print(AccountMsg.WITHDRAW_PROMPT.get());
        final long amount = sc.nextLong();

        TransactionStatus status = financialService.withdrawFromSavings(amount);

		String out = switch (status) {
			case SUCCESS ->
				AccountMsg.WITHDRAW_SUCCESS.format(amount);
			case INSUFFICIENT_FUNDS ->
				AccountMsg.WITHDRAW_FAIL_INSUFFICIENT_BALANCE.get();
			case INVALID_AMOUNT ->
				AccountMsg.WITHDRAW_FAIL_NEGATIVE_AMOUNT.get();
			default ->
				AccountMsg.WITHDRAW_FAIL_UNKNOWN.get();
		};

		System.out.print(out);
        return true;
    }
}
