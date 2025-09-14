package service;

import domain.Fund;
import domain.Player;
import domain.SavingAccount;

import java.util.ArrayList;
import java.util.List;

public class FinancialService {
    private SavingAccount account;
    private List<Fund> funds;
    private Player player;

    public FinancialService() {
        this.account = new SavingAccount(0, "오라클 은행", "21-4748-3647",0.03);
        this.funds = new ArrayList<>();
        this.player = new Player();
    }

    public long playerGetCash() {
        return player.getCash();
    }

    public void depositToSavings(long amount) {
        if (player.spendCash(amount)) {
            account.deposit(amount);
            System.out.printf("현금 %,d원을 예금 계좌에 입금했습니다.\n", amount);
        } else {
            System.out.printf("입금 실패: 보유 현금이 부족합니다. (보유 현금: %,d원)\n", player.getCash());
        }
    }

    public void withdrawFromSavings(long amount) {
        if (account.withdraw(amount)) {
            player.earnCash(amount);
            System.out.printf("예금 계좌에서 %,d원을 출금해 현금으로 보유합니다.\n", amount);
        }
    }

    public void showSavingsAccountInfo() {
        System.out.println(account.toString());
    }

    public void createFund(String fundName, long amount, int riskLevel) {
        if (account.withdraw(amount)) {
            funds.add(new Fund(amount, fundName, riskLevel));
            System.out.printf("펀드 '%s' 생성이 완료되었습니다. (투자금: %,d원)\n", fundName, amount);
        } else {
            System.out.println("펀드 생성 실패: 예금 계좌의 잔액이 부족합니다.");
        }
    }

    public void liquidateFund(int index) {
        if (index >= 0 && index < funds.size()) {
            Fund fund = funds.remove(index);
            final long returnAmount = fund.getBalance();
            account.deposit(returnAmount);
            System.out.printf("펀드 '%s'를 해지하고 예금 계좌로 %,d원이 입금되었습니다.\n", fund.getProductName(), returnAmount);
        } else {
            System.out.println("잘못된 번호입니다.");
        }
    }

    public void showAllFunds() {
        System.out.println("--- 보유 펀드 목록 ---");
        if (funds.isEmpty()) {
            System.out.println("보유 중인 펀드가 없습니다.");
        } else {
            for (int i = 0; i < funds.size(); ++i) {
                Fund fund = funds.get(i);
                System.out.printf("%d. %s (현재가치: %,d원)\n", i + 1, fund.getProductName(), fund.getBalance());
            }
        }
        System.out.println("--------------------");
    }

    public void passOneMonth() {
        System.out.println("================== 한 달이 경과했습니다 ==================");
        account.updateValue();
        for (Fund fund : funds) {
            fund.updateValue();
        }
        System.out.println("==========================================================");
    }


}
