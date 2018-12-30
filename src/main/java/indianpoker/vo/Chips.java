package indianpoker.vo;

import java.util.Objects;

public class Chips {
    private int numberOfChips;

    public Chips(int numberOfChips) {
        this.numberOfChips = numberOfChips;
    }

    public Chips giveChips(int numberOfMinusChips) {
        if (numberOfChips < numberOfMinusChips) throw new IllegalArgumentException("남은 Chips가 부족합니다.");
        this.numberOfChips -= numberOfMinusChips;
        return new Chips(numberOfMinusChips);
    }

    public Chips addChips(Chips otherChips) {
        return new Chips(this.numberOfChips + otherChips.numberOfChips);
    }

    public boolean isEmpty() {
        return this.numberOfChips == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chips chips = (Chips) o;
        return numberOfChips == chips.numberOfChips;
    }

    public Chips subChips(Chips other) {
        return new Chips(Math.abs(this.numberOfChips - other.numberOfChips));
    }

    public Chips halfChips(){
        return new Chips(this.numberOfChips / 2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfChips);
    }

    @Override
    public String toString() {
        return "Chips{" +
                "numberOfChips=" + numberOfChips +
                '}';
    }
}
