package HardCodedDomain;

import java.util.ArrayList;

import HardCodedDomain.HCActions.Condition;
import HardCodedDomain.HCMethods.Method;
import StoryGenerator.Operator;
import StoryGenerator.PlanningVariable;
import StoryGenerator.Predicate;
import StoryGenerator.PlanningVariable.VariableType;

public class KROperatorLoader {
	
	//PRIMATIVE TASKS
	static Operator MoveOp(){
		//MOVE OPERATOR
		PlanningVariable[] para = new PlanningVariable[3];
		para[0] = new PlanningVariable("character", VariableType.CHARACTER);
		para[1] = new PlanningVariable("from location", VariableType.LOCATION);
		para[2] = new PlanningVariable("to location", VariableType.LOCATION);
						
		//Preconditions
		Predicate[] preconditions = new Predicate[5];
		preconditions[0] = new Predicate(Condition.CHARACTER, new PlanningVariable[]{para[0]}, new int[]{0}, false);
		preconditions[1] = new Predicate(Condition.LOCATION, new PlanningVariable[]{para[1]}, new int[]{1}, false);
		preconditions[2] = new Predicate(Condition.LOCATION, new PlanningVariable[]{para[2]}, new int[]{2}, false);
				
		//Character must be at the to location
		preconditions[3] = new Predicate(Condition.AT, new PlanningVariable[]{para[0], para[1]}, new int[]{0,1}, false);
		
		//Double check the character is alive
		preconditions[4] = new Predicate(Condition.ALIVE, new PlanningVariable[]{para[0]}, new int[]{0}, false);
		
		//Check the character isn't trapped in current location
		//preconditions[5] = new Predicate(Condition.TRAPPED, new PlanningVariable[]{para[0], new PlanningVariable("any", VariableType.LOCATION)},  , true);
		if(!HCActions.ValidatePredicates(preconditions)){
			System.err.println("Invalid precondition in Move");
			System.exit(1);
		}
			
		//Add list
		Predicate[] addList = new Predicate[1];
		addList[0] = new Predicate(Condition.AT, new PlanningVariable[]{para[0], para[2]}, new int[]{0,2}, false);
		
		if(!HCActions.ValidatePredicates(addList)){
			System.err.println("Invalid add predicate in Move");
			System.exit(1);
		}
				
		//Delete List
		Predicate[] delList = new Predicate[1];
		delList[0] = new Predicate(Condition.AT, new PlanningVariable[]{para[0], para[1]}, new int[]{0,1}, false);
		
		if(!HCActions.ValidatePredicates(delList)){
			System.err.println("Invalid del predicate in Move");
			System.exit(1);
		}
		
		return new Operator(Method.MOVE, para, 1, preconditions, null, addList, delList);
	}
	
	
	static Operator PickUpOps(){
		//PICKUP OPERATOR
		PlanningVariable[] para = new PlanningVariable[3];
		para[0] = new PlanningVariable("character", VariableType.CHARACTER);
		para[1] = new PlanningVariable("item", VariableType.ITEM);
		para[2] = new PlanningVariable("item location", VariableType.LOCATION);
				
		//Preconditions
		Predicate[] preconditions = new Predicate[6];
		preconditions[0] = new Predicate(Condition.CHARACTER, new PlanningVariable[]{para[0]}, new int[]{0} , false);
		preconditions[1] = new Predicate(Condition.ITEM, new PlanningVariable[]{para[1]}, new int[]{1}, false);
		preconditions[2] = new Predicate(Condition.LOCATION, new PlanningVariable[]{para[2]},new int[]{2}, false);
				
		//Is the character and the item in the same location
		preconditions[3] = new Predicate(Condition.AT, new PlanningVariable[]{para[0], para[2]}, new int[]{0, 2}, false);
		preconditions[4] = new Predicate(Condition.AT, new PlanningVariable[]{para[1], para[2]}, new int[]{1, 2}, false);
		
		//Double check the character is alive
		preconditions[5] = new Predicate(Condition.ALIVE, new PlanningVariable[]{para[0]}, new int[]{0}, false);
		
		if(!HCActions.ValidatePredicates(preconditions)){
			System.err.println("Invalid precondition in PickUp");
			System.exit(1);
		}
		
		//Add List
		Predicate[] addList = new Predicate[1];
		addList[0] = new Predicate(Condition.HOLDS, new PlanningVariable[]{para[0], para[1]},new int[]{0,1}, false);
		
		if(!HCActions.ValidatePredicates(addList)){
			System.err.println("Invalid add predicate in PickUp");
			System.exit(1);
		}
		
		//Delete List
		Predicate[] delList = new Predicate[1];
		delList[0] = new Predicate(Condition.AT, new PlanningVariable[]{para[1], para[2]}, new int[]{1,2},  false);
		
		if(!HCActions.ValidatePredicates(delList)){
			System.err.println("Invalid del predicate in PickUp");
			System.exit(1);
		}
		
		return new Operator(Method.PICKUP, para, 1, preconditions, null, addList, delList);
	}
	
	
	static Operator KillOps(){
		//PICKUP OPERATOR
		PlanningVariable[] para = new PlanningVariable[4];
		para[0] = new PlanningVariable("killer", VariableType.CHARACTER);
		para[1] = new PlanningVariable("killee", VariableType.CHARACTER);
		para[2] = new PlanningVariable("location", VariableType.LOCATION);
		para[3] = new PlanningVariable("weapon", VariableType.ITEM);
						
		//Preconditions
		Predicate[] preconditions = new Predicate[8];
		preconditions[0] = new Predicate(Condition.CHARACTER, new PlanningVariable[]{para[0]}, new int[]{0}, false);
		preconditions[1] = new Predicate(Condition.CHARACTER, new PlanningVariable[]{para[1]}, new int[]{1}, false);
		preconditions[2] = new Predicate(Condition.ITEM, new PlanningVariable[]{para[3]}, new int[]{3}, false);
		preconditions[3] = new Predicate(Condition.LOCATION, new PlanningVariable[]{para[2]}, new int[]{2}, false);
						
		//Is the character and the item in the same location
		preconditions[3] = new Predicate(Condition.AT, new PlanningVariable[]{para[0], para[2]}, new int[]{0,2}, false);				
		preconditions[4] = new Predicate(Condition.AT, new PlanningVariable[]{para[1], para[2]}, new int[]{1,2}, false);
		
		//Find better check for this being a weapon
		//preconditions[5] = new Predicate(Condition.SAMEVAR, new PlanningVariable[]{para[3], new PlanningVariable("magic sword", VariableType.ITEM)}, new int[]{3, -1}, false);
		
		preconditions[5] = new Predicate(Condition.HOLDS, new PlanningVariable[]{para[0], para[3]}, new int[]{0,3}, false);
		
		//Double check the character is alive
		preconditions[6] = new Predicate(Condition.ALIVE, new PlanningVariable[]{para[0]}, new int[]{0}, false);
		preconditions[7] = new Predicate(Condition.ALIVE, new PlanningVariable[]{para[1]}, new int[]{1}, false);
		
		if(!HCActions.ValidatePredicates(preconditions)){
			System.err.println("Invalid precondition in Kill");
			System.exit(1);
		}
					
		//Delete List
		Predicate[] delList = new Predicate[1];
		delList[0] = new Predicate(Condition.ALIVE, new PlanningVariable[]{para[1]}, new int[]{1}, false);
		
		if(!HCActions.ValidatePredicates(delList)){
			System.err.println("Invalid del predicate in kill");
			System.exit(1);
		}
		
		return new Operator(Method.KILL, para, 1, preconditions, null, null, delList);
	}
	
	
	static Operator PickupFromCorpse(){
		//PICKUP OPERATOR
		PlanningVariable[] para = new PlanningVariable[4];
		para[0] = new PlanningVariable("character", VariableType.CHARACTER);
		para[1] = new PlanningVariable("item", VariableType.ITEM);
		para[2] = new PlanningVariable("item location", VariableType.LOCATION);
		para[3] = new PlanningVariable("corpse", VariableType.CHARACTER);
						
		//Preconditions
		Predicate[] preconditions = new Predicate[8];
		preconditions[0] = new Predicate(Condition.CHARACTER, new PlanningVariable[]{para[0]}, new int[]{0}, false);
		preconditions[1] = new Predicate(Condition.ITEM, new PlanningVariable[]{para[1]}, new int[]{1}, false);
		preconditions[2] = new Predicate(Condition.LOCATION, new PlanningVariable[]{para[2]}, new int[]{2}, false);
		preconditions[3] = new Predicate(Condition.CHARACTER, new PlanningVariable[]{para[3]}, new int[]{3}, false);
						
		//Is the character and the item in the same location
		preconditions[4] = new Predicate(Condition.AT, new PlanningVariable[]{para[0], para[2]}, new int[]{0,2}, false);
		preconditions[5] = new Predicate(Condition.AT, new PlanningVariable[]{para[3], para[2]}, new int[]{3,2},false);
				
		//Double check the character is alive
		preconditions[6] = new Predicate(Condition.ALIVE, new PlanningVariable[]{para[0]}, new int[]{0}, false);
		
		//Check corpse has item
		preconditions[7] = new Predicate(Condition.HOLDS, new PlanningVariable[]{para[3], para[1]}, new int[]{3,1}, false);
		
		if(!HCActions.ValidatePredicates(preconditions)){
			System.err.println("Invalid precondition in PickUpFromCorpse");
			System.exit(1);
		}
		
		//Add List
		Predicate[] addList = new Predicate[1];
		addList[0] = new Predicate(Condition.HOLDS, new PlanningVariable[]{para[0], para[1]}, new int[]{0,1}, false);
		
		if(!HCActions.ValidatePredicates(addList)){
			System.err.println("Invalid add predicates in PickUpFromCorpse");
			System.exit(1);
		}
		
		//Delete List
		Predicate[] delList = new Predicate[1];
		delList[0] = new Predicate(Condition.HOLDS, new PlanningVariable[]{para[3], para[1]}, new int[]{3,1}, false);
		
		if(!HCActions.ValidatePredicates(delList)){
			System.err.println("Invalid del predicates in PickUpFromCorpse");
			System.exit(1);
		}
		
		return new Operator(Method.PICKUPFROMC, para, 1, preconditions, null, addList, delList);
	}
	
