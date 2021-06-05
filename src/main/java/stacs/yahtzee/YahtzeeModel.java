package stacs.yahtzee;

import stacs.yahtzee.model.Player;

import java.util.List;

/**
 * An interface for the Yahtzee model.
 */
public interface YahtzeeModel {

    /**
     * @return The current {@link Player}.
     */
    Player getCurrentPlayer();

    /**
     * @return All {@link Player}s.
     */
    List<Player> getAllPlayers();

    /**
     * Transfers game action to the next {@link Player}.
     */
    void takeTurn();

    /**
     * @return True when the game has ended, i.e. when 13 rounds have been completed.
     */
    boolean hasEnded();

    /**
     * @return The winner or, if it is a tie game, all winners of the game.
     */
    List<Player> getWinner();
  
}
