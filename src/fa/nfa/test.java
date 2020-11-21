package fa.nfa;

import fa.State;
import fa.dfa.DFA;
import fa.dfa.DFAState;

public class test {

	public static void main(String[] args) {
	
		NFA test = new NFA();

		test.addFinalState("f");
		test.addStartState("a");
		test.addState("b");
		test.addState("c");
		test.addState("d");
		test.addState("e");
		test.addState("f");

		test.addTransition("a", '1', "b");
		test.addTransition("a", '3', "e");
		test.addTransition("b", '1', "c");
		test.addTransition("b", '2', "c");
		test.addTransition("b", '3', "e");
		test.addTransition("c", '2', "d");
		test.addTransition("c", '3', "d");
		test.addTransition("d", '4', "e");
		test.addTransition("e", 'e', "a");
		test.addTransition("e", 'e', "f");
	
		DFA test2 = test.getDFA();
		System.out.println(test2.toString());
		
//		System.out.println();
//		System.out.println("------------------------------");
//		System.out.println("Q = " + test2.getStates().toString());
//		System.out.println("Sigma = " + test2.getABC());
//		
//		for (Character x: test2.getABC()) {
//			System.out.print("\t" + x);
//		}
//		System.out.println();
//		for (DFAState x: test2.getStates()) {
//			System.out.print(x);
//			for (Character z: test2.getABC()) {
//				System.out.print("\t" + x.getTo(z));
//			}
//			System.out.println();
//		}
//		System.out.println("[b]       [b]        [empty]");
//		System.out.println("[a]       [empty]    [a]");
//		System.out.println("q0 = " + test2.getStartState());
//		System.out.println("F = " + test2.getFinalStates());
//		System.out.println("------------------------------");
//		System.out.println();		
		
		
}
}
