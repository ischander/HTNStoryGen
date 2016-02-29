package HardCodedDomain;

import StoryGenerator.PlanningVariable;
import StoryGenerator.PlanningVariable.VariableType;
import StoryGenerator.Predicate;

//Methods here used to describe how to check for preconditions
//Used by prover to validate the domain 
public class HCActions {
	
	public enum Condition { AT, HOLDS, ALIVE, TRAPPED, CHARACTER, LOCATION, ITEM, SAMEVAR, DIFVAR }
	
	public static boolean Character(PlanningVariable c){
		if(c.GetType() == VariableType.CHARACTER)
			return true;
		else
			System.err.println("Character parameter must be a character");
			return false;
	}
	
	public static boolean Location(PlanningVariable l){
		if(l.GetType() == VariableType.LOCATION)
			return true;
		else
			System.err.println("Location parameter must be a location");
			return false;
	}
	
	public static boolean Item(PlanningVariable i){
		if(i.GetType() == VariableType.ITEM)
			return true;
		else
			System.err.println("Item parameter must be an item");
			return false;
	}
	
	public static boolean At(PlanningVariable c, PlanningVariable l){
		if(l.GetType() == VariableType.LOCATION && (c.GetType() == VariableType.CHARACTER || c.GetType() == VariableType.ITEM))
			return true;
		else
			System.err.println("At parameters must be a character and a location");
			return false;
	}
	
	public static boolean Alive(PlanningVariable c){
		if(c.GetType() == VariableType.CHARACTER)
			return true;
		else
			System.err.println("Alive parameter must be a character");
			return false;
	}
	
	public static boolean Holds(PlanningVariable c, PlanningVariable i){
		if(c.GetType() == VariableType.CHARACTER && i.GetType() == VariableType.ITEM)
			return true;
		else
			System.err.println("Holds parameters must be character and then item");
			return false;
	}
	
	public static boolean SameVar(PlanningVariable v1, PlanningVariable v2){
		if(v1 == v2)
			return true;
		else
			System.err.println("Same variables must be the same");
			return false;
	}
	
	public static boolean DifVar(PlanningVariable v1, PlanningVariable v2){
		if(v1 != v2)
			return true;
		else
			System.err.println("Different variables must be different");
			return false;
	}
	
	public static boolean Trapped(PlanningVariable c, PlanningVariable l){
		if(c.GetType() == VariableType.CHARACTER && l.GetType() == VariableType.LOCATION)
			return true;
		else
			System.err.println("Trapped parameters must be a character and a location");
			return false;
	}
	
	public static boolean ValidatePredicates(Predicate[] preds){
		for(Predicate p : preds){
			PlanningVariable[] parameters = p.GetParameters();
			
			switch(p.GetAction()){
			case ALIVE:
				if(!Alive(parameters[0]))
					return false;
				else
					break;
			case AT:
				if(!At(parameters[0], parameters[1]))
					return false;
				else
					break;
			case CHARACTER:
				if(!Character(parameters[0]))
					return false;
				else
					break;
			case DIFVAR:
				if(!DifVar(parameters[0], parameters[1]))
					return false;
				else
					break;
			case HOLDS:
				if(!Holds(parameters[0], parameters[1]))
					return false;
				else
					break;
			case ITEM:
				if(!Item(parameters[0]))
					return false;
				else
					break;
			case LOCATION:
				if(!Location(parameters[0]))
					return false;
				else
					break;
			case SAMEVAR:
				if(!SameVar(parameters[0], parameters[1]))
					return false;
				else
					break;
			case TRAPPED:
				if(!Trapped(parameters[0], parameters[1]))
					return false;
				else
					break;
			default:
				System.err.println("Invalid predicate type.");
		        System.exit(1);
				return false;
			}
		}
		
		return true;
	}
	
}
