package util;

import msg.AccountMsg;
import msg.FundMsg;
import service.FinancialService;

public class TimeFlow implements Runnable {

    private final FinancialService financialService;
    private int weekCounter;

    public TimeFlow(FinancialService financialService) {
        this.financialService = financialService;
        this.weekCounter = 0;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(10_000);
				System.out.print(FundMsg.WEEK_PASSED_HEADER.get());
				System.out.print(financialService.passOneWeek());;
				System.out.print(FundMsg.WEEK_PASSED_FOOTER.get());

				++weekCounter;
				if (weekCounter >= 4) {
					System.out.println(AccountMsg.MONTH_PASSED_HEADER.get());
					System.out.println(financialService.processMonthlyInterest());
					System.out.println(AccountMsg.MONTH_PASSED_FOOTER.get());
					weekCounter = 0;
				}

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
