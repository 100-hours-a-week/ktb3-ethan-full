package view;

import command.*;
import command.handle.HandleFundMenuCommand;
import command.handle.HandleSavingsAccountMenuCommand;
import dto.FundCreationRequestDto;
import dto.FundCreationStatus;
import msg.CommonMsg;
import msg.FundMsg;
import service.FinancialService;
import util.MenuHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleView {

    private final Scanner sc;
    private final Map<Integer, Command> mainCommands;

    public ConsoleView(FinancialService service) {
        this.sc = new Scanner(System.in);
        this.mainCommands = new HashMap<>();
		initCommands(service);
    }

    private void initCommands(FinancialService service) {
        mainCommands.put(1, new HandleSavingsAccountMenuCommand(service, sc));
        mainCommands.put(2, new HandleFundMenuCommand(service, this, sc)); // this(ConsoleView)와 scanner 전달
        mainCommands.put(3, new ExitCommand());
    }

    public void run() {
		String menuTitle = CommonMsg.START_HEADER.get();
		MenuHandler menuHandler = new MenuHandler(sc, mainCommands, menuTitle);
		menuHandler.run();
        sc.close();
    }

    public FundCreationRequestDto getFundCreationRequest() {
        System.out.print(FundMsg.CREATE_FUND_NAME_PROMPT.get());
        String fundName = sc.next();
        System.out.print(FundMsg.CREATE_FUND_AMOUNT_PROMPT.get());
        final long amount = sc.nextLong();
        System.out.println(FundMsg.CREATE_FUND_RISK_LEVEL_PROMPT.get());
        final int riskLevel = sc.nextInt();
        return new FundCreationRequestDto(fundName, amount, riskLevel);
    }

    public void displayFundCreationResult(FundCreationStatus status, String fundName, long amount) {
        switch (status) {
            case SUCCESS:
                System.out.print(FundMsg.CREATE_SUCCESS.format(fundName, amount));
                break;
            case INSUFFICIENT_FUNDS:
                System.out.print(FundMsg.CREATE_FAIL_INSUFFICIENT_FUNDS.get());
                break;
            case INVALID_AMOUNT:
                System.out.print(FundMsg.CREATE_FAIL_INVALID_AMOUNT.get());
                break;
            case INVALID_RISK_LEVEL:
                System.out.print(FundMsg.CREATE_FAIL_INVALID_RISK_LEVEL.get());
                break;
            default:
                System.out.print(FundMsg.CREATE_FAIL_UNKNOWN.get());
                break;
        }
    }
}
