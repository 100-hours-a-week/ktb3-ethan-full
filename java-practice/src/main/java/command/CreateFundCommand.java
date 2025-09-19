package command;

import dto.FundCreationStatus;
import service.FinancialService;
import java.util.Scanner;

public class CreateFundCommand implements Command {
    private final FinancialService financialService;
    private final Scanner scanner;

    public CreateFundCommand(FinancialService financialService, Scanner scanner) {
        this.financialService = financialService;
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        System.out.print("신규 펀드 이름: ");
        String fundName = scanner.next();
        System.out.print("투자할 금액 (예금 계좌에서 출금): ");
        final long amount = scanner.nextLong();
        System.out.println("투자할 펀드 등급\n(1등급: -3.0% ~ +4.0%), (2등급: -6.0% ~ +8.0%), (3등급: -9.0% ~ +12.0%)");
        final int riskLevel = scanner.nextInt();

        // 1. 서비스를 호출하고 결과 '상태(Enum)'를 받음
        FundCreationStatus status = financialService.createFund(fundName, amount, riskLevel);

        // 2. 뷰(커맨드)가 상태를 해석하여 적절한 메시지를 '직접' 생성하고 출력
        switch (status) {
            case SUCCESS:
                System.out.printf("펀드 '%s' 생성이 완료되었습니다. (투자금: %,d원)\n", fundName, amount);
                break;
            case INSUFFICIENT_FUNDS:
                System.out.println("펀드 생성 실패: 예금 계좌의 잔액이 부족합니다.");
                break;
            case INVALID_AMOUNT:
                System.out.println("펀드 생성 실패: 투자금은 0보다 커야 합니다.");
                break;
            default:
                System.out.println("알 수 없는 오류로 펀드 생성에 실패했습니다.");
                break;
        }

        return true; // 서브 메뉴 루프를 계속 실행합니다.
    }
}