	static Operator Trap(){
		PlanningVariable[] para = new PlanningVariable[2];
		para[0] = new PlanningVariable("character", VariableType.CHARACTER);
		para[1] = new PlanningVariable("location", VariableType.LOCATION);
		
		Predicate[] preconditions = new Predicate[4];
		preconditions[0] = new Predicate(Condition.CHARACTER, new PlanningVariable[]{para[0]}, new int[]{0}, false);
		preconditions[1] = new Predicate(Condition.LOCATION, new PlanningVariable[]{para[1]}, new int[]{1}, false);
		
		//Character is alive
		preconditions[2] = new Predicate(Condition.ALIVE, new PlanningVariable[]{para[0]}, new int[]{0}, false);
		preconditions[3] = new Predicate(Condition.AT, new PlanningVariable[]{para[0], para[1]}, new int[]{0, 1}, false);
		
		if(!HCActions.ValidatePredicates(preconditions)){
			System.err.println("Invalid preconditions in Trap");
			System.exit(1);
		}
		
		//Add List
		Predicate[] addList = new Predicate[1];
		addList[0] = new Predicate(Condition.TRAPPED, new PlanningVariable[]{para[0], para[1]}, new int[]{0, 1}, false);
		
		if(!HCActions.ValidatePredicates(addList)){
			System.err.println("Invalid add predicates in Trap");
			System.exit(1);
		}
		
		return new Operator(Method.TRAP, para, 1, preconditions, null, addList, null);
	}
	
