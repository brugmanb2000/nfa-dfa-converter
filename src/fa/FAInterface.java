package fa;

import java.util.Set;


public interface FAInterface {

	/**
	 * Adds the initial state to the DFA instance
	 * @param name is the label of the start state
	 */
	public abstract void addStartState(String name);

	/**
	 * Adds a non-final, not initial state to the DFA instance
	 * @param name is the label of the state 
	 */
	public abstract void addState(String name);

	/**
	 * Adds a final state to the DFA
	 * @param name is the label of the state
	 */
	public abstract void addFinalState(String name);


	/**
	 * Adds the transition to the DFA's delta data structure
	 * @param fromState is the label of the state where the transition starts
	 * @param onSymb is the symbol from the DFA's alphabet.
	 * @param toState is the label of the state where the transition ends
	 */
	public abstract void addTransition(String fromState, char onSymb,
			String toState);
	
	/**
	 * Getter for Q
	 * @return a set of states that FA has
	 */
	public abstract Set<? extends State> getStates();
	
	/**
	 * Getter for F
	 * @return a set of final states that FA has
	 */
	public abstract Set<? extends State> getFinalStates();
	
	/**
	 * Getter for q0
	 * @return the start state of FA
	 */
	public abstract State getStartState();
	
	/**
	 * Getter for the alphabet Sigma
	 * @return the alphabet of FA
	 */
	public abstract Set<Character> getABC();

}