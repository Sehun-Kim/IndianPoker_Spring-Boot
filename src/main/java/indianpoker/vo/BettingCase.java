package indianpoker.vo;

public enum BettingCase {
    CALL_CASE(1), RAISE_CASE(2), DIE_CASE(3);

    private int caseNumber;

    BettingCase(int caseNumber) {
        this.caseNumber = caseNumber;
    }

    public static BettingCase valueOf(int number) {
        for (BettingCase value : values()) {
            if (value.caseNumber == number) {
                return value;
            }
        }
        throw new IllegalArgumentException("유효한 베팅이 아닙니다.");
    }

}