	static Operator Free(){
		PlanningVariable[] para = new PlanningVariable[4];
		para[0] = new PlanningVariable("character", VariableType.CHARACTER);
		para[1] = new PlanningVariable("location", VariableType.LOCATION);
		para[2] = new PlanningVariable("Rescuer", VariableType.CHARACTER);
		para[3] = new PlanningVariable("key", VariableType.ITEM);
		
		Predicate[] preconditions = new Predicate[8];
		preconditions[0] = new Predicate(Condition.CHARACTER, new PlanningVariable[]{para[0]}, new int[]{0}, false);
		preconditions[1] = new Predicate(Condition.LOCATION, new PlanningVariable[]{para[1]}, new int[]{1}, false);
		
		//Character is alive
		preconditions[2] = new Predicate(Condition.ALIVE, new PlanningVariable[]{para[0]}, new int[]{0}, false);
		preconditions[3] = new Predicate(Condition.AT, new PlanningVariable[]{para[0], para[1]}, new int[]{0, 1}, false);
		preconditions[4] = new Predicate(Condition.TRAPPED, new PlanningVariable[]{para[0], para[1]}, new int[]{0, 1}, false);
		preconditions[5] = new Predicate(Condition.ALIVE, new PlanningVariable[]{para[2]}, new int[]{2}, false);
		
		preconditions[6] = new Predicate(Condition.HOLDS, new PlanningVariable[]{para[2], para[3]}, new int[]{2, 3}, false);
		preconditions[7] = new Predicate(Condition.CHARACTER, new PlanningVariable[]{para[2]}, new int[]{2}, false);
		
		if(!HCActions.ValidatePredicates(preconditions)){
			System.err.println("Invalid preconditions in free");
			System.exit(1);
		}
		
		///Del List
		Predicate[] delList = new Predicate[1];
		delList[0] = new Predicate(Condition.TRAPPED, new PlanningVariable[]{para[0], para[1]}, new int[]{0,1}, false);
		
		if(!HCActions.ValidatePredicates(delList)){
			System.err.println("Invalid del predicates in free");
			System.exit(1);
		}
		
		return new Operator(Method.FREE, para, 1, preconditions, null, null, delList);
	}
	
