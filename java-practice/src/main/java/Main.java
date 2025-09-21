import service.FinancialService;
import util.TimeFlow;
import view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        FinancialService service = new FinancialService();

        TimeFlow timeFlow = new TimeFlow(service);
        Thread timeThread = new Thread(timeFlow);
        timeThread.setDaemon(true);
        timeThread.start();

        ConsoleView view = new ConsoleView(service);
        view.run();
    }
}
