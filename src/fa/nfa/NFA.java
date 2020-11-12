package fa.nfa;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import fa.FAInterface;
import fa.State;
import fa.dfa.DFA;
import fa.dfa.DFAState;

public class NFA implements FAInterface {

	public Set<NFAState> states;
	private NFAState start;
	private Set<Character> alphabet;

	public NFA() {
		states = new LinkedHashSet<NFAState>();
		alphabet = new LinkedHashSet<Character>();
	}

	public NFAState getStart() {
		return start;
	}

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

	@Override
	public void addState(String name) {
		NFAState state = checkIfExists(name);
		if (state == null) {
			NFAState newState = new NFAState(name);
			states.add(newState);
		}
	}

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

	@Override
	public Set<NFAState> getStates() {
		Set<NFAState> retVal = new TreeSet<NFAState>();
		for (NFAState x : states) {
			retVal.add(x);
		}
		return retVal;
	}

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

	@Override
	public State getStartState() {
		return start;
	}

	@Override
	public Set<Character> getABC() {
		Set<Character> retVal = new TreeSet<Character>();
		for (Character x : alphabet) {
			retVal.add(x);
		}
		return retVal;
	}

	public DFA getDFA() {
		DFA dfa = new DFA();
		LinkedHashSet<String> usedStates = new LinkedHashSet<String>();
		LinkedHashSet<NFAState> empty = new LinkedHashSet<NFAState>();
		empty.add(new NFAState(""));


		// Loop through all states/transitions
		for (NFAState x: states) {
			for(Character y: alphabet) {
				LinkedHashSet<NFAState> state = new LinkedHashSet<NFAState>();
				state.add(x);				
				LinkedHashSet<NFAState> nextState = x.getTransitions(y);

				if (nextState == null) {
					nextState = empty;
				}

				// Check if current state is in the dfa
				if (!usedStates.contains(state.toString())) {
					usedStates.add(state.toString());

					Boolean isFinalState = false;
					Boolean isStartState = false;
					for (NFAState i: state) {
						if (this.getFinalStates().contains(i)) {
							isFinalState = true;
						}

						if (this.getStart() == x) {
							isStartState = true;
						}
					}
					if (isFinalState == true) {
						dfa.addFinalState(state.toString());
						if (isStartState == true) {
							dfa.addStartState(state.toString());
						}
					} else {

						if (isStartState == true) {
							dfa.addStartState(state.toString());
						}
						else {
							dfa.addState(state.toString());
						}	
					}
				}

				// Check if next state is in the dfa
				if (!usedStates.contains(nextState.toString())) {
					usedStates.add(nextState.toString());

					Boolean isFinalState = false;
					Boolean isStartState = false;
					for (NFAState i: nextState) {
						if (this.getFinalStates().contains(i)) {
							isFinalState = true;
						}
					}
					if (isFinalState == true) {
						dfa.addFinalState(nextState.toString());
					} else {
						dfa.addState(nextState.toString());
					}

				}
				dfa.addTransition(state.toString(), y, nextState.toString());
			}
		}

		// Get ECLosure for Start State
		LinkedHashSet<NFAState> startEClose = new LinkedHashSet<NFAState>();

		empty.add(new NFAState(""));
		startEClose.add(start);
		startEClose = start.getEClosure(startEClose);
		dfa.addStartState(startEClose.toString());




		/*
		// Get States from EClosure
		for (Character b: alphabet) {
			LinkedHashSet<State> validStates = new LinkedHashSet<State>();
			for (NFAState c: startEClose) {
				if (c.getTransitions(b) != null) {
					validStates.add(c);
				}
			}
		}
		 */


		/*	
		// Repeat for all other states
		for (NFAState x: states) {
			if (!x.equals(getStartState())) {
				LinkedHashSet<NFAState> currentStateEClose = new LinkedHashSet<NFAState>();
				currentStateEClose.add(x);
				currentStateEClose = x.getEClosure(currentStateEClose);
				if (!dfa.getStates().equals(currentStateEClose)) {
					if (this.getFinalStates().contains(x)) {
						dfa.addFinalState(currentStateEClose.toString());
					} else {
						dfa.addState(currentStateEClose.toString());
					}
				}
			}
		}
		 */

		dfa.getStates().remove(empty);
		dfa.getABC().remove('e');
		return dfa;
	}

	private LinkedHashSet<NFAState> getNextState(NFAState initialState, Character x) {
		LinkedHashSet<NFAState> nextNode = initialState.getTransitions(x);
		nextNode = initialState.getEClosure(nextNode);
		return nextNode;
	}

}

class OrderComp implements Comparator<NFAState> {

	@Override
	public int compare(NFAState arg0, NFAState arg1) {
		return arg0.compareTo(arg1);
	}

}