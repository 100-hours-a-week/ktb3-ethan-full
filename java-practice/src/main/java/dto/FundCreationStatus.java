package dto;

public enum FundCreationStatus {
    SUCCESS,                // 성공
    INSUFFICIENT_FUNDS,     // 예금 계좌 잔액 부족
    INVALID_AMOUNT          // 투자 금액 오류 (0 또는 음수)
}
