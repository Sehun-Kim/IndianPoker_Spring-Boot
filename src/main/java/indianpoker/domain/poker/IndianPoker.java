package indianpoker.domain.poker;

import indianpoker.domain.game.Dealer;
import indianpoker.domain.game.Turn;
import indianpoker.domain.humanplayer.HumanPlayer;
import indianpoker.dto.GameResultDto;
import indianpoker.exception.CannotEnterGameException;
import indianpoker.vo.GameStatus;
import indianpoker.vo.Preemptive;
import support.domain.Deck;

import java.util.ArrayList;
import java.util.List;

public class IndianPoker {
    private static int MAX_PERSONNEL_SIZE = 2;

    private long id;
    private String gameName;
    private List<HumanPlayer> players;
    private Dealer dealer;
    private Turn turn;
    private int playerChipsSize;
    private boolean preemptive;
    private GameStatus gameStatus;

    public IndianPoker() {
        this.players = new ArrayList<>();
    }

    public IndianPoker(String gameName, int playerChipsSize, String preemptive) {
        this.gameName = gameName;
        this.playerChipsSize = playerChipsSize;
        this.preemptive = Preemptive.valueOf(preemptive).isPreemptive();
        this.dealer = new Dealer();
        this.players = new ArrayList<>();
        this.gameStatus = GameStatus.WAITS_FOR_PLAYER;
    }

    public IndianPoker readyToPlayer(HumanPlayer player) {
        if (players.size() >= MAX_PERSONNEL_SIZE) throw new CannotEnterGameException("잘못된 게임에 접근하였습니다.");
        if (players.isEmpty()) {
            this.players.add(player.readyToGame(this.playerChipsSize, this.preemptive, Deck.ofGenerateAuto()));
            return this;
        }
        this.players.add(player.readyToGame(this.playerChipsSize, !this.preemptive, Deck.ofGenerateAuto()));
        this.gameStatus = GameStatus.IN_PROGRESS;
        return this;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGameName() {
        return gameName;
    }

    public Turn getTurn() {
        return turn;
    }

    public int getPlayerChipsSize() {
        return playerChipsSize;
    }

    public boolean isPreemptive() {
        return preemptive;
    }

    public GameStatus getGameStatus() {
        return this.gameStatus;
    }

    // domain
    public boolean isGameStatus(GameStatus gameStatus) {
        return this.gameStatus.equals(gameStatus);
    }

    public Turn generateTurn(int turnCount) {
        return this.turn = new Turn(turnCount)
                .addPlayers(this.players)
                .addDealer(this.dealer)
                .init();
    }

    public GameResultDto judgeGameWinner() {
        GameResultDto gameResultDto = this.turn.judgeGameWinner();

        this.gameStatus = changeGameStatus(gameResultDto);
        return gameResultDto;
    }

    private GameStatus changeGameStatus(GameResultDto gameResultDto) {
        if (gameResultDto.isDraw()) {
            return GameStatus.TIE;
        }
        return whoIsWinner(gameResultDto.getWinnerNames());
    }

    private GameStatus whoIsWinner(List<String> winnerNames) {
        if (players.get(0).matchPlayerName(winnerNames.get(0)))
            return GameStatus.FIRST_PLAYER_WON;
        return GameStatus.SECOND_PLAYER_WON;
    }

    public IndianPoker forceQuit() {
        this.gameStatus = GameStatus.ERROR;
        return this;
    }
}
