package view;

import command.*;
import command.handle.HandleFundMenuCommand;
import command.handle.HandleSavingsAccountMenuCommand;
import msg.CommonMsg;
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
        mainCommands.put(2, new HandleFundMenuCommand(service, sc));
        mainCommands.put(3, new ExitCommand());
    }

    public void run() {
		String menuTitle = CommonMsg.START_HEADER.get();
		MenuHandler menuHandler = new MenuHandler(sc, mainCommands, menuTitle);
		menuHandler.run();
        sc.close();
    }
}
