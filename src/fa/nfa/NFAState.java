package fa.nfa;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import fa.State;
import fa.dfa.DFAState;


public class NFAState extends State  {
	
	private Boolean isFinal;
	private HashMap<Character, LinkedHashSet<NFAState>> transitions;
	
	public NFAState(String name) {
		transitions = new HashMap<Character, LinkedHashSet<NFAState>>();
		this.setName(name);
		isFinal = false; 
	}


	
	public void setFinalState(Boolean value) {
		isFinal = value;
	}
	
	public Boolean isFinalState() {
		return isFinal;
	}
	
	public void addTransition(Character c, NFAState nextState) {
		if (transitions.containsKey(c)) {
			transitions.get(c).add(nextState);
		} else {
		LinkedHashSet<NFAState> initialValue = new LinkedHashSet<NFAState>();
		initialValue.add(nextState);
		transitions.put(c, initialValue);
	}
	}
	
	public LinkedHashSet<NFAState> getTransitions(Character onSymb) {
		if (transitions.containsKey(onSymb)) {
			return transitions.get(onSymb);
		}
		else 
		{
			return null;
		}
	}
	
	public void printTransitions() {
		System.out.println(name + " : " + transitions.toString());
	}


	public String getName() {
		return this.name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public int compareTo(NFAState x) {
		if (this.name.length() > x.name.length()) {
			return 1;
		} else if (this.name.length() < x.name.length()) {
			return -1; 
		} else {
			return 0;
		}
	}

	public LinkedHashSet<NFAState> getEClosure(LinkedHashSet<NFAState> startEClose) {
		LinkedHashSet<NFAState> nextTransitions = this.getTransitions('e');
		
		// If there are no free transitions, return own state
		if (nextTransitions == null) {
			return startEClose;
		}
		
		if (startEClose.containsAll(nextTransitions)) {
			return startEClose;
		}
		
		
		
		// If there are free transitions, check those states
		for (NFAState x: nextTransitions) {
			startEClose.add(x);
			startEClose.addAll(x.getEClosure(startEClose));
			
		}
		
		return startEClose;
	}
}
