package domain;

public abstract class InvestmentProduct extends FinancialProduct {
    protected String productName;
    protected RiskLevel riskLevel;

    InvestmentProduct(long initialValue, String productName, RiskLevel riskLevel) {
        super(initialValue);
        this.productName = productName;
        this.riskLevel = riskLevel;
    }
    @Override
    public String toString() {
        return super.toString()
                + "[상품명] : " + this.productName + "\n"
                + "[위험등급]: " + this.riskLevel.getLevel() + "\n";
    }
    public String getProductName() {
        return this.productName;
    }
}
