package StoryGenerator;

import java.util.ArrayList;
import java.util.Arrays;

public class Prover {
	
	//Need to make sure that the parameters on the preconditions are the same in the initial state
	public static boolean MeetsPrecondition(State s, Predicate[] pre){
		if(pre == null){
			return true;
		}
		
		for(Predicate p: pre){
			if((p.negated && StateContains(p, s) != null) || (!p.negated && StateContains(p, s) == null)){
				return false;
			}
		}
		
		return true;
	}
	
	static Predicate StateContains(Predicate precondition, State s){
		ArrayList<Predicate> statePredicates = s.GetFacts();
		
		for(Predicate pred : statePredicates){
			if(pred.GetAction() == precondition.GetAction()
				&& Arrays.asList(pred.GetParameters()).containsAll(Arrays.asList(precondition.GetParameters())))
			{
				return pred;
			}
		}
		
		return null;
	}
	
}
