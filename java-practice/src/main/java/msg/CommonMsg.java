package msg;

public enum CommonMsg implements Msg {
	START_HEADER("미니 금융 시뮬레이터에 오신 것을 환영합니다!\n[메인 메뉴] 1.예금 계좌 | 2.펀드 투자 | 3.한달 지내기 | 4.종료\n"),
	INVALID_NUMBER("잘못된 입력입니다. 메뉴에 있는 숫자를 입력해주세요.\n"),
	INVALID_INPUT("잘못된 입력입니다. 숫자를 입력해주세요.\n"),
	BACK_MENU("이전 메뉴로 돌아갑니다.\n"),
	EXIT_PROGRAM("프로그램을 종료합니다.\n")
	;

	private final String message;

	CommonMsg(String message) {
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
