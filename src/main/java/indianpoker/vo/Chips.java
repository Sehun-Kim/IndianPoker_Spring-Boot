package indianpoker.vo;

import java.util.Objects;

public class Chips implements Comparable<Chips> {
    private static final String A_CHIP = "💵";
    private static final String TEN_CHIP = "💰";

    private int numberOfChips;

    public static Chips ofZero() {
        return new Chips(0);
    }

    public static Chips ofNumberOfChips(int numberOfChips) {
        return new Chips(numberOfChips);
    }

    private Chips(int numberOfChips) {
        if (numberOfChips < 0)
            throw new IllegalArgumentException("NOT ENOUGH CHIPS");
        this.numberOfChips = numberOfChips;
    }

    public Chips subChips(Chips minusChips) {
        return Chips.ofNumberOfChips(this.numberOfChips - minusChips.numberOfChips);
    }

    public Chips absSubChips(Chips other) {
        return Chips.ofNumberOfChips(Math.abs(this.numberOfChips - other.numberOfChips));
    }

    public Chips addChips(Chips otherChips) {
        return Chips.ofNumberOfChips(this.numberOfChips + otherChips.numberOfChips);
    }

    public Chips halfChips() {
        return Chips.ofNumberOfChips(this.numberOfChips / 2);
    }

    public boolean isEmpty() {
        return this.numberOfChips == 0;
    }

    public int getNumberOfChips() {
        return numberOfChips;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chips chips = (Chips) o;
        return numberOfChips == chips.numberOfChips;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfChips);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append("[")
                .append(this.numberOfChips)
                .append("] ");

        int numberOfTenChips = this.numberOfChips / 10;
        int numberOfAChips = this.numberOfChips % 10;

        for (int i = 0; i < numberOfTenChips; i++) {
            sb.append(TEN_CHIP);
        }
        for (int i = 0; i < numberOfAChips; i++) {
            sb.append(A_CHIP);
        }

        return sb.toString();
    }

    @Override
    public int compareTo(Chips other) {
        if (this.numberOfChips > other.numberOfChips) return 1;
        if (this.numberOfChips < other.numberOfChips) return -1;
        return 0;
    }

}
