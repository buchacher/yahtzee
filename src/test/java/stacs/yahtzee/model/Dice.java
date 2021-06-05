package stacs.yahtzee.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * A test class for {@link Die} class test cases.
 */
@DisplayName("Dice Tests")
public class Dice {
    @Test
    void shouldSuggestAvailableCombinationsForDiceToScore() {
        // Arrange
        Player player = new Player(0);
        player.setDice(List.of(new Die(2), new Die(2), new Die(2), new Die(4),
                new Die(1)));

        // Act
        Map<Combination, Long> availableCombinations = player.suggestCombinations();

        // Assert
        Assertions.assertEquals(availableCombinations.get(Combination.ONES), 1);
        Assertions.assertEquals(availableCombinations.get(Combination.TWOS), 6);
        Assertions.assertEquals(availableCombinations.get(Combination.FOURS), 4);
        Assertions.assertEquals(availableCombinations.get(Combination.THREE_OF_A_KIND), 11);
        Assertions.assertEquals(availableCombinations.get(Combination.CHANCE), 11);
        Assertions.assertEquals(availableCombinations.size(), 5);
    }

    @Test
    void shouldNotIncludeCombinationsAlreadyScored() {
        // Arrange
        Player player = new Player(0);
        player.setDice(List.of(new Die(2), new Die(2), new Die(2), new Die(4),
                new Die(1)));
        player.score(Combination.TWOS);

        // Act
        Map<Combination, Long> availableCombinations = player.suggestCombinations();

        // Assert
        Assertions.assertEquals(availableCombinations.size(), 4);
        Assertions.assertFalse(availableCombinations.containsKey(Combination.TWOS));
    }

    @Test
    void playerShouldOnlyRollThreeTimesPerTurn() {
        // Arrange
        Player player = new Player(0);
        player.roll();
        player.roll();
        player.roll();

        List<Die> currentDice = player.getDice();

        // Act
        player.roll();

        // Assert
        Assertions.assertEquals(currentDice, player.getDice());
    }

    @Test
    void shouldResetPlayersRollsOnNewTurn() {
        // Arrange
        Player player = new Player(0);
        player.roll();

        // Act
        player.resetRolls();

        // Assert
        Assertions.assertEquals(player.getRollsLeft(), 3);
    }

    @Test
    void shouldNotRollFrozenDice() {
        // Arrange
        Player player = new Player(0);
        Die dieOne = new Die(7);
        Die dieTwo = new Die(7);
        Die dieThree = new Die(7);
        Die dieFour = new Die(7);
        Die dieFive = new Die(7);
        player.setDice(List.of(dieOne, dieTwo, dieThree, dieFour, dieFive));

        // Act
        dieOne.freeze();
        dieTwo.freeze();
        player.roll();

        // Assert
        Assertions.assertEquals(dieOne.getValue(), 7);
        Assertions.assertEquals(dieTwo.getValue(), 7);
        Assertions.assertNotEquals(dieThree.getValue(), 7);
        Assertions.assertNotEquals(dieFour.getValue(), 7);
        Assertions.assertNotEquals(dieFive.getValue(), 7);
    }

    @Test
    void shouldOnlyRollUnfrozenDice() {
        // Arrange
        Player player = new Player(0);
        Die dieOne = new Die(7);
        Die dieTwo = new Die(7);
        Die dieThree = new Die(7);
        Die dieFour = new Die(7);
        Die dieFive = new Die(7);
        player.setDice(List.of(dieOne, dieTwo, dieThree, dieFour, dieFive));

        // Act
        dieOne.freeze();
        dieTwo.freeze();
        dieThree.freeze();
        dieFour.freeze();
        dieFive.freeze();
        dieOne.unfreeze();
        player.roll();

        // Assert
        Assertions.assertNotEquals(dieOne.getValue(),7);
        Assertions.assertEquals(dieTwo.getValue(), 7);
        Assertions.assertEquals(dieThree.getValue(), 7);
        Assertions.assertEquals(dieFour.getValue(), 7);
        Assertions.assertEquals(dieFive.getValue(), 7);
    }

    @Test
    void onlyFrozenDieShouldBeFrozen() {
        // Arrange
        Die dieOne = new Die(1);
        Die dieTwo = new Die(2);

        // Act
        dieOne.freeze();

        // Assert
        Assertions.assertTrue(dieOne.isFrozen());
        Assertions.assertFalse(dieTwo.isFrozen());
    }
}
