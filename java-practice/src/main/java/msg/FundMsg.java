package msg;

public enum FundMsg implements Msg {

	WEEK_PASSED_HEADER("================== 1주가 경과했습니다 ===================\n"),
	WEEK_PASSED_FOOTER("=======================================================\n"),

    NO_CHANGE("[알림] '%s' 펀드의 잔액이 0원이라 변동사항 없습니다.\n"),
    BANKRUPT("[!!!] %s 펀드의 가치가 0원이 되었습니다. (전액 손실)\n"),
    UPDATE("[알림] %s 펀드의 가치가 %,d원 변동했습니다. (수익률: %.2f%%, 최종 가치: %,d원)\n"),


	HAVE_FUND_HEADER("----- 보유 펀드 목록 -----\n"),
	HAVE_FUND_FOOTER("------------------------\n"),
	NOT_HAVE_FUND("보유 중인 펀드가 없습니다.\n"),
	HAVE_FUND_INFO("%d. %s (현재가치: %,d원)\n"),

    CREATE_FUND_NAME_PROMPT("신규 펀드 이름: "),
    CREATE_FUND_AMOUNT_PROMPT("투자할 금액 (예금 계좌에서 출금): "),
    CREATE_FUND_RISK_LEVEL_PROMPT("투자할 펀드 등급\n(1등급: -3.0% ~ +4.0%), (2등급: -6.0% ~ +8.0%), (3등급: -9.0% ~ +12.0%)\n"),

    CREATE_SUCCESS("펀드 '%s' 생성이 완료되었습니다. (투자금: %,d원)\n"),
    CREATE_FAIL_INSUFFICIENT_FUNDS("펀드 생성 실패: 예금 계좌의 잔액이 부족합니다.\n"),
    CREATE_FAIL_INVALID_AMOUNT("펀드 생성 실패: 투자금은 0보다 커야 합니다.\n"),
    CREATE_FAIL_INVALID_RISK_LEVEL("펀드 생성 실패: 1, 2, 3 등급 중에서 선택해주세요.\n"),
    CREATE_FAIL_UNKNOWN("알 수 없는 오류로 펀드 생성에 실패했습니다.\n"),

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
