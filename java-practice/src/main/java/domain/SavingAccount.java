package domain;

public class SavingAccount extends BankAccount {
    private double intersetRate;

    public SavingAccount(long initialBalance, String bankName, String accountNumber, double intersetRate) {
        super(initialBalance, bankName, accountNumber);
        this.intersetRate = intersetRate;
    }

    @Override
    public void updateValue() {
        long interest = (long) (balance * intersetRate / 12);
        balance += interest;
        System.out.printf("[알림] %s 예금 계좌에 월 이사 %,d원이 지급되었습니다.\n", bankName, interest);
    }

    @Override
    public String toString() {
        return "-----< 일반 예금 >-----\n"
                + super.toString()
                + "[이자율] : " + (intersetRate * 100) + "%\n";
    }
}
