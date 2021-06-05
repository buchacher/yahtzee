# Yahtzee

A model for the game Yahtzee. The aim was to practice the use of test-driven development as well as good coding practices, refactoring methods and version control.

### Functional requirements implemented

See [requirements.txt](requirements.txt)

- Model any number of players playing the game together
- Model turn taking 
    - After each player took a turn, the next player should be the first again
- Model the determination of the winner
    - If it is a tie game, there are multiple winners
- At any point in the game, the model must be able to answer questions such as what combinations the player taking
their turn still needs
- It must also be able to tell the player what combinations their current dice can make that they still need, i.e.,
what their options for scoring are 
- Score points by rolling five dice to make certain combinations
- The game consists of thirteen rounds
- In each turn, a player first rolls all five dice
- They can roll all or only some dice again up to two more times in an attempt to score a combination
- The combinations are as follows:
    - Upper section:
        - Aces – the sum of dice with the number 1
        - Twos – the sum of dice with the number 2
        - Threes - the sum of dice with the number 3
        - Fours - the sum of dice with the number 4
        - Fives - the sum of dice with the number 5
        - Sixes - the sum of dice with the number 6
    - Lower section:
        - Three of a kind – three dice with the same number; sum of all dice
        - Four of a kind – four dice with the same number; sum of all dice
        - Full house – three of one number and two of another number; 25 points
        - Small straight – four sequential dice: 1-2-3-4, 2-3-4-5 or 3-4-5-6; 30 points
        - Large straight – five sequential dice: 1-2-3-4-5 or 2-3-4-5-6; 40 points
        - Yahtzee – five dice with the same number; 50 points
        - Chance – sum of all dice, regardless of combination
- Each of the 13 combinations can only be scored once
- If a player cannot use the dice they rolled to make any of the combinations they have not scored yet,
they can enter a zero for any of them
- Note that some combination of dice can be used to make up different combinations
- Three fives can be scored as three fives in the upper section or as three of a kind in the lower section
(together with the numbers on the remaining two dice)
- The player can decide which scoring option to use
- The combinations in the upper section are added and if the total is 63 or more, the player gets a bonus of 35
points
- 63 is the number of points resulting from the player getting three of each kind in the upper section
(63=3x1+3x2+3x3+3x4+3x5+3x6)
- If they score fewer than three of a kind in for a number then they need to make up for it by scoring more for other
numbers to make up the difference. For example, if they score only two threes then they can make up for it by
scoring four fives
- The total score is the sum of the totals for the upper and lower sections