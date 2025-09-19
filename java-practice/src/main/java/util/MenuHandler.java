package util;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import command.Command;
import msg.CommonMsg;

public class MenuHandler {
	private final Scanner sc;
	private final Map<Integer, Command> commands;
	private final String menuTitle;

	public MenuHandler(Scanner sc, Map<Integer, Command> commands, String menuTitle) {
		this.sc = sc;
		this.commands = commands;
		this.menuTitle = menuTitle;
	}

	public void run() {
		boolean isRunning = true;
		while (isRunning) {
			System.out.print(menuTitle);
			try {
				final int choice = sc.nextInt();
				Command command = commands.get(choice);
				if (command != null) {
					isRunning = command.execute();
				} else {
					System.out.print(CommonMsg.INVALID_NUMBER.get());
				}
			} catch (InputMismatchException e) {
				System.out.print(CommonMsg.INVALID_INPUT.get());
				sc.next();
			}
		}
	}
}
