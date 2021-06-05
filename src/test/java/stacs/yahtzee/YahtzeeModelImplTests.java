package stacs.yahtzee;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stacs.yahtzee.model.Combination;
import stacs.yahtzee.model.Player;

import java.util.List;
import java.util.Map;

/**
 * A test class for the {@link YahtzeeModelImpl} test cases, predominantly tests turn taking functionality
 */
@DisplayName("YahtzeeModelImpl Tests")
public class YahtzeeModelImplTests {

    @Test
    void playerTurnsShouldFollowInOrder() {
        // Arrange
        YahtzeeModel twoPlayerGame = new YahtzeeModelImpl(2);

        // Act
        Player firstPlayer = twoPlayerGame.getCurrentPlayer();
        twoPlayerGame.takeTurn();
        Player secondPlayer = twoPlayerGame.getCurrentPlayer();

        // Assert
        Assertions.assertEquals(firstPlayer.getPlayerNumber(), 0);
        Assertions.assertEquals(secondPlayer.getPlayerNumber(), 1);
    }

    @Test
    void shouldStartWithFirstPlayerAfterAllTookTurns() {
        // Arrange
        YahtzeeModel twoPlayerGame = new YahtzeeModelImpl(2);

        // Act
        for (int i = 0; i < 2; i++) {
            twoPlayerGame.takeTurn();
        }

        // Assert
        Assertions.assertEquals(twoPlayerGame.getCurrentPlayer().getPlayerNumber(), 0);
    }

    @Test
    void gameShouldConsistOfThirteenRounds() {
        // Arrange
        YahtzeeModel twoPlayerGame = new YahtzeeModelImpl(2);

        // Assume
        Assertions.assertFalse(twoPlayerGame.hasEnded());

        // Act
        for (int i = 0; i < 13; i++) {
            // In a given round, each player takes one turn.
            twoPlayerGame.takeTurn();
            twoPlayerGame.takeTurn();
        }

        // Assert
        Assertions.assertTrue(twoPlayerGame.hasEnded());
    }

    @Test
    void winnerShouldBePlayerWithMostScoredPoints() {
        // Arrange
        YahtzeeModel twoPlayerGame = new YahtzeeModelImpl(2);
        List<Player> players = twoPlayerGame.getAllPlayers();

        Player firstPlayer = players.get(0);
        Map<Combination, Long> scoreCardP1 = firstPlayer.getScoreCard();
        scoreCardP1.put(Combination.ONES, 4L);
        scoreCardP1.put(Combination.TWOS, 6L);
        scoreCardP1.put(Combination.THREES, 9L);
        scoreCardP1.put(Combination.FOURS, 12L);
        scoreCardP1.put(Combination.FIVES, 15L);
        scoreCardP1.put(Combination.SIXES, 18L);
        scoreCardP1.put(Combination.THREE_OF_A_KIND, 17L);
        scoreCardP1.put(Combination.FOUR_OF_A_KIND, 13L);
        scoreCardP1.put(Combination.FULL_HOUSE, 25L);
        scoreCardP1.put(Combination.SMALL_STRAIGHT, 30L);
        scoreCardP1.put(Combination.LARGE_STRAIGHT, 40L);
        scoreCardP1.put(Combination.YAHTZEE, 0L);
        scoreCardP1.put(Combination.CHANCE, 13L);

        Player secondPlayer = players.get(1);
        Map<Combination, Long> scoreCardP2 = secondPlayer.getScoreCard();
        scoreCardP2.put(Combination.ONES, 2L);
        scoreCardP2.put(Combination.TWOS, 4L);
        scoreCardP2.put(Combination.THREES, 6L);
        scoreCardP2.put(Combination.FOURS, 4L);
        scoreCardP2.put(Combination.FIVES, 20L);
        scoreCardP2.put(Combination.SIXES, 18L);
        scoreCardP2.put(Combination.THREE_OF_A_KIND, 17L);
        scoreCardP2.put(Combination.FOUR_OF_A_KIND, 13L);
        scoreCardP2.put(Combination.FULL_HOUSE, 25L);
        scoreCardP2.put(Combination.SMALL_STRAIGHT, 30L);
        scoreCardP2.put(Combination.LARGE_STRAIGHT, 40L);
        scoreCardP2.put(Combination.YAHTZEE, 0L);
        scoreCardP2.put(Combination.CHANCE, 13L);

        // Act
        List<Player> winner = twoPlayerGame.getWinner();

        // Assert
        Assertions.assertTrue(winner.contains(firstPlayer));
        Assertions.assertFalse(winner.contains(secondPlayer));
    }

