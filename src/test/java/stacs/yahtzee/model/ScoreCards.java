package stacs.yahtzee.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * A test class for score card test cases.
 */
@DisplayName("Score Card Tests")
public class ScoreCards {

    @Test
    void shouldGiveCombinationsPlayerStillNeeds() {
        // Arrange
        Player player = new Player(0);

        // Act
        List<Combination> missingCombinations = player.getMissingCombinations();

        // Assert
        Assertions.assertEquals(missingCombinations.size(), Combination.values().length);
    }

    @Test
    void shouldFilterCombinationsAlreadyScoredFromMissingCombinations() {
        // Arrange
        Player player = new Player(0);

        // Act
        player.score(Combination.TWOS);
        List<Combination> missingCombinations = player.getMissingCombinations();

        // Assert
        Assertions.assertFalse(missingCombinations.contains(Combination.TWOS));
    }

    @Test
    void shouldCheckCorrectScoreIntoScoreCard() {
        // Arrange
        Player player = new Player(0);
        player.setDice(List.of(new Die(1), new Die(1), new Die(1), new Die(2), new Die(4)));

        // Act
        player.score(Combination.ONES);

        // Assert
        Assertions.assertEquals(player.getScoreFor(Combination.ONES), 3);
    }

    @Test
    void playersShouldNotScoreCombinationsMoreThanOnce() {
        // Arrange
        Player player = new Player(0);

        // Act
        player.setDice(List.of(new Die(2), new Die(2), new Die(3), new Die(5),
                new Die(6)));
        player.score(Combination.TWOS);
        player.setDice(List.of(new Die(2), new Die(3), new Die(3), new Die(5),
                new Die(6)));
        player.score(Combination.TWOS);

        // Assert
        Assertions.assertEquals(player.getScoreFor(Combination.TWOS), 4L);
    }

    @Test
    void shouldScoreZeroIfDiceCannotScoreOpenCombination() {
        // Arrange
        Player player = new Player(0);
        player.setDice(List.of(new Die(4), new Die(4), new Die(3), new Die(5),
                new Die(6)));

        // Act
        player.score(Combination.TWOS);

        // Assert
        Assertions.assertEquals(player.getScoreFor(Combination.TWOS), 0);
    }

    @Test
    void shouldSumPointsOfAPlayersScoreCard() {
        // Arrange
        Player player = new Player(0);
        Map<Combination, Long> scoreCard = player.getScoreCard();
        scoreCard.put(Combination.ONES, 2L);
        scoreCard.put(Combination.TWOS, 4L);
        scoreCard.put(Combination.THREES, 6L);
        scoreCard.put(Combination.FOURS, 4L);
        scoreCard.put(Combination.FIVES, 20L);
        scoreCard.put(Combination.SIXES, 18L);
        scoreCard.put(Combination.THREE_OF_A_KIND, 17L);
        scoreCard.put(Combination.FOUR_OF_A_KIND, 13L);
        scoreCard.put(Combination.FULL_HOUSE, 25L);
        scoreCard.put(Combination.SMALL_STRAIGHT, 30L);
        scoreCard.put(Combination.LARGE_STRAIGHT, 40L);
        scoreCard.put(Combination.YAHTZEE, 0L);
        scoreCard.put(Combination.CHANCE, 13L);

        // Act
        long score = player.sumScoreCard();

        // Assert
        Assertions.assertEquals(score, 192L);
    }

    @Test
    void shouldAwardBonusIfPlayerScoredSixtyThreeOrMoreOnUpperSection() {
        // Arrange
        Player player = new Player(0);
        Map<Combination, Long> scoreCard = player.getScoreCard();
        scoreCard.put(Combination.ONES, 4L);
        scoreCard.put(Combination.TWOS, 6L);
        scoreCard.put(Combination.THREES, 9L);
        scoreCard.put(Combination.FOURS, 12L);
        scoreCard.put(Combination.FIVES, 15L);
        scoreCard.put(Combination.SIXES, 18L);
        scoreCard.put(Combination.THREE_OF_A_KIND, 17L);
        scoreCard.put(Combination.FOUR_OF_A_KIND, 13L);
        scoreCard.put(Combination.FULL_HOUSE, 25L);
        scoreCard.put(Combination.SMALL_STRAIGHT, 30L);
        scoreCard.put(Combination.LARGE_STRAIGHT, 40L);
        scoreCard.put(Combination.YAHTZEE, 0L);
        scoreCard.put(Combination.CHANCE, 13L);

        // Act
        long score = player.sumScoreCard();

        // Assert
        Assertions.assertEquals(score, 237L);
    }
}
