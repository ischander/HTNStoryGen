package StoryGenerator;

import HardCodedDomain.DomainDebug;
import HardCodedDomain.HCActions.Condition;

public class Predicate {
	
	Condition actionId;
	boolean negated;
	PlanningVariable[] parameters;
	
	//In predicates with bound variables this will be null
	int[] parameterPositions;
	
	public Predicate(Condition action, PlanningVariable[] para, int[] paraPos, boolean n){
		actionId = action; parameters = para; negated = n; parameterPositions = paraPos;
		
		if(paraPos != null && para.length != paraPos.length){
			System.err.println("The parameter array and parameter position array must be of equal length.");
			DomainDebug.PrintPredicates(new Predicate[]{this});
	        System.exit(1);
		}
	}
	

	public boolean GetValue(){
		//Insert prover code here
		
		return true;
	}
	
	public Condition GetAction(){
		return actionId;
	}
	
	public boolean GetNegated(){
		return negated;
	}
	
	public PlanningVariable[] GetParameters(){
		return parameters;
	}
	
	public Predicate GetBoundPredicate(PlanningVariable[] newPara){
		return new Predicate(actionId, newPara, null, negated);
	}
	
	//Used by temp operator to make bound predicates
	public int[] GetParameterPositions(){
		return parameterPositions;
	}
}