    @Test
    void shouldDetermineMultipleWinnersIfTieGame() {
        // Arrange
        YahtzeeModel threePlayerGame = new YahtzeeModelImpl(3);
        List<Player> players = threePlayerGame.getAllPlayers();

        Player firstPlayer = players.get(0);
        Map<Combination, Long> scoreCardP1 = firstPlayer.getScoreCard();
        scoreCardP1.put(Combination.ONES, 4L);
        scoreCardP1.put(Combination.TWOS, 6L);
        scoreCardP1.put(Combination.THREES, 9L);
        scoreCardP1.put(Combination.FOURS, 12L);
        scoreCardP1.put(Combination.FIVES, 15L);
        scoreCardP1.put(Combination.SIXES, 18L);
        scoreCardP1.put(Combination.THREE_OF_A_KIND, 17L);
        scoreCardP1.put(Combination.FOUR_OF_A_KIND, 13L);
        scoreCardP1.put(Combination.FULL_HOUSE, 25L);
        scoreCardP1.put(Combination.SMALL_STRAIGHT, 30L);
        scoreCardP1.put(Combination.LARGE_STRAIGHT, 40L);
        scoreCardP1.put(Combination.YAHTZEE, 0L);
        scoreCardP1.put(Combination.CHANCE, 13L);

        Player secondPlayer = players.get(1);
        Map<Combination, Long> scoreCardP2 = secondPlayer.getScoreCard();
        scoreCardP2.put(Combination.ONES, 2L);
        scoreCardP2.put(Combination.TWOS, 4L);
        scoreCardP2.put(Combination.THREES, 6L);
        scoreCardP2.put(Combination.FOURS, 4L);
        scoreCardP2.put(Combination.FIVES, 20L);
        scoreCardP2.put(Combination.SIXES, 18L);
        scoreCardP2.put(Combination.THREE_OF_A_KIND, 17L);
        scoreCardP2.put(Combination.FOUR_OF_A_KIND, 13L);
        scoreCardP2.put(Combination.FULL_HOUSE, 25L);
        scoreCardP2.put(Combination.SMALL_STRAIGHT, 30L);
        scoreCardP2.put(Combination.LARGE_STRAIGHT, 40L);
        scoreCardP2.put(Combination.YAHTZEE, 0L);
        scoreCardP2.put(Combination.CHANCE, 13L);

        Player thirdPlayer = players.get(0);
        Map<Combination, Long> scoreCardP3 = firstPlayer.getScoreCard();
        scoreCardP3.put(Combination.ONES, 4L);
        scoreCardP3.put(Combination.TWOS, 6L);
        scoreCardP3.put(Combination.THREES, 9L);
        scoreCardP3.put(Combination.FOURS, 12L);
        scoreCardP3.put(Combination.FIVES, 15L);
        scoreCardP3.put(Combination.SIXES, 18L);
        scoreCardP3.put(Combination.THREE_OF_A_KIND, 17L);
        scoreCardP3.put(Combination.FOUR_OF_A_KIND, 13L);
        scoreCardP3.put(Combination.FULL_HOUSE, 25L);
        scoreCardP3.put(Combination.SMALL_STRAIGHT, 30L);
        scoreCardP3.put(Combination.LARGE_STRAIGHT, 40L);
        scoreCardP3.put(Combination.YAHTZEE, 0L);
        scoreCardP3.put(Combination.CHANCE, 13L);

        // Act
        List<Player> winner = threePlayerGame.getWinner();

        // Assert
        Assertions.assertTrue(winner.contains(firstPlayer));
        Assertions.assertTrue(winner.contains(thirdPlayer));
        Assertions.assertFalse(winner.contains(secondPlayer));
    }
}
