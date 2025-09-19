package dto;

public record FundLiquidationResult(
    FundLiquidationStatus status,
    String productName, // 성공 시에만 사용
    long returnAmount   // 성공 시에만 사용
) {
}
