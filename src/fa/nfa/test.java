package fa.nfa;

import fa.dfa.DFA;

public class test {

	public static void main(String[] args) {
	
		NFA test = new NFA();
		DFA test2;
		test.addFinalState("a");
		test.addStartState("b");
		test.addState("c");
		
	
		test.addTransition("b", '1', "b");
		test.addTransition("b", '1', "a");
		test.addTransition("a", '2', "a");
		test.addTransition("a", 'e', "b");
		test.addTransition("c", 'e', "b");
		test.addTransition("a",	'e', "c");
		System.out.println(test.getStart().getTransitions('1'));
		test2 = test.getDFA();
		
		System.out.println(test2.getStartState());
		
		System.out.println();
		System.out.println("------------------------------");
		System.out.println("Q = " + test2.getStates().toString());
		System.out.println("Sigma = " + test2.getABC());
		System.out.println("Delta: ");
		System.out.println("q0 = " + test2.getStartState());
		System.out.println("F = " + test2.getFinalStates());
		System.out.println("------------------------------");
		System.out.println();		
}
}
