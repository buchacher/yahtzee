package stacs.yahtzee.model;

import java.util.Random;

/**
 * A class for the Die.
 */
public class Die {

    private static final Random RANDOM = new Random();
    private int value = 1;
    private boolean frozen = false;

    /**
     * A constructor utilised to construct a {@link Player}'s dice.
     */
    public Die() {
    }

    /**
     * A constructor utilised in test classes. While this permits the passing of invalid Die values such as a 7 for
     * testing purposes, it is never called in from within the model.
     * @param value The value the Die should hold.
     */
    public Die(int value) {
        this.value = value;
    }

    /**
     * @return A Die's current value.
     */
    public int getValue() {
        return value;
    }

    /**
     * Rolls a single Die.
     * @return A new value provided the Die is not frozen, otherwise the value the Die currently holds.
     */
    public int roll() {
        if (!frozen) {
            value = RANDOM.nextInt(6) + 1;
        }

        return value;
    }

    /**
     * @return True if the Die is frozen.
     */
    public boolean isFrozen() {
        return frozen;
    }

    /**
     * Freeze a Die, i.e. this Die will not generate a new value when a {@link Player} rolls their dice.
     */
    public void freeze() {
        this.frozen = true;
    }

    /**
     * Unfreeze a Die.
     */
    public void unfreeze() {
        this.frozen = false;
    }
}
