package command;

import msg.CommonMsg;

public class ExitCommand implements Command {
    @Override
    public boolean execute() {
		System.out.print(CommonMsg.EXIT_PROGRAM.get());
        return false;
    }
}
