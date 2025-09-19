package command.handle;

import command.BackCommand;
import command.Command;
import command.CreateFundCommand;
import command.ShowAllFundsCommand;
import service.FinancialService;
import util.MenuHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HandleFundMenuCommand implements Command {
    private final Scanner scanner;
	private final Map<Integer, Command> fundCommands;

    public HandleFundMenuCommand(FinancialService service, Scanner scanner) {
        this.scanner = scanner;
		fundCommands = new HashMap<>();
		initCommands(service);
    }
	private void initCommands(FinancialService service) {
		fundCommands.put(1, new ShowAllFundsCommand(service));
		fundCommands.put(2, new CreateFundCommand(service, scanner));
		fundCommands.put(3, new HandleLiquidateFundCommand(service, scanner));
		fundCommands.put(4, new BackCommand());
	}

    @Override
    public boolean execute() {
        String menuTitle = "\n[펀드 메뉴] 1.보유 펀드 확인 | 2.신규 펀드 생성 | 3.펀드 해지 | 4.뒤로가기\n>> 작업을 선택하세요: ";
        MenuHandler menuHandler = new MenuHandler(scanner, fundCommands, menuTitle);
        menuHandler.run();
        return true;
    }
}
