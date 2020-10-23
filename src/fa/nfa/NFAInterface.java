package fa.nfa;


import java.util.Set;

import fa.FAInterface;
import fa.dfa.DFA;


public interface NFAInterface extends FAInterface {
	
	/**
	 * 
	 * @return equivalent DFA
	 */
	public abstract DFA getDFA(); 
	
	/**
	 * Return delta entries
	 * @param from - the source state
	 * @param onSymb - the label of the transition
	 * @return a set of sink states
	 */
	public Set<NFAState> getToState(NFAState from, char onSymb);
	
	/**
	 * Traverses all epsilon transitions and determine
	 * what states can be reached from s through e
	 * @param s
	 * @return set of states that can be reached from s on epsilon trans.
	 */
	
	public Set<NFAState> eClosure(NFAState s);
}