	//COMPOUND TASKS
	static Operator Kidnap(ArrayList<Operator> ops){
		PlanningVariable[] para = new PlanningVariable[3];
		para[0] = new PlanningVariable("Kidnapper", VariableType.CHARACTER);
		para[1] = new PlanningVariable("Victim", VariableType.CHARACTER);
		para[2] = new PlanningVariable("Prison location", VariableType.LOCATION);
		
		Predicate[] preconditions = new Predicate[5];
		preconditions[0] = new Predicate(Condition.CHARACTER, new PlanningVariable[]{para[0]}, new int[]{0}, false);
		preconditions[1] = new Predicate(Condition.CHARACTER, new PlanningVariable[]{para[1]}, new int[]{1}, false);
		preconditions[2] = new Predicate(Condition.LOCATION, new PlanningVariable[]{para[2]}, new int[]{2}, false);
		
		//Characters are alive
		preconditions[3] = new Predicate(Condition.ALIVE, new PlanningVariable[]{para[0]}, new int[]{0}, false);
		preconditions[4] = new Predicate(Condition.ALIVE, new PlanningVariable[]{para[1]}, new int[]{1}, false);
		
		/*//Victim does not have key
		preconditions[5] = new Predicate(Condition.HOLDS, new PlanningVariable[]{para[1], new PlanningVariable("any", VariableType.ITEM)}, true);
		
		//Check the character isn't trapped in current location
		preconditions[6] = new Predicate(Condition.TRAPPED, new PlanningVariable[]{para[0], new PlanningVariable("any", VariableType.LOCATION)}, true);
		preconditions[7] = new Predicate(Condition.TRAPPED, new PlanningVariable[]{para[1], new PlanningVariable("any", VariableType.LOCATION)}, true);*/
		
		if(!HCActions.ValidatePredicates(preconditions)){
			System.err.println("Invalid preconditions in Kidnap");
			System.exit(1);
		}
		
		return new Operator(Method.KIDNAP, para, 1, preconditions, null, null, null);
	}
	
