package msg;

public enum AccountMsg implements Msg {
	MONTH_PASSED_HEADER("================== 한 달이 경과했습니다 ==================\n"),
	MONTH_PASSED_FOOTER("=======================================================\n"),

    DEPOSIT_PROMPT("입금할 금액(보유 현금 %,d원): "),
    DEPOSIT_SUCCESS("현금 %,d원을 예금 계좌에 입금했습니다.\n"),
    DEPOSIT_FAIL_INSUFFICIENT_MONEY("입금 실패: 보유 현금이 부족합니다. (보유 현금: %,d원)\n"),
    DEPOSIT_FAIL_NEGATIVE_AMOUNT("입금 실패: 입금액은 0보다 커야 합니다.\n"),
    DEPOSIT_FAIL_UNKNOWN("알 수 없는 오류로 입금에 실패했습니다.\n"),

    WITHDRAW_PROMPT("출금할 금액: "),
    WITHDRAW_SUCCESS("예금 계좌에서 %,d원을 출금해 현금으로 보유합니다.\n"),
    WITHDRAW_FAIL_INSUFFICIENT_BALANCE("출금 실패: 예금 계좌의 잔액이 부족합니다.\n"),
    WITHDRAW_FAIL_NEGATIVE_AMOUNT("출금 실패: 출금액은 0보다 커야 합니다.\n"),
    WITHDRAW_FAIL_UNKNOWN("알 수 없는 오류로 출금에 실패했습니다.\n"),

    UPDATE_INTEREST("[알림] %s 예금 계좌에 월 이자 %,d원이 지급되었습니다. (최종 잔액: %,d원)\n");

    private final String message;

    AccountMsg(String message) {
        this.message = message;
    }

	@Override
    public String get() {
        return message;
    }

	@Override
    public String format(Object... args) {
        return String.format(message, args);
    }
}
