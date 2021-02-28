# Nonderministic finite automaton (NFA) to deterministic finite automaton (DFA) converter
## By Brandon Brugman

### Purpose
This project is a culmination of two projects for my CS 361 Theory of Computation class. In this class, we worked with both deterministic and nondeterministic finite automaton (for short, DFA and NFA). The purpose of this project was twofold:

- Create an NFA object from a formatted text file
- Create a DFA object from the NFA object

From user input, we would be able to create an NFA object similar to what one might see from the program JFLAP. Afterwards, we would then be able to take this NFA object and convert it to a DFA using depth-first search, collection classes, and polymorphism. 

### Testing

If wanting to test this project, the user would create a text file formatted as such below:


b                   <- Final States
a                   <- Start State
c d e               <- Other States

a0a a1b bea         <- Transitions (State->Transition Character->State) \n
0                   < \n
1                   < \ \n
00                  < - - Individual test strings. Program will output TRUE/FALSE if string passes the DFA (e is an empty string) \n
101                 < / \n
e                   < \n
