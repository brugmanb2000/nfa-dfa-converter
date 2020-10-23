package fa.dfa;

import java.util.HashMap;

import fa.State;

/**
 * Jan 19, 2017
 * Implementation of a DFA state, which
 * mainly contains the information of its
 * neighboring states.
 * @author elenasherman
 *
 */
public class DFAState extends State{
	

	private HashMap<Character,DFAState> delta;//delta
	private boolean isFinal;//remembers its type
	
	/**
	 * Default constructor
	 * @param name the state name
	 */
	public DFAState(String name){
		initDefault(name);
		isFinal = false;
	}
	
	/**
	 * Overlaoded constructor that sets the state type
	 * @param name the state name
	 * @param isFinal the type of state: true - final, false - nonfinal.
	 */
	public DFAState(String name, boolean isFinal){
		initDefault(name);
		this.isFinal = isFinal;
	}
	
	private void initDefault(String name ){
		this.name = name;
		delta = new HashMap<Character, DFAState>();
	}
	
	/**
	 * Accessor for the state type
	 * @return true if final and false otherwise
	 */
	public boolean isFinal(){
		return isFinal;
	}
	

	/**
	 * Add the transition from <code> this </code> object
	 * @param onSymb the alphabet symbol
	 * @param toState to DFA state
	 */
	public void addTransition(char onSymb, DFAState toState){
		delta.put(onSymb, toState);
	}
	
	/**
	 * Retrieves the state that <code>this</code> transitions to
	 * on the given symbol
	 * @param symb - the alphabet symbol
	 * @return the new state 
	 */
	public DFAState getTo(char symb){
		DFAState ret = delta.get(symb);
		if(ret == null){
			 System.err.println("ERROR: DFAState.getTo(char symb) returns null on " + symb + " from " + name);
			 System.exit(2);
			}
		return delta.get(symb);
	}
	
	
}
