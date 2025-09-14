package domain;

public abstract class BankAccount extends FinancialProduct {
    protected String bankName;
    protected String accountNumber;

    BankAccount(long initialBalance, String bankName, String accountNumber) {
        super(initialBalance);
        this.bankName = bankName;
        this.accountNumber = accountNumber;
    }
    @Override
    public String toString() {
        return super.toString()
                + "[은행명] : " + this.bankName + "\n"
                + "[계좌번호]: " + this.accountNumber + "\n";
    }

}