	static void KidnapSubTasks(Operator kidnap, ArrayList<Operator> ops){
		Operator[] subTasks = new Operator[2];
		
		for(Operator o : ops){
			if(o.GetMethodId() == Method.MOVE){
				subTasks[0] = o;
				break;
			}
		}
		
		for(Operator o : ops){
			if(o.GetMethodId() == Method.TRAP){
				subTasks[1] = o;
				break;
			}
		}
		
		kidnap.SetSubTasks(subTasks);
	}
	
	static Operator Steal(ArrayList<Operator> ops){
		PlanningVariable[] para = new PlanningVariable[4];
		para[0] = new PlanningVariable("Thief", VariableType.CHARACTER);
		para[1] = new PlanningVariable("Victim", VariableType.CHARACTER);
		para[2] = new PlanningVariable("location", VariableType.LOCATION);
		para[3] = new PlanningVariable("item", VariableType.ITEM);
		
		Predicate[] preconditions = new Predicate[8];
		preconditions[0] = new Predicate(Condition.CHARACTER, new PlanningVariable[]{para[0]}, new int[]{0}, false);
		preconditions[1] = new Predicate(Condition.CHARACTER, new PlanningVariable[]{para[1]}, new int[]{1}, false);
		preconditions[2] = new Predicate(Condition.LOCATION, new PlanningVariable[]{para[2]}, new int[]{2}, false);
		preconditions[3] = new Predicate(Condition.ITEM, new PlanningVariable[]{para[3]}, new int[]{3}, false);
		
		//Thief character is alive
		preconditions[4] = new Predicate(Condition.ALIVE, new PlanningVariable[]{para[0]}, new int[]{0}, false);
		
		//Check the character isn't trapped in current location
		//preconditions[5] = new Predicate(Condition.TRAPPED, new PlanningVariable[]{para[0], new PlanningVariable("any", VariableType.LOCATION)}, true);
		
		//Check the thief doesn't already have the object
		preconditions[5] = new Predicate(Condition.HOLDS, new PlanningVariable[]{para[0], para[3]}, new int[]{0,3}, true);
		
		preconditions[6] = new Predicate(Condition.AT, new PlanningVariable[]{para[1], para[2]}, new int[]{1,2}, false);
		preconditions[7] = new Predicate(Condition.HOLDS, new PlanningVariable[]{para[1], para[3]}, new int[]{1,3}, false);
		
		if(!HCActions.ValidatePredicates(preconditions)){
			System.err.println("Invalid preconditions in steal");
			System.exit(1);
		}
		
		return new Operator(Method.STEAL, para, 1, preconditions, null, null, null);
	}
	
	static void StealSubTasks(Operator steal, ArrayList<Operator> ops){
		Operator[] subTasks = new Operator[2];
		
		for(Operator o : ops){
			if(o.GetMethodId() == Method.MOVE){
				subTasks[0] = o;
				break;
			}
		}
		
		for(Operator o : ops){
			if(o.GetMethodId() == Method.PICKUPFROMC){
				subTasks[1] = o;
				break;
			}
		}
		
		steal.SetSubTasks(subTasks);
	}
	
