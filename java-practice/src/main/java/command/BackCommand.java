package command;

import msg.CommonMsg;

public class BackCommand implements Command {
    @Override
    public boolean execute() {
        System.out.print(CommonMsg.BACK_MENU.get());
        return false;
    }
}
