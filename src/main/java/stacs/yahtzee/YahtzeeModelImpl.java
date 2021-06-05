package stacs.yahtzee;

import stacs.yahtzee.model.Player;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The implementation of the {@link YahtzeeModel}.
 */
public class YahtzeeModelImpl implements YahtzeeModel {
    private final List<Player> players;
    private int currentPlayerIdx = 0;

    /**
     * A constructor for the {@link YahtzeeModelImpl} class, which initialises the game's {@link Player}s.
     * @param numberOfPlayers The number of players playing the game.
     */
    public YahtzeeModelImpl(int numberOfPlayers) {
        players = IntStream.range(0, numberOfPlayers)
                .mapToObj(Player::new)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIdx % players.size());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Player> getAllPlayers() {
        return players;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void takeTurn() {
        currentPlayerIdx += 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasEnded() {
        return (currentPlayerIdx / players.size()) >= 13;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Player> getWinner() {
        long maxScore = players.stream()
                .max(Comparator.comparing(Player::sumScoreCard))
                .map(Player::sumScoreCard)
                .orElse(0L);
        return players.stream()
                .filter(p -> p.sumScoreCard() == maxScore)
                .collect(Collectors.toList());
    }
}
