package indianpoker.domain.game;

import indianpoker.domain.game.betting.BettingTable;
import indianpoker.domain.game.player.Player;
import indianpoker.domain.humanplayer.HumanPlayer;
import indianpoker.dto.AllBettingInfoDto;
import indianpoker.dto.BettingInfoDto;
import indianpoker.dto.SingleBettingInfoDto;
import indianpoker.dto.ex.BettingChipBoundaryDto;
import indianpoker.dto.ex.TurnResultDto;
import indianpoker.exception.EmptyChipException;
import indianpoker.vo.BettingCase;
import indianpoker.vo.Chips;
import support.util.ChipExtractorUtil;

import java.util.List;

public class Turn {
    public static final int FIRST_TURN_COUNT = 1;
    public static final int LAST_TURN_COUNT = 20;

    private int turnCount = FIRST_TURN_COUNT;

    private Player firstPlayer;
    private Player lastPlayer;
    private Dealer dealer;
    private BettingTable bettingTable;


    public Turn() {
    }

    public Turn addPlayers(List<HumanPlayer> players) {
        this.firstPlayer = players.get(0);
        this.lastPlayer = players.get(1);
        return this;
    }

    public Turn addDealer(Dealer dealer) {
        this.dealer = dealer;
        return this;
    }

    public Turn init() {
        this.dealer.drawPlayerCards(firstPlayer, lastPlayer);
        this.bettingTable = generateTable();
        return this;
    }

    public Turn reverse() {
        Player tempPlayer = this.firstPlayer;
        this.firstPlayer = this.lastPlayer;
        this.lastPlayer = tempPlayer;
        return this;
    }

    private BettingTable generateTable() {
        BettingTable bettingTable = new BettingTable();
        ChipExtractorUtil.addAllBettingChips(firstPlayer.initTurn(), lastPlayer.initTurn(), bettingTable);
        return bettingTable;
    }

    public TurnResultDto judgeCallCase() {
        return this.dealer.judgeCallCase(firstPlayer, lastPlayer, bettingTable.calcWinningChips());
    }

    public void checkGameOver() {
        this.dealer.checkGameOver(firstPlayer, lastPlayer);
    }

    public boolean firstPlayerIsFirst() {
        return this.firstPlayer.isFirst();
    }

    public BettingInfoDto generateBettingInfo() {
        return new BettingInfoDto(this.firstPlayer.toDto().getName(),
                generateAllBettingInfo(),
                generateSingleBettingInfo());
    }

    private AllBettingInfoDto generateAllBettingInfo() {
        return new AllBettingInfoDto(
                this.turnCount,
                this.firstPlayer.toDto(),
                this.lastPlayer.toDto(),
                this.bettingTable.toDto(this.firstPlayer)
        );
    }

    private SingleBettingInfoDto generateSingleBettingInfo() {
        return new SingleBettingInfoDto(this.dealer.getOtherPlayerCard(firstPlayer));
    }

    public void checkEmptyChipException() {
        if (this.firstPlayer.showChips().isEmpty() || this.lastPlayer.showChips().isEmpty()) {
            throw new EmptyChipException("chip is empty"
                    + System.lineSeparator()
                    + "firstPlayer :"
                    + firstPlayer.showChips() + " lastPlayer : " + lastPlayer.showChips());
        }
    }

    public boolean lastPlayerChipIsEmpty() {
        return this.lastPlayer.showChips().isEmpty();
    }

    public BettingChipBoundaryDto generateBettingBoundary() {
        return new BettingChipBoundaryDto(bettingTable.calcDiffChips(), firstPlayer.showChips(), lastPlayer.showChips());
    }

    public void raiseBetting(Chips inputChip) {
        ChipExtractorUtil.addBettingChips(firstPlayer.betting(firstPlayer.payChips(inputChip), BettingCase.RAISE_CASE), bettingTable);
    }

    public void callBetting() {
        Chips chips = bettingTable.calcDiffChips();
        ChipExtractorUtil.addBettingChips(firstPlayer.betting(firstPlayer.payChips(chips), BettingCase.CALL_CASE), bettingTable);
    }

    public void dieBetting() {
        ChipExtractorUtil.addBettingChips(firstPlayer.betting(Chips.ofZero(), BettingCase.DIE_CASE), bettingTable);
    }

    public TurnResultDto judgeDieCase() {
        return dealer.judgeDieCase(firstPlayer, lastPlayer, bettingTable.calcWinningChips());
    }

    @Override
    public String toString() {
        return "Turn{" +
                "turnCount=" + turnCount +
                ", firstPlayer=" + firstPlayer +
                ", lastPlayer=" + lastPlayer +
                ", dealer=" + dealer +
                ", bettingTable=" + bettingTable +
                '}';
    }
}
