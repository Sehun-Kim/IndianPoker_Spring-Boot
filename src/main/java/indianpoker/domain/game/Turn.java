package indianpoker.domain.game;

import indianpoker.domain.game.betting.BettingTable;
import indianpoker.domain.game.player.Player;
import indianpoker.domain.humanplayer.HumanPlayer;
import indianpoker.dto.TurnInfoDto;
import indianpoker.dto.GameInfoDto;
import indianpoker.dto.BetterInfoDto;
import indianpoker.dto.ex.BettingChipBoundaryDto;
import indianpoker.dto.ex.GameResultDto;
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
    private boolean isFirst;


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
        this.isFirst = true;
        return this;
    }

    public Turn reverse() {
        Player tempPlayer = this.firstPlayer;
        this.firstPlayer = this.lastPlayer;
        this.lastPlayer = tempPlayer;
        return this;
    }

    public Turn checkEmptyChipException() {
        if (this.firstPlayer.showChips().isEmpty() || this.lastPlayer.showChips().isEmpty()) {
            throw new EmptyChipException("chip is empty"
                    + System.lineSeparator()
                    + "firstPlayer :"
                    + firstPlayer.showChips() + " lastPlayer : " + lastPlayer.showChips());
        }
        return this;
    }

    private BettingTable generateTable() {
        BettingTable bettingTable = new BettingTable();
        ChipExtractorUtil.addAllBettingChips(firstPlayer.initTurn(), lastPlayer.initTurn(), bettingTable);
        return bettingTable;
    }

    public boolean firstPlayerIsFirst() {
        return this.firstPlayer.isFirst();
    }

    public GameInfoDto generateGameInfoDto() {
        return new GameInfoDto(this.firstPlayer.toDto().getName(),
                generateTurnInfoDto(),
                generateBetterInfoDto());
    }

    private TurnInfoDto generateTurnInfoDto() {
        return new TurnInfoDto(
                this.turnCount,
                this.firstPlayer.toDto(),
                this.lastPlayer.toDto()
        );
    }

    private BetterInfoDto generateBetterInfoDto() {
        return new BetterInfoDto(
                this.bettingTable.toDto(this.firstPlayer),
                this.dealer.getOtherPlayerCard(firstPlayer),
                generateBettingBoundary(),
                isFirst
        );
    }

    public BettingChipBoundaryDto generateBettingBoundary() {
        return new BettingChipBoundaryDto(bettingTable.calcDiffChips(), firstPlayer.showChips(), lastPlayer.showChips());
    }

    public TurnResultDto callBetting() {
        Chips chips = bettingTable.calcDiffChips();
        ChipExtractorUtil.addBettingChips(firstPlayer.betting(firstPlayer.payChips(chips), BettingCase.CALL_CASE), bettingTable);
        return judgeCallCase();
    }

    public TurnResultDto judgeCallCase() {
        this.turnCount++;
        return this.dealer.judgeCallCase(firstPlayer, lastPlayer, bettingTable.calcWinningChips());
    }

    public void raiseBetting(Chips inputChip) {
        ChipExtractorUtil.addBettingChips(firstPlayer.betting(firstPlayer.payChips(inputChip), BettingCase.RAISE_CASE), bettingTable);
    }

    public TurnResultDto dieBetting() {
        ChipExtractorUtil.addBettingChips(firstPlayer.betting(Chips.ofZero(), BettingCase.DIE_CASE), bettingTable);
        return judgeDieCase();
    }

    public TurnResultDto judgeDieCase() {
        this.turnCount++;
        return dealer.judgeDieCase(firstPlayer, lastPlayer, bettingTable.calcWinningChips());
    }

    public boolean isLastPlayerAllIn() {
        return this.lastPlayer.showChips().isEmpty();
    }

    public void checkBankrupt() {
        this.dealer.checkBankrupt(firstPlayer, lastPlayer);
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

    public boolean makeNotFirst() {
        return this.isFirst = false;
    }

    public GameResultDto judgeGameWinner() {
        return dealer.judgeGameWinner(firstPlayer, lastPlayer);
    }

    public boolean isGameOver() {
        return this.turnCount == LAST_TURN_COUNT;
    }
}
