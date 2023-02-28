# Playing abstract strategy games with full information

Due to the natrue of the game, it is impossible to have a well trained engine ahead of time.
Most games played with this engine will be nothing that has ever seen the light of day before.

This ain't no Stockfish, but at least plays chess as good as my hamster.

## Adversarial Search

The strategies applied are based on the chapter on "Adversarial Search" in Stuart Russels and
Peter Norvics book "Artificial intelligence: A Modern Approach" (Pearson, 2016).

Further extensions are done in ways that were utilised by Googles AlphaGo / AlphaZero systems.
(I am not looking at MuZero as I am implementing the whole thing in pure Java.)

### Minimax Algorithm

Looking into the future of the game, we maximise our chance assuming the opponent will minimize
our chance if he can on his turn. We look ahead until we reach a win/lose/draw condition of the
game. We then take that value and track that back to the current move. We select one, that has
the best result.

This will take ages as the game-tree is very big. Alpha-Beta Pruning will eat half of the exponent.
Still too much.

### Be Imperfect use Heuristics

Have some heuristic, that might indicate, how close we are to winning. Calculate for both sides.
Take difference. The heuristic can be quite wonky, but should point into the general direction.
If a position has to be reached, how close are our pieces to that position? If a king is to be
beaten up, how close are we to the king? How many of the opponents pieces can be attacked? How
important are the attacked pieces? How many pieces of what importance are left on our side?

Mix everything into a single number using a weight vector. Evaluate moves, that look good for
us and bad for the opponent first. Analyse moves until the time for thinking is up. Select best
one and go for it.

### Neural Networks

Have a look at the board (convolutional networks), have better heuristics for the moves.

### Iterative Destillation and Amplification

Use shitty network, look ahead several turns. Use slightly better results from the future, seen in
couple of moves later to make the network better. Rinse and repeat.
