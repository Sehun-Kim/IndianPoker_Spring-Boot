package indianpoker.dto;

import indianpoker.vo.DtoType;

public class BettingInfoDto implements GameMessage {
    private String betterName;
    private AllBettingInfoDto allBettingInfoDto;
    private SingleBettingInfoDto singleBettingInfoDto;
    private DtoType type;

    public BettingInfoDto(String betterName, AllBettingInfoDto allBettingInfoDto, SingleBettingInfoDto singleBettingInfoDto) {
        this.betterName = betterName;
        this.allBettingInfoDto = allBettingInfoDto;
        this.singleBettingInfoDto = singleBettingInfoDto;
        this.type = DtoType.BETTING;
    }

    public BettingInfoDto makeFirstBetting() {
        this.singleBettingInfoDto.setFirstBetting(true);
        return this;
    }

    public String getBetterName() {
        return betterName;
    }

    public AllBettingInfoDto getAllBettingInfoDto() {
        return allBettingInfoDto;
    }

    public SingleBettingInfoDto getSingleBettingInfoDto() {
        return singleBettingInfoDto;
    }

    @Override
    public DtoType getType() {
        return this.type;
    }
}
