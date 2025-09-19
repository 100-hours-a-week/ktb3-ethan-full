package command.handle;

import command.Command;
import dto.FundLiquidationResult;
import msg.FundMsg;
import service.FinancialService;
import java.util.Scanner;

public class HandleLiquidateFundCommand implements Command {
	private final FinancialService financialService;
	private final Scanner scanner;

	public HandleLiquidateFundCommand(FinancialService financialService, Scanner scanner) {
		this.financialService = financialService;
		this.scanner = scanner;
	}

	@Override
	public boolean execute() {
		System.out.print(financialService.showAllFunds());
		System.out.print(FundMsg.LIQUIDATE_PROMPT.get());
		final int index = scanner.nextInt() - 1;

		FundLiquidationResult result = financialService.liquidateFund(index);

		String out = switch (result.status()) {
			case SUCCESS -> FundMsg.LIQUIDATE_SUCCESS.format(result.productName(), result.returnAmount());
			case INVALID_INDEX-> FundMsg.LIQUIDATE_FAIL_UNKNOWN.get();
		};

		System.out.print(out);

		return true;
	}
}
