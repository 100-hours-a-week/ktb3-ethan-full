package dto;

public enum TransactionStatus {
    SUCCESS,                // 성공
    INSUFFICIENT_FUNDS,     // 잔액 부족 (출금 시)
    INSUFFICIENT_CASH,      // 현금 부족 (입금 시)
    INVALID_AMOUNT          // 금액 오류 (0 또는 음수)
}
