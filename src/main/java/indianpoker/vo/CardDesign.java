package indianpoker.vo;

public enum CardDesign {
    ONE("🂡", 1), TWO("🂢", 2), THREE("🂣", 3), FOUR("🂤", 4), FIVE("🂥", 5), SIX("🂦", 6), SEVEN("🂧", 7), EIGHT("🂨", 8), NINE("🂩", 9), TEN("🂪", 10);

    private String cardImage;
    private int cardNum;

    CardDesign(String cardImage, int cardNum) {
        this.cardImage = cardImage;
        this.cardNum = cardNum;
    }

    public static String valueOfCard(int cardNum) {
        for (CardDesign value : values()) {
            if (value.cardNum == cardNum)
                return value.cardImage;
        }
        return null;
    }

}
