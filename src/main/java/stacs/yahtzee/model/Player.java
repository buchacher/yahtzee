package stacs.yahtzee.model;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A class for the Player.
 */
public class Player {

    private final static Set<Combination> UPPER_SECTION = Set.of(Combination.ONES, Combination.TWOS,
            Combination.THREES, Combination.FOURS, Combination.FIVES, Combination.SIXES);
    private final int playerNumber;
    private final Map<Combination, Long> scoreCard;

    private List<Die> dice;
    private int rollsLeft = 3;

    /**
     * A constructor for the Player class.
     */
    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
        this.scoreCard = new HashMap<>();
        this.dice = IntStream.range(1, 6)
                .mapToObj(i -> new Die())
                .collect(Collectors.toList());
    }

    /**
     * @return A Player's player number.
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * @return A Player's {@link Die}.
     */
    public List<Die> getDice() {
        return dice;
    }

    /**
     * @param dice A Player's {@link Die} to be set.
     */
    public void setDice(List<Die> dice) {
        this.dice = dice;
    }

    /**
     * Rolls a Player's unfrozen {@link Die} and decreases their rollsLeft by 1, provided they still have > 0 rolls
     * leff. Will always roll all five {@link Die} when called at the beginning of a Player's turn.
     */
    public void roll() {
        if (rollsLeft > 0) {
            dice.forEach(Die::roll);
            rollsLeft -= 1;
        }
    }

    /**
     * @return How many of their 3 rolls a Player has left in their current turn.
     */
    public int getRollsLeft() {
        return rollsLeft;
    }

    /**
     * Resets a Player's rolls to 3 at the end of their turn.
     */
    public void resetRolls() {
        rollsLeft = 3;
    }

    /**
     * @return A Player's score card.
     */
    public Map<Combination, Long> getScoreCard() {
        return scoreCard;
    }

    /**
     * @return The {@link Combination}s a player still needs to score.
     */
    public List<Combination> getMissingCombinations() {
        return Arrays.stream(Combination.values())
                .filter(combination -> !scoreCard.containsKey(combination))
                .collect(Collectors.toList());
    }

    /**
     * @return A Player's scoring options.
     */
    public Map<Combination, Long> suggestCombinations() {
        return Arrays.stream(Combination.values())
                .filter(combination -> !scoreCard.containsKey(combination))
                .collect(Collectors.toMap(combination -> combination, this::calculateScore))
                .entrySet()
                .stream()
                .filter(es -> es.getValue() != 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * @return The score for a given {@link Combination}.
     */
    private long calculateScore(Combination combination) {
        switch (combination) {
            case ONES:
                return calculateUpperSectionCombinationScore(1);
            case TWOS:
                return calculateUpperSectionCombinationScore(2);
            case THREES:
                return calculateUpperSectionCombinationScore(3);
            case FOURS:
                return calculateUpperSectionCombinationScore(4);
            case FIVES:
                return calculateUpperSectionCombinationScore(5);
            case SIXES:
                return calculateUpperSectionCombinationScore(6);
            case THREE_OF_A_KIND:
                return calculateOfAKindScore(3);
            case FOUR_OF_A_KIND:
                return calculateOfAKindScore(4);
            case FULL_HOUSE:
                return calculateFullHouseScore();
            case SMALL_STRAIGHT:
            case LARGE_STRAIGHT:
                return calculateStraightScore();
            case YAHTZEE:
                if (dice.stream().map(Die::getValue).distinct().count() == 1) {
                    return 50L;
                }
                return 0L;
            case CHANCE:
                return dice.stream().mapToLong(Die::getValue).sum();
        }
        return 0L;
    }

    /**
     * @param i 1 for ONES, 2 for TWOS, ..., 6 for SIXES {@link Combination}.
     * @return The score for a given upper section {@link Combination}.
     */
    private long calculateUpperSectionCombinationScore(int i) {
        return dice.stream().filter(die -> die.getValue() == i).mapToLong(Die::getValue).sum();
    }

    /**
     * @param i 3 for THREE_OF_A_KIND, 4 for FOUR_OF_A_KIND {@link Combination}.
     * @return The score for a THREE_OF_A_KIND or FOUR_OF_A_KIND {@link Combination}.
     */
    private long calculateOfAKindScore(int i) {
        Map<Integer, List<Die>> diceByNumber = dice.stream()
                .collect(Collectors.groupingBy(Die::getValue));
        if (diceByNumber.keySet().stream().anyMatch(number -> diceByNumber.get(number).size() >= i)) {
            return dice.stream().mapToLong(Die::getValue).sum();
        }
        return 0L;
    }

    /**
     * @return The score for a FULL_HOUSE {@link Combination}.
     */
    private long calculateFullHouseScore() {
        Map<Integer, List<Die>> diceByNumber = dice.stream()
                .collect(Collectors.groupingBy(Die::getValue));
        if (diceByNumber.keySet().stream().anyMatch(number -> diceByNumber.get(number).size() == 3)
                && diceByNumber.keySet().stream().anyMatch(number -> diceByNumber.get(number).size() == 2)) {
            return 25L;
        }
        return 0L;
    }

    /**
     * @return The score for a SMALL_STRAIGHT or LARGE_STRAIGHT {@link Combination}.
     */
    private long calculateStraightScore() {
        List<Integer> numbers = dice.stream()
                .map(Die::getValue)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        int straightLength = 0;
        int maxStraightLength = 0;
        int previousElement = 0;
        for (int n : numbers) {
            if (previousElement == 0 || previousElement + 1 == n) {
                straightLength += 1;
            }
            else if (straightLength > maxStraightLength) {
                maxStraightLength = straightLength;
            }
            previousElement = n;
        }
        if (straightLength > maxStraightLength) {
            maxStraightLength = straightLength;
        }
        if (maxStraightLength == 4) {
            return 30L;
        }
        else if (maxStraightLength == 5) {
            return 40L;
        }
        return 0L;
    }

    /**
     * Logs a score for a {@link Combination} on a Player's score card, provided a score for that Combination has not
     * already been logged.
     */
    public void score(Combination combination) {
        scoreCard.putIfAbsent(combination, calculateScore(combination));
    }

    /**
     * @return If previously logged, the score for a given {@link Combination}, otherwise 0.
     */
    public long getScoreFor(Combination combination) {
        return scoreCard.getOrDefault(combination, 0L);
    }

    /**
     * @return The sum of points for all {@link Combination}s, i.e. a Player's total points. If a Player has scored 63
     * or more on the upper section, they are awarded a bonus of additional 35 points.
     */
    public long sumScoreCard() {
        long scoreSum = scoreCard.values().stream().mapToLong(l -> l).sum();

        if (scoreCard.entrySet()
                .stream()
                .filter(es -> UPPER_SECTION.contains(es.getKey()))
                .mapToLong(Map.Entry::getValue)
                .sum() >= 63) {
            scoreSum += 35;
        }

        return scoreSum;
    }
}