	static Operator GetItemFromLoc(ArrayList<Operator> ops){
		PlanningVariable[] para = new PlanningVariable[3];
		para[0] = new PlanningVariable("Thief", VariableType.CHARACTER);
		para[1] = new PlanningVariable("location", VariableType.LOCATION);
		para[2] = new PlanningVariable("item", VariableType.ITEM);
		
		Predicate[] preconditions = new Predicate[6];
		preconditions[0] = new Predicate(Condition.CHARACTER, new PlanningVariable[]{para[0]}, new int[]{0}, false);
		preconditions[1] = new Predicate(Condition.LOCATION, new PlanningVariable[]{para[1]}, new int[]{1}, false);
		preconditions[2] = new Predicate(Condition.ITEM, new PlanningVariable[]{para[2]}, new int[]{2}, false);
		
		//Thief character is alive
		preconditions[3] = new Predicate(Condition.ALIVE, new PlanningVariable[]{para[0]}, new int[]{0}, false);
		
		//Check the character isn't trapped in current location
		//preconditions[4] = new Predicate(Condition.TRAPPED, new PlanningVariable[]{para[0], new PlanningVariable("any", VariableType.LOCATION)}, true);
		
		//Check the thief doesn't already have the object
		preconditions[4] = new Predicate(Condition.HOLDS, new PlanningVariable[]{para[0], para[2]}, new int[]{0,2}, true);
		
		preconditions[5] = new Predicate(Condition.AT, new PlanningVariable[]{para[2], para[1]}, new int[]{2,1}, false);
		
		if(!HCActions.ValidatePredicates(preconditions)){
			System.err.println("Invalid preconditions in GetItemFromLoc");
			System.exit(1);
		}
		
		return new Operator(Method.GETITEMFROMLOC, para, 1, preconditions, null, null, null);
	}
	
	static void GetItemFromLocSubTasks(Operator getItem, ArrayList<Operator> ops){
		Operator[] subTasks = new Operator[2];
		
		for(Operator o : ops){
			if(o.GetMethodId() == Method.MOVE){
				subTasks[0] = o;
				break;
			}
		}
		
		for(Operator o : ops){
			if(o.GetMethodId() == Method.PICKUP){
				subTasks[1] = o;
				break;
			}
		}
		
		getItem.SetSubTasks(subTasks);
	}
	
	static Operator LootandKill(ArrayList<Operator> ops){
		PlanningVariable[] para = new PlanningVariable[7];
		para[0] = new PlanningVariable("Killer", VariableType.CHARACTER);
		para[1] = new PlanningVariable("Victim", VariableType.CHARACTER);
		para[2] = new PlanningVariable("item", VariableType.ITEM);
		para[3] = new PlanningVariable("weapon", VariableType.ITEM);
		para[4] = new PlanningVariable("weapon location", VariableType.LOCATION);
		para[5] = new PlanningVariable("killer location", VariableType.LOCATION);
		para[6] = new PlanningVariable("victim location", VariableType.LOCATION);
		
		Predicate[] preconditions = new Predicate[11];
		preconditions[0] = new Predicate(Condition.CHARACTER, new PlanningVariable[]{para[0]}, new int[]{0}, false);
		preconditions[1] = new Predicate(Condition.CHARACTER, new PlanningVariable[]{para[1]}, new int[]{1}, false);
		preconditions[2] = new Predicate(Condition.ITEM, new PlanningVariable[]{para[2]}, new int[]{2}, false);
		preconditions[3] = new Predicate(Condition.ITEM, new PlanningVariable[]{para[3]}, new int[]{3}, false);
		preconditions[4] = new Predicate(Condition.LOCATION, new PlanningVariable[]{para[4]}, new int[]{4}, false);
		preconditions[5] = new Predicate(Condition.LOCATION, new PlanningVariable[]{para[5]}, new int[]{5}, false);
		preconditions[6] = new Predicate(Condition.LOCATION, new PlanningVariable[]{para[6]}, new int[]{6}, false);

		
		//character is alive
		preconditions[7] = new Predicate(Condition.ALIVE, new PlanningVariable[]{para[0]}, new int[]{0}, false);
		preconditions[8] = new Predicate(Condition.ALIVE, new PlanningVariable[]{para[1]}, new int[]{1}, false);
		
		//Check the character isn't trapped in current location
		preconditions[9] = new Predicate(Condition.TRAPPED, new PlanningVariable[]{para[0], para[5]}, new int[]{0, 5}, true);
		preconditions[10] = new Predicate(Condition.HOLDS, new PlanningVariable[]{para[1], para[2]}, new int[]{1, 2}, false);
		
		if(!HCActions.ValidatePredicates(preconditions)){
			System.err.println("Invalid preconditions in LootandKill");
			System.exit(1);
		}

		return new Operator(Method.LOOTANDKILL, para, 1, preconditions, null, null, null);
	}
	
