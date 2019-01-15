package indianpoker.dto;

import indianpoker.dto.ex.BettingTableDto;
import indianpoker.dto.ex.PlayerInfoDto;
import indianpoker.vo.DtoType;

public class AllBettingInfoDto implements GameMessage {
    private int turnCount;
    private PlayerInfoDto ownPlayerInfoDto;
    private PlayerInfoDto otherPlayerInfoDto;
    private BettingTableDto currentTableDto;
    private DtoType type;

    public AllBettingInfoDto(int turnCount, PlayerInfoDto ownPlayerInfoDto, PlayerInfoDto otherPlayerInfoDto, BettingTableDto currentTableDto) {
        this.turnCount = turnCount;
        this.ownPlayerInfoDto = ownPlayerInfoDto;
        this.otherPlayerInfoDto = otherPlayerInfoDto;
        this.currentTableDto = currentTableDto;
        this.type = DtoType.TURN_INFO;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public PlayerInfoDto getOwnPlayerInfoDto() {
        return ownPlayerInfoDto;
    }

    public PlayerInfoDto getOtherPlayerInfoDto() {
        return otherPlayerInfoDto;
    }

    public BettingTableDto getCurrentTableDto() {
        return currentTableDto;
    }

    @Override
    public DtoType getType() {
        return this.type;
    }
}
