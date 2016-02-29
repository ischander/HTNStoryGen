package StoryGenerator;
import java.util.ArrayList;

public class State {
	PlanningVariable[] variable;
	ArrayList<Predicate> facts;
	
	public State(ArrayList<Predicate> initialState, PlanningVariable[] pV){
		facts = new ArrayList<Predicate>(initialState); variable = pV;
	}
	
	public boolean Add(Predicate[] addList){
		if(addList != null){
			for(Predicate p : addList){
				if(Prover.StateContains(p, this) == null){
					facts.add(p);
				}
			}
			
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean Del(Predicate[] delList){
		if(delList != null){
			for(Predicate p : delList){
				if(Prover.StateContains(p, this) != null){
					facts.remove(Prover.StateContains(p, this));
				}
			}
		
			return true;
		}
		else{
			return false;
		}
	}
	
	public ArrayList<Predicate> GetFacts(){
		return facts;
	}
	
	public PlanningVariable[] GetVariables(){
		return variable;
	}
	
	public void Apply(Operator o){
		Del(o.GetDelList()); Add(o.GetAddList()); 
	}
}
