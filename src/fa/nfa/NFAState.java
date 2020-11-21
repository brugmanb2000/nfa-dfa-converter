package fa.nfa;
import java.util.HashMap;
import java.util.LinkedHashSet;
import fa.State;



/**
 * This class is used for adding NFA States to be used in the NFA class
 * 
 * @author Brandon Brugman
 *
 */

public class NFAState extends State  {
	
	private Boolean isFinal;
	private HashMap<Character, LinkedHashSet<NFAState>> transitions;
	
	/** 
	 * @param name names the NFA State
	 */
	public NFAState(String name) {
		transitions = new HashMap<Character, LinkedHashSet<NFAState>>();
		this.setName(name);
		isFinal = false; 
	}


	
	/**
	 * @param value sets a final state
	 */
	public void setFinalState(Boolean value) {
		isFinal = value;
	}
	
	/**
	 * @return if the state is final or not
	 */
	public Boolean isFinalState() {
		return isFinal;
	}
	
	/**
	 * @param c adds a transition based on the character of c
	 * @param nextState places this state into the set of transition characters
	 */
	public void addTransition(Character c, NFAState nextState) {
		if (transitions.containsKey(c)) {
			transitions.get(c).add(nextState);
		} else {
		LinkedHashSet<NFAState> initialValue = new LinkedHashSet<NFAState>();
		initialValue.add(nextState);
		transitions.put(c, initialValue);
	}
	}
	
	
	/**
	 * @param onSymb Uses the transition character you want to use
	 * @return Returns the transitions contained by this state based on the symbol used
	 */
	public LinkedHashSet<NFAState> getTransitions(Character onSymb) {
		if (transitions.containsKey(onSymb)) {
			return transitions.get(onSymb);
		}
		else 
		{
			return null;
		}
	}
	
	/**
	 *  Prints the transitions of the state
	 */
	public void printTransitions() {
		System.out.println(name + " : " + transitions.toString());
	}


	/**
	 * Gets name of the state
	 */
	public String getName() {
		return this.name;
	}


	/**
	 * @param name Sets the name of the state
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param states Recursively searches all transitions for the states
	 * @return list of all NFA states that can be transitioned to using only eclosure
	 */
	public LinkedHashSet<NFAState> getEClosure(LinkedHashSet<NFAState> states) {
		LinkedHashSet<NFAState> startEClose = new LinkedHashSet<NFAState>();
		startEClose.add(this);
		
		if (this.getTransitions('e') == null) {
			return startEClose;
		}
		
		LinkedHashSet<NFAState> test = this.getTransitions('e');
		
		if (states.containsAll(test)) {
			return startEClose;
		}
		
		for (NFAState x: test) {
			startEClose.addAll(test);
			startEClose.addAll(x.getEClosure(startEClose));
		}
		
		return startEClose;
	}
}
