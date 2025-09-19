package command.handle;

import command.BackCommand;
import command.Command;
import command.DepositCommand;
import command.ShowSavingsInfoCommand;
import command.WithdrawCommand;
import service.FinancialService;
import util.MenuHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HandleSavingsAccountMenuCommand implements Command {

    private final Scanner sc;
	private final Map<Integer, Command> savingsCommands;

    public HandleSavingsAccountMenuCommand(FinancialService service, Scanner sc) {
        this.sc = sc;
		savingsCommands = new HashMap<>();
		initCommands(service);
    }

	private void initCommands(FinancialService service) {
		savingsCommands.put(1, new ShowSavingsInfoCommand(service));
		savingsCommands.put(2, new DepositCommand(service, sc));
		savingsCommands.put(3, new WithdrawCommand(service, sc));
		savingsCommands.put(4, new BackCommand());
	}

    @Override
    public boolean execute() {
        String menuTitle = "\n[예금 메뉴] 1.정보 확인 | 2.입금 | 3.출금 | 4.뒤로가기\n>> 작업을 선택하세요: ";
        MenuHandler menuHandler = new MenuHandler(sc, savingsCommands, menuTitle);
        menuHandler.run();
        return true;
    }
}
