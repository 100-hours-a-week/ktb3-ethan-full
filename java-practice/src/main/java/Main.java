import service.FinancialService;
import view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        FinancialService service = new FinancialService();
        ConsoleView view = new ConsoleView(service);
        view.run();
    }
}