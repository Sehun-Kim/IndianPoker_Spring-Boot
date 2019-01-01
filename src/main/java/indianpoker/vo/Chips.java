package indianpoker.vo;

import java.util.Objects;

public class Chips {
    private int numberOfChips;

    public Chips(int numberOfChips) {
        if (numberOfChips < 0)
            throw new IllegalArgumentException("NOT ENOUGH CHIPS");
        this.numberOfChips = numberOfChips;
    }

    public Chips giveChips(int numberOfMinusChips) {
        return new Chips(this.numberOfChips - numberOfMinusChips);
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
