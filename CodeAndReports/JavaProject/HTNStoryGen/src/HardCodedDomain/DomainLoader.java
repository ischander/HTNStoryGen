package HardCodedDomain;

import java.util.ArrayList;

import HardCodedDomain.HCActions.Condition;
import HardCodedDomain.HCMethods.Method;
import StoryGenerator.Operator;
import StoryGenerator.PlanningVariable;
import StoryGenerator.Predicate;
import StoryGenerator.State;
import StoryGenerator.PlanningVariable.VariableType;
import Utilities.ListUtil;

public final class DomainLoader {
	
	public static State Preset1(){
		ArrayList<Predicate> facts = new ArrayList<Predicate>();
		
		//CHARACTERS
		//Create character
		PlanningVariable[] pV = new PlanningVariable[9];
		pV[0] = new PlanningVariable("Villian", VariableType.CHARACTER);
		//Add fact that they are a character and that they are alive
		facts.add(new Predicate(Condition.CHARACTER, new PlanningVariable[]{pV[0]},null, false));
		facts.add(new Predicate(Condition.ALIVE, new PlanningVariable[]{pV[0]}, null, false));
		
		pV[1] = new PlanningVariable("Hero", VariableType.CHARACTER);
		facts.add(new Predicate(Condition.CHARACTER, new PlanningVariable[]{pV[1]}, null, false));
		facts.add(new Predicate(Condition.ALIVE, new PlanningVariable[]{pV[1]}, null, false));
		
		pV[2] = new PlanningVariable("Princess", VariableType.CHARACTER);
		facts.add(new Predicate(Condition.CHARACTER, new PlanningVariable[]{pV[2]}, null, false));
		facts.add(new Predicate(Condition.ALIVE, new PlanningVariable[]{pV[2]}, null, false));
		
		pV[3] = new PlanningVariable("Dragon", VariableType.CHARACTER);
		facts.add(new Predicate(Condition.CHARACTER, new PlanningVariable[]{pV[3]}, null, false));
		facts.add(new Predicate(Condition.ALIVE, new PlanningVariable[]{pV[3]}, null, false));
		
		//LOCATIONS
		pV[4] = new PlanningVariable("Tower", VariableType.LOCATION);
		facts.add(new Predicate(Condition.LOCATION, new PlanningVariable[]{pV[4]}, null, false));
		
		pV[5] = new PlanningVariable("Castle", VariableType.LOCATION);
		facts.add(new Predicate(Condition.LOCATION, new PlanningVariable[]{pV[5]}, null, false));
		
		pV[6] = new PlanningVariable("Forest", VariableType.LOCATION);
		facts.add(new Predicate(Condition.LOCATION, new PlanningVariable[]{pV[6]}, null, false));
		
		//ITEMS
		pV[7] = new PlanningVariable("magic sword", VariableType.ITEM);
		facts.add(new Predicate(Condition.ITEM, new PlanningVariable[]{pV[7]}, null, false));
		pV[8] = new PlanningVariable("key", VariableType.ITEM);
		facts.add(new Predicate(Condition.ITEM, new PlanningVariable[]{pV[8]}, null, false));
		
		//ADD CHARACTERS STARTING LOCATIONS
		facts.add(new Predicate(Condition.AT, new PlanningVariable[]{pV[1], pV[5]}, null, false));
		facts.add(new Predicate(Condition.AT, new PlanningVariable[]{pV[2], pV[5]}, null, false));
		facts.add(new Predicate(Condition.AT, new PlanningVariable[]{pV[0], pV[6]}, null, false));
		facts.add(new Predicate(Condition.AT, new PlanningVariable[]{pV[3], pV[4]}, null, false));
		
		//ADD ITEM LOCATIONS
		facts.add(new Predicate(Condition.AT, new PlanningVariable[]{pV[7], pV[5]}, null, false));
		
		//GIVE ITEMS
		facts.add(new Predicate(Condition.HOLDS, new PlanningVariable[]{pV[3], pV[8]}, null, false));
		
		return new State(facts, pV);
	}
	
	public static State Preset2(){
		return null;
	}
	
	public static State Preset3(){
		return null;
	}
	
	public static Operator[] LoadOperators(){
		ArrayList<Operator> ops = new ArrayList<Operator>();
		//PRIMATIVE TASKS
		ops.add(KROperatorLoader.MoveOp());
		ops.add(KROperatorLoader.PickUpOps());
		ops.add(KROperatorLoader.KillOps());
		ops.add(KROperatorLoader.PickupFromCorpse());
		ops.add(KROperatorLoader.Trap());
		ops.add(KROperatorLoader.Free());
		
		//COMPOUND TASKS
		Operator kidnap = KROperatorLoader.Kidnap(ops);
		ops.add(kidnap);
		Operator steal = KROperatorLoader.Steal(ops);
		ops.add(steal);
		Operator getItemLoc = KROperatorLoader.GetItemFromLoc(ops);
		ops.add(getItemLoc);
		Operator getItemChar = KROperatorLoader.GetItemFromChar(ops);
		ops.add(getItemChar);
		Operator landk = KROperatorLoader.LootandKill(ops);
		ops.add(landk);
		Operator getKey = KROperatorLoader.GetKey(ops);
		ops.add(getKey);
		Operator rescue = KROperatorLoader.Rescue(ops);
		ops.add(rescue);
		ops.add(Story(ops));
		
		KROperatorLoader.GetItemFromCharSubTasks(getItemChar, ops);
		KROperatorLoader.GetItemFromLocSubTasks(getItemLoc, ops);
		KROperatorLoader.GetKeySubTasks(getKey, ops);
		KROperatorLoader.KidnapSubTasks(kidnap, ops);
		KROperatorLoader.LootandKillSubtasks(landk, ops);
		KROperatorLoader.RescueSubTasks(rescue, ops);
		KROperatorLoader.StealSubTasks(steal, ops);
		
		return (ListUtil.ConvertOperatorList(ops));
	}
	
	static Operator Story(ArrayList<Operator> ops){
		Operator[] subTasks = new Operator[2];
		
		for(Operator o : ops){
			if(o.GetMethodId() == Method.KIDNAP){
				subTasks[0] = o;
				break;
			}
		}
		
		for(Operator o : ops){
			if(o.GetMethodId() == Method.RESCUE){
				subTasks[1] = o;
				break;
			}
		}
		
		return new Operator(Method.STORY, null, 1, null, subTasks, null, null);
	}
}
