package msg;

public enum FundMsg implements Msg {

	MONTH_PASSED_HEADER("================== 한 달이 경과했습니다 ==================\n"),
	MONTH_PASSED_FOOTER("=======================================================\n"),

    NO_CHANGE("[알림] '%s' 펀드의 잔액이 0원이라 변동사항 없습니다.\n"),
    BANKRUPT("[!!!] %s 펀드의 가치가 0원이 되었습니다. (전액 손실)\n"),
    UPDATE("[알림] %s 펀드의 가치가 %,d원 변동했습니다. (수익률: %.2f%%, 최종 가치: %,d원)\n"),


	HAVE_FUND_HEADER("----- 보유 펀드 목록 -----\n"),
	HAVE_FUND_FOOTER("------------------------\n"),
	NOT_HAVE_FUND("보유 중인 펀드가 없습니다.\n"),
	HAVE_FUND_INFO("%d. %s (현재가치: %,d원)\n"),

	LIQUIDATE_PROMPT("해지할 펀드 번호 입력: "),
	LIQUIDATE_SUCCESS("펀드 '%s'를 해지하고 예금 계좌로 %,d원이 입금되었습니다.\n"),

	LIQUIDATE_FAIL_UNKNOWN("알 수 없는 오류로 펀드 해지에 실패했습니다.\n");

    private final String message;

    FundMsg(String message) {
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
