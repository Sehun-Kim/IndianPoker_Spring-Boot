package indianpoker.dto;

import indianpoker.vo.Chips;

public class PlayerInfoDto {
    private String name;
    private Chips remainChips;

    public PlayerInfoDto(String name, Chips remainChips) {
        this.name = name;
        this.remainChips = remainChips;
    }

    public String getName() {
        return name;
    }

    public Chips getRemainChips() {
        return remainChips;
    }

    @Override
    public String toString() {
        return "Player : " + name + System.lineSeparator() +
                "Chips : " + remainChips;
    }
}
