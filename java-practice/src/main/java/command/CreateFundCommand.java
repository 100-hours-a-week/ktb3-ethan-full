package command;

import dto.FundCreationRequestDto;
import dto.FundCreationStatus;
import service.FinancialService;
import view.ConsoleView;

public class CreateFundCommand implements Command {
    private final FinancialService financialService;
    private final ConsoleView view;

    public CreateFundCommand(FinancialService financialService, ConsoleView view) {
        this.financialService = financialService;
        this.view = view;
    }

    @Override
    public boolean execute() {
        FundCreationRequestDto request = view.getFundCreationRequest();

        FundCreationStatus status = financialService.createFund(request);

        view.displayFundCreationResult(status, request.getFundName(), request.getAmount());

        return true; // 서브 메뉴 루프를 계속 실행합니다.
    }
}