	static void LootandKillSubtasks(Operator landk, ArrayList<Operator> ops){
		Operator[] subTasks = new Operator[5];
		
		for(Operator o : ops){
			if(o.GetMethodId() == Method.GETITEMFROMLOC){
				subTasks[0] = o;
				break;
			}
		}
		
		for(Operator o : ops){
			if(o.GetMethodId() == Method.GETITEMFROMCHAR){
				subTasks[1] = o;
				break;
			}
		}
		
		for(Operator o : ops){
			if(o.GetMethodId() == Method.MOVE){
				subTasks[2] = o;
				break;
			}
		}
		
		for(Operator o : ops){
			if(o.GetMethodId() == Method.KILL){
				subTasks[3] = o;
				break;
			}
		}
		
		for(Operator o : ops){
			if(o.GetMethodId() == Method.PICKUPFROMC){
				subTasks[4] = o;
				break;
			}
		}
		
		landk.SetSubTasks(subTasks);
	}

	static Operator GetItemFromChar(ArrayList<Operator> ops){
		PlanningVariable[] para = new PlanningVariable[4];
		para[0] = new PlanningVariable("Thief", VariableType.CHARACTER);
		para[1] = new PlanningVariable("Victim", VariableType.CHARACTER);
		para[2] = new PlanningVariable("item", VariableType.ITEM);
		para[3] = new PlanningVariable("victim location", VariableType.LOCATION);
		
		Predicate[] preconditions = new Predicate[6];
		preconditions[0] = new Predicate(Condition.CHARACTER, new PlanningVariable[]{para[0]}, new int[]{0}, false);
		preconditions[1] = new Predicate(Condition.CHARACTER, new PlanningVariable[]{para[1]}, new int[]{1}, false);
		preconditions[2] = new Predicate(Condition.ITEM, new PlanningVariable[]{para[2]}, new int[]{2}, false);
		preconditions[3] = new Predicate(Condition.LOCATION, new PlanningVariable[]{para[3]}, new int[]{3}, false);
		
		//character is alive
		preconditions[4] = new Predicate(Condition.ALIVE, new PlanningVariable[]{para[0]}, new int[]{0}, false);
		
		//Check the character isn't trapped in current location
		//preconditions[5] = new Predicate(Condition.TRAPPED, new PlanningVariable[]{para[0], new PlanningVariable("any", VariableType.LOCATION)}, true);
		preconditions[5] = new Predicate(Condition.HOLDS, new PlanningVariable[]{para[1], para[2]}, new int[]{1,2}, false);
		
		if(!HCActions.ValidatePredicates(preconditions)){
			System.err.println("Invalid preconditions in getitemfromchar");
			System.exit(1);
		}
		return new Operator(Method.GETITEMFROMCHAR, para, 1, preconditions, null, null, null);
	}
	
	static void GetItemFromCharSubTasks(Operator getItem, ArrayList<Operator> ops){
		Operator[] subTasks = new Operator[2];
		
		for(Operator o : ops){
			if(o.GetMethodId() == Method.LOOTANDKILL){
				subTasks[0] = o;
				break;
			}
		}
		
		for(Operator o : ops){
			if(o.GetMethodId() == Method.STEAL){
				subTasks[1] = o;
				break;
			}
		}
		
		getItem.SetSubTasks(subTasks);
	}
	
	
	static Operator GetKey(ArrayList<Operator> ops){
		PlanningVariable[] para = new PlanningVariable[2];
		para[0] = new PlanningVariable("Thief", VariableType.CHARACTER);
		para[1] = new PlanningVariable("item", VariableType.ITEM);
		
		Predicate[] preconditions = new Predicate[3];
		preconditions[0] = new Predicate(Condition.CHARACTER, new PlanningVariable[]{para[0]}, new int[]{0}, false);
		preconditions[1] = new Predicate(Condition.ITEM, new PlanningVariable[]{para[1]}, new int[]{1}, false);
		
		//character is alive
		preconditions[2] = new Predicate(Condition.ALIVE, new PlanningVariable[]{para[0]}, new int[]{0}, false);
		
		//Check the character isn't trapped in current location
		//preconditions[3] = new Predicate(Condition.TRAPPED, new PlanningVariable[]{para[0], new PlanningVariable("any", VariableType.LOCATION)}, true);
		
		if(!HCActions.ValidatePredicates(preconditions)){
			System.err.println("Invalid preconditions in GetKey");
			System.exit(1);
		}
		
		return new Operator(Method.GETKEY, para, 1, preconditions, null, null, null);
	}
	
