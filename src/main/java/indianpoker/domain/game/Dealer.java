package indianpoker.domain.game;

import indianpoker.domain.game.player.Loser;
import indianpoker.domain.game.player.Player;
import indianpoker.domain.game.player.Winner;
import indianpoker.dto.GameResultDto;
import indianpoker.dto.TurnResultDto;
import indianpoker.exception.BankruptException;
import indianpoker.vo.Card;
import indianpoker.vo.Chips;

import java.util.HashMap;
import java.util.Map;

public class Dealer {
    private static final int COMPARE_STANDARD = 0;
    private Map<Player, Card> playerCards;

    public Card getOtherPlayerCard(Player better) {
        for (Player player : playerCards.keySet()) {
            if (!player.equals(better)) return playerCards.get(player);
        }
        return null;
    }

    public void drawPlayerCards(Player firstPlayer, Player lastPlayer) {
        this.playerCards = receivePlayerCards(firstPlayer, lastPlayer);
    }

    Map<Player, Card> receivePlayerCards(Player firstPlayer, Player lastPlayer) {
        Map<Player, Card> playerCards = new HashMap<>();
        playerCards.put(firstPlayer, firstPlayer.drawACard());
        playerCards.put(lastPlayer, lastPlayer.drawACard());

        return playerCards;
    }

    public TurnResultDto judgeDieCase(Player loser, Player winner, Chips winningChips) {
        return this.winOrLose(winner, loser, winningChips);
    }

    public TurnResultDto judgeCallCase(Player player1, Player player2, Chips winningChips) {
        if (getPlayerCard(player1).compareTo(getPlayerCard(player2)) == COMPARE_STANDARD) {
            return this.draw(player1, player2, winningChips);
        }

        if (getPlayerCard(player1).compareTo(getPlayerCard(player2)) > COMPARE_STANDARD) {
            return this.winOrLose(player1, player2, winningChips);
        }

        return this.winOrLose(player2, player1, winningChips);
    }

    public Card getPlayerCard(Player player) {
        return this.playerCards.get(player);
    }

    TurnResultDto draw(Winner winner1, Winner winner2, Chips winningChips) {
        winner1.gainChips(winningChips.halfChips());
        winner2.gainChips(winningChips.halfChips());

        return TurnResultDto.of()
                .addPlayerCards(getPlayerCards())
                .addWinner(winner1.getPlayerName())
                .addWinner(winner2.getPlayerName())
                .addWinningChips(winningChips.halfChips());
    }

    TurnResultDto winOrLose(Winner winner, Loser loser, Chips winningChips) {
        winner.gainChips(winningChips);
        winner.changeFirstBetter();
        loser.changeLastBetter();
        return TurnResultDto.of()
                .addPlayerCards(getPlayerCards())
                .addWinner(winner.getPlayerName())
                .addWinningChips(winningChips);
    }

    public void checkBankrupt(Player player1, Player player2) {
        if (player1.isGameOver() || player2.isGameOver())
            throw new BankruptException("GAME OVER");
    }

    public GameResultDto judgeGameWinner(Player player1, Player player2) {
        GameResultDto gameResultDto = new GameResultDto();
        if (player1.showChips().compareTo(player2.showChips()) > COMPARE_STANDARD) {
            gameResultDto.addWinnerName(player1.winGame().getPlayerName());
            player2.loseGame();
        }

        if (player1.showChips().compareTo(player2.showChips()) < COMPARE_STANDARD) {
            gameResultDto.addWinnerName(player2.winGame().getPlayerName());
            player1.winGame();
        }

        if (player1.showChips().compareTo(player2.showChips()) == COMPARE_STANDARD) {
            gameResultDto.addWinnerName(player1.getPlayerName());
            gameResultDto.addWinnerName(player2.getPlayerName());
        }
        return gameResultDto;
    }

    public Map<String, Integer> getPlayerCards() {
        Map<String, Integer> cards = new HashMap<>();
        for (Player player : playerCards.keySet()) {
            cards.put(player.getPlayerName(), getPlayerCard(player).getCard());
        }
        return cards;
    }

}

