package stacs.yahtzee.model;

/**
 * An enum to hold all possible combinations on a Player's score card, which are split into an upper section and a
 * lower section
 */
public enum Combination {
    // Upper section:
    ONES, TWOS, THREES, FOURS, FIVES, SIXES,
    // Lower section:
    THREE_OF_A_KIND, FOUR_OF_A_KIND, FULL_HOUSE, SMALL_STRAIGHT, LARGE_STRAIGHT, YAHTZEE, CHANCE
}