	static void GetKeySubTasks(Operator getKey, ArrayList<Operator> ops){
		Operator[] subTasks = new Operator[2];
		
		for(Operator o : ops){
			if(o.GetMethodId() == Method.GETITEMFROMCHAR){
				subTasks[0] = o;
				break;
			}
		}
		
		for(Operator o : ops){
			if(o.GetMethodId() == Method.GETITEMFROMLOC){
				subTasks[1] = o;
				break;
			}
		}
		
		getKey.SetSubTasks(subTasks);
	}
	
	static Operator Rescue(ArrayList<Operator> ops){
		PlanningVariable[] para = new PlanningVariable[4];
		para[0] = new PlanningVariable("Hero", VariableType.CHARACTER);
		para[1] = new PlanningVariable("Victim", VariableType.CHARACTER);
		para[2] = new PlanningVariable("location", VariableType.LOCATION);
		para[3] = new PlanningVariable("Key", VariableType.ITEM);
		
		Predicate[] preconditions = new Predicate[7];
		preconditions[0] = new Predicate(Condition.CHARACTER, new PlanningVariable[]{para[0]}, new int[]{0}, false);
		preconditions[1] = new Predicate(Condition.CHARACTER, new PlanningVariable[]{para[1]}, new int[]{1}, false);
		preconditions[2] = new Predicate(Condition.LOCATION, new PlanningVariable[]{para[2]}, new int[]{2}, false);
		preconditions[3] = new Predicate(Condition.ITEM, new PlanningVariable[]{para[3]}, new int[]{3}, false);
		
		//character is alive
		preconditions[4] = new Predicate(Condition.ALIVE, new PlanningVariable[]{para[0]}, new int[]{0}, false);
		preconditions[5] = new Predicate(Condition.ALIVE, new PlanningVariable[]{para[1]}, new int[]{1}, false);
		
		//Check the character isn't trapped in current location
		//preconditions[6] = new Predicate(Condition.TRAPPED, new PlanningVariable[]{para[0], new PlanningVariable("any", VariableType.LOCATION)}, true);
		preconditions[6] = new Predicate(Condition.TRAPPED, new PlanningVariable[]{para[1], para[2]}, new int[]{1, 2}, false);
		
		if(!HCActions.ValidatePredicates(preconditions)){
			System.err.println("Invalid preconditions in Rescue");
			System.exit(1);
		}
		
		return new Operator(Method.RESCUE, para, 1, preconditions, null, null, null);
	}
	
	static void RescueSubTasks(Operator rescue, ArrayList<Operator> ops){
		Operator[] subTasks = new Operator[3];
		
		for(Operator o : ops){
			if(o.GetMethodId() == Method.GETKEY){
				subTasks[0] = o;
				break;
			}
		}
		
		for(Operator o : ops){
			if(o.GetMethodId() == Method.MOVE){
				subTasks[1] = o;
				break;
			}
		}
		
		for(Operator o : ops){
			if(o.GetMethodId() == Method.FREE){
				subTasks[2] = o;
				break;
			}
		}
		
		rescue.SetSubTasks(subTasks);	
	}
	
}
