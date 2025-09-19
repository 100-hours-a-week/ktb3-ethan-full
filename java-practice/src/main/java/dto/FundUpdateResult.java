package dto;

public record FundUpdateResult(
    String productName, // 상품명
    long changeAmount,  // 변동 금액
    double rate,        // 수익률
    long finalBalance,  // 최종 잔액
    boolean isBankrupt  // 파산 여부
) implements UpdateResult {
}
