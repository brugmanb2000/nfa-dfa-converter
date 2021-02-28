package fa.nfa;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import fa.FAInterface;
import fa.State;
import fa.dfa.DFA;


/**
 * This class is used to create and NFA and also to convert the NFA created into a DFA
 * 
 * @author Brandon Brugman
 *
 */



public class NFA implements FAInterface {

	private Set<NFAState> states;
	private NFAState start;
	private Set<Character> alphabet;

	/**
	 * Creates an NFA object
	 */
	public NFA() {
		states = new LinkedHashSet<NFAState>();
		alphabet = new LinkedHashSet<Character>();
	}

	/**
	 * @return NFAState start state
	 */
	public NFAState getStart() {
		return start;
	}
	
	/**
	 * @param String name: Adds a NFA State under this name to the NFA as a start state
	 */
	@Override
	public void addStartState(String name) {
		NFAState startState = checkIfExists(name);
		if (startState == null) {
			NFAState newState = new NFAState(name);
			this.start =  newState;
			states.add(newState);
		} else {
			this.start = startState;
		}
	}
	/**
	 * @param String name: Adds a NFA State under this name to the NFA as a state
	 */
	@Override
	public void addState(String name) {
		NFAState state = checkIfExists(name);
		if (state == null) {
			NFAState newState = new NFAState(name);
			states.add(newState);
		}
	}

	/**
	 * @param String name: Adds a NFA State under this name to the NFA as a final state
	 */
	@Override
	public void addFinalState(String name) {
		NFAState state = checkIfExists(name);
		if (state == null) {
			NFAState finalState = new NFAState(name);
			finalState.setFinalState(true);
			states.add(finalState);
		}
		else { 
			state.isFinalState();
		}
	}

	/**
	 * @param: fromState: adds transition from original state
	 * @param: onSymb: the alphabet symbol to add the transition to
	 * @param: toState: the state the alphabet symbol will transition to
	 */
	@Override
	public void addTransition(String fromState, char onSymb, String toState) {
		NFAState from = checkIfExists(fromState);
		NFAState to = checkIfExists(toState);
		if ((to == null)) {
			System.out.println(toState + "does not exist. Ending program");
			System.exit(1);
		}
		if ((from == null)) {
			System.out.println(fromState + "does not exist. Ending program");
			System.exit(1);
		}

		from.addTransition(onSymb, to);
		if (!alphabet.contains(onSymb)) {
			alphabet.add(onSymb);
		}		
	}

	/**
	 * @param String name: This is the name of the NFA State you are checking if exists
	 * @return: Returns the NFAState if it exists
	 */
	private NFAState checkIfExists(String name){
		NFAState ret = null;
		for(NFAState s : states){
			if(s.getName().equals(name)){
				ret = s;
				break;
			}
		}
		return ret;
	}

	/**
	 * @return: Set of all states in the NFA
	 */
	@Override
	public Set<NFAState> getStates() {
		Set<NFAState> retVal = new LinkedHashSet<NFAState>();
		for (NFAState x : states) {
			retVal.add(x);
		}
		return retVal;
	}

	/**
	 * @return: Returns the final states of your NFA
	 */
	@Override
	public LinkedHashSet<NFAState> getFinalStates() {
		LinkedHashSet<NFAState> retVal = new LinkedHashSet<NFAState>();
		for (NFAState x : states) {
			if (x.isFinalState()) {
				retVal.add(x);
			}
		}
		return retVal;
	}

	
	/**
	 * @return: Returns your start state
	 */
	@Override
	public State getStartState() {
		return start;
	}

	/**
	 * @return: Returns the alphabet characters of our NFA
	 */
	@Override
	public Set<Character> getABC() {
		Set<Character> retVal = new TreeSet<Character>();
		for (Character x : alphabet) {
			retVal.add(x);
		}
		return retVal;
	}

