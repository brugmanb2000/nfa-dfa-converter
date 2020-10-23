package fa;

public abstract class State {
	/**
	 * The state label.
	 * It should be a unique name.
	 * @author elenasherman
	 */
	protected String name;
	
	/**
	 * getter for the string label
	 * @return returns the state label.
	 */
	public String getName(){
		return name;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	
}
