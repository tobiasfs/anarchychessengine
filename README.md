# Anarchy Chess Engine
<sub>(This thing that has totally no abbreviation that makes any sense, totally not, I assure you!) </sub>

It plays chess and other games, that count as strategic abstract board games, that have the full information available.

# Motivation

I always see these fine, well thought through ideas to make chess better, that can be found on the [anarchy chess reddit board](https://www.reddit.com/r/AnarchyChess/).
I want to play them !!1!

## Idea

Take a textual description of a game. Extract the details of the game:
* The board
* The pieces
* The setup
* The rules of movement, combat
* The winning condition

For a computer opponent, since the combinations are legion, only algorithms, that need no training or that can train without examples:
* [minimax](https://en.wikipedia.org/wiki/Minimax)
* [alpha-beta pruning](https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning)
* [NN](https://en.wikipedia.org/wiki/Neural_network) with [iterative distillation and amplification](https://arxiv.org/abs/1810.08575)

## Language

Java, because portability > speed
<sub>We are talking about anarchy chess anyway.</sub>

## ECS (Entity Component System)

Since the properties of the pieces are not known up front. They have to be constructed on the fly. Hence [ECS](https://en.wikipedia.org/wiki/Entity_component_system).
