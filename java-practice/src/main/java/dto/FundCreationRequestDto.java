package dto;

public class FundCreationRequestDto {
    private final String fundName;
    private final long amount;
    private final int riskLevel;

    public FundCreationRequestDto(String fundName, long amount, int riskLevel) {
        this.fundName = fundName;
        this.amount = amount;
        this.riskLevel = riskLevel;
    }

    public String getFundName() {
        return fundName;
    }

    public long getAmount() {
        return amount;
    }

    public int getRiskLevel() {
        return riskLevel;
    }
}