	/**
	 * @return DFA: A DFA equivelent of the current NFA
	 */
	public DFA getDFA() {
		DFA dfa = new DFA();
		Queue <LinkedHashSet<NFAState>> queue = new LinkedList<LinkedHashSet<NFAState>>(); // Used for BFS
		LinkedHashSet<NFAState> startState = new LinkedHashSet<NFAState>(); // Collects start state
		LinkedHashSet<LinkedHashSet<NFAState>> stateSet = new LinkedHashSet<LinkedHashSet<NFAState>>(); // Used for checking all stored states that have been found
		Boolean emptyStateFound = false; // If empty state is found, we know to track for it


		// Get E-Closure for start
		startState = start.getEClosure(startState);
		queue.add(startState);
		stateSet.add(startState);
		// Check if a final states and add if so
		for (NFAState y: getFinalStates()) {
				for (NFAState z: queue.peek())
					if (y.equals(z)) {
						dfa.addFinalState(startState.toString());
						break;
					}
			}

		// Add the start state as a start state
		dfa.addStartState(startState.toString());

		while (!queue.isEmpty()) {
			LinkedHashSet<NFAState> poppedSet = new LinkedHashSet<NFAState>();
			poppedSet = queue.remove();
			// Find all the states with EClosure through the start state's initial transitions
			for (char x: getABC()) {

				// Create blank list and add transitions to it
				LinkedHashSet<NFAState> a = new LinkedHashSet<NFAState>();
				a.addAll(addTransitions(poppedSet, x));
				boolean finalStateFound = false;

				// If the item exists, ignore it
				if ((!checkIfExists(a, stateSet))) {
					
					// if a isn't blank
					if (a.size() != 0) {
						
					// If final state, add as a final state
					for (NFAState y: getFinalStates()) {
						if (a.contains(y)) {
							dfa.addFinalState(a.toString());
							finalStateFound = true;
							queue.add(a);
							stateSet.add(a);
							break;
						}
					}

						// If not a final state, add as a normal state
						if (finalStateFound == false) {
							stateSet.add(a);
							queue.add(a);
							dfa.addState(a.toString());
						}
				}
				}
				
				// DFA doesn't need the empty string, so break if it is found
				if (x == 'e') {
					break;
				}
				
				// Check for empty state
				if (a.size() == 0 && emptyStateFound == false) {
					emptyStateFound = true;
					dfa.addState("[]");
					dfa.addTransition(poppedSet.toString(), x, "[]");
				// if empty state is found, don't add it again
				} else if (a.size() == 0) {
					dfa.addTransition(poppedSet.toString(), x, "[]");
				}
				// else if empty state is not found, add as normal
				else {
					dfa.addTransition(poppedSet.toString(), x, filter(a, stateSet).toString());
				}

			}

		}
		
		// If empty state is found, fill out the rest of the null spaces to avoid error
		if (emptyStateFound == true) {
			for (char x: getABC()) {
				if (x == 'e') {
					break;
				}
				dfa.addTransition("[]", x, "[]");
			}
		}
		return dfa;
	}

	/**
	 * Adds all the transitions/eclosures of transitions and returns the set of transitions.
	 * @param LinkedHashSet initialState: Use current set of states
	 * @param Character x: choose a transition to look at
	 * @return returns the transitions unless looking at the empty string and all the contents are already inside of the initial state
	 */
	private LinkedHashSet<NFAState> addTransitions (LinkedHashSet<NFAState> initialState, char x) {
		LinkedHashSet<NFAState> transitionState = new LinkedHashSet<NFAState>();
		for (NFAState b: initialState) {
			if (!(b.getTransitions(x) == null)) {
				transitionState.addAll(getEClosure(b.getTransitions(x)));
			}

		}
		
		if (x == 'e') {
			if (initialState.containsAll(transitionState)) {
				return initialState;
			}
		}

		return transitionState;	
	}

	/**
	 * Collects the eclosure of a given node
	 * @param: Node you need an eclosure for
	 * @return: list of the eclosures
	 */
	private LinkedHashSet<NFAState> getEClosure (LinkedHashSet<NFAState> states) {
		LinkedHashSet<NFAState> eClosedStates = new LinkedHashSet<NFAState>();
		for (NFAState x: states) {
			eClosedStates.addAll(x.getEClosure(states));
		}
		return eClosedStates;
	}

	/**
	 * This is used to test whether a certain set is already in your current stateSet
	 * @param LinkedHashSet test: The current set you are testing if is already in your stateSet
	 * @param LinkedHashSet stateSet: The current list of sets you have
	 * @return boolean if the test set is already in your stateSet or not
	 */
	private Boolean checkIfExists(LinkedHashSet<NFAState> test, LinkedHashSet<LinkedHashSet<NFAState>> stateSet) {
		Boolean exists = false;
		for (Set<NFAState> x: stateSet) {
			if (x.containsAll(test) && test.containsAll(x)) {
				exists = true;
			}
		}

		if (exists == true) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Used to ensure the same order of the sets are used so duplicate DFA states cannot be added
	 * @param LinkedHashSet test: The current set you are testing if is already in your stateSet
	 * @param LinkedHashSEt stateSet: The current list of sets you have
	 * @return The version of the set that is already in your stateSet
	 */
	private LinkedHashSet<NFAState> filter(LinkedHashSet<NFAState> test, LinkedHashSet<LinkedHashSet<NFAState>> stateSet) {
		for (Set<NFAState> x: stateSet) {
			if (x.containsAll(test) && test.containsAll(x)) {
				return (LinkedHashSet<NFAState>) x;
			}
		}
		return test;
	}
}
