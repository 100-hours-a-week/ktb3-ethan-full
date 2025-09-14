package view;

import service.FinancialService;

import java.util.Scanner;

public class ConsoleView {

    private final FinancialService service;
    private final Scanner sc;

    public ConsoleView(FinancialService service) {
        this.service = service;
        this.sc = new Scanner(System.in);
    }

    public void run() {
        System.out.println("미니 금융 시뮬레이터에 오신 것을 환영합니다!");
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\n[메인 메뉴] 1.예금 계좌 | 2.펀드 투자 | 3.한달 지내기 | 4.종료");
            System.out.print(">> 행동을 선택하세요: ");
            final int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    handleSavingsAccountMenu();
                    break;
                case 2:
                    handleFundMenu();
                    break;
                case 3:
                    service.passOneMonth();
                    break;
                case 4:
                    isRunning = false;
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 1~4 사이의 숫자를 입력해주세요.");
            }
        }
        System.out.println("프로그램을 종료합니다.");
        sc.close();
    }

    private void handleSavingsAccountMenu() {
        System.out.println("\n[예금 메뉴] 1.정보 확인 | 2.입금 | 3.출금 | 4.뒤로가기");
        System.out.print(">> 작업을 선택하세요: ");
        final int choice = sc.nextInt();
        switch (choice) {
            case 1:
                service.showSavingsAccountInfo();
                break;
            case 2:
                System.out.printf("입금할 금액(보유 현금 %d): ", service.playerGetCash());
                long depositAmount = sc.nextLong();
                service.depositToSavings(depositAmount);
                break;
            case 3:
                System.out.print("출금할 금액: ");
                long withdrawAmount = sc.nextLong();
                service.withdrawFromSavings(withdrawAmount);
                break;
            case 4:
                System.out.println("메인 메뉴로 돌아갑니다.");
                break;
            default:
                System.out.println("잘못된 입력입니다.");
        }
    }

    // 펀드 서브 메뉴 처리
    private void handleFundMenu() {
        System.out.println("\n[펀드 메뉴] 1.보유 펀드 확인 | 2.신규 펀드 생성 | 3.펀드 해지 | 4.뒤로가기");
        System.out.print(">> 작업을 선택하세요: ");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                service.showAllFunds();
                break;
            case 2:
                System.out.print("신규 펀드 이름: ");
                String fundName = sc.next();
                System.out.print("투자할 금액 (예금 계좌에서 출금): ");
                final long amount = sc.nextLong();
                System.out.println("투자할 펀드 등급\n(1등급: -7 ~ 10%), (2등급: -14% ~ 20%), (3등급: -21% ~ 30%)");
                final int riskLevel = sc.nextInt();
                service.createFund(fundName, amount, riskLevel);
                break;
            case 3:
                service.showAllFunds();
                System.out.print("해지할 펀드 번호 입력: ");
                int index = sc.nextInt() - 1;
                service.liquidateFund(index);
                break;
            case 4:
                System.out.println("메인 메뉴로 돌아갑니다.");
                break;
            default:
                System.out.println("잘못된 입력입니다.");
        }
    }
}
