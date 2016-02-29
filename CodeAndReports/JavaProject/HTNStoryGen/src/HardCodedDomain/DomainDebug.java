package HardCodedDomain;

import StoryGenerator.Operator;
import StoryGenerator.PlanningVariable;
import StoryGenerator.Predicate;
import StoryGenerator.State;
import Utilities.ListUtil;

public final class DomainDebug {
	
	public static void PrintDomainFacts(State i){
		System.out.println("True facts in the current state:");
		PrintPredicates(ListUtil.ConvertPredicateList(i.GetFacts()));
	}
	
	public static void PrintPredicates(Predicate[] predicates){
		for(Predicate p : predicates){
			if(p == null){
				System.err.println("Null predicate");
			}
			
			PlanningVariable[] parameter = p.GetParameters();
			switch(p.GetAction()){
				case AT:
					System.out.println(parameter[0].GetName() + " is at " + parameter[1].GetName());
					break;
				case ALIVE:
					if(!p.GetNegated())
						System.out.println(parameter[0].GetName() + " is alive");
					else
						System.out.println(parameter[0].GetName() + " is not alive");
					break;
				case CHARACTER:
					System.out.println(parameter[0].GetName() + " is a character that exists");
					break;
				case LOCATION:
					System.out.println(parameter[0].GetName() + " is a location that exists");
					break;
				case ITEM:
					System.out.println(parameter[0].GetName() + " is an item that exists");
					break;
				case DIFVAR:
					System.out.println(parameter[0].GetName() + " is a different variable from " + parameter[1].GetName());
					break;
				case HOLDS:
					if(!p.GetNegated())
						System.out.println(parameter[0].GetName() + " is holding " + parameter[1].GetName());
					else
						System.out.println(parameter[0].GetName() + " is not holding " + parameter[1].GetName());
					break;
				case SAMEVAR:
					System.out.println(parameter[0].GetName() + " is the same variable as " + parameter[1].GetName());
					break;
				case TRAPPED:
					if(!p.GetNegated())
						System.out.println(parameter[0].GetName() + " is trapped at " + parameter[1].GetName());
					else
						System.out.println(parameter[0].GetName() + " is not trapped at " + parameter[1].GetName());
					break;
				default:
					break;
			}
		}
	}
	
	public static void PrintAvailableOperators(Operator[] aT){
		for(Operator o: aT){			
			PrintOperatorInfo(o);
		}
	}
	
	public static void OperatorPrintSwitch(Operator o)
	{
		switch(o.GetMethodId()){
			case FREE:
				System.out.println("\nFree Operator - Primative");
				break;
			case KILL:
				System.out.println("\nKill Operator - Primative");
				break;
			case MOVE:
				System.out.println("\nMove Operator - Primative");
				break;
			case PICKUP:
				System.out.println("\nPickup Operator - Primative");
				break;
			case PICKUPFROMC:
				System.out.println("\nPickup from Corpse Operator - Primative");
				break;
			case TRAP:
				System.out.println("\nTrap Operator - Primative");
				break;
			case KIDNAP:
				System.out.println("\nKidnap Operator - Compound");
				break;
			case STEAL:
				System.out.println("\nSteal Operator - Compound");
				break;
			case GETITEMFROMLOC:
				System.out.println("\nGetItem from location Operator - Compound");
				break;
			case LOOTANDKILL:
				System.out.println("\nLoot and Kill Operator - Compound");
				break;
			case GETITEMFROMCHAR:
				System.out.println("\nGet Item from Char Operator - Compound");
				break;
			case GETKEY:
				System.out.println("\nGet Key Operator - Compound");
				break;
			case RESCUE:
				System.out.println("\nRescue Operator - Compound");
				break;
			case STORY:
				System.out.println("\nStory Operator - Compound");
				break;
			default:
				break;
		}
	}
	
	public static void PrintOperatorInfo(Operator o){
		OperatorPrintSwitch(o);
		
		System.out.println("\nRequired Variables:");
		if(o.GetParameters() != null){
			for(PlanningVariable p : o.GetParameters()){
				switch(p.GetType()){
				case CHARACTER:
					System.out.println(p.GetName() + " of type character");
					break;
				case ITEM:
					System.out.println(p.GetName() + " of type item.");
					break;
				case LOCATION:
					System.out.println(p.GetName() + " of type location.");
					break;
				default:
					break;
				}
			}
		}
		else
		{
			System.out.println("None");
		}
			
		
		System.out.println("\nThese predicates must be true for this operator to be used:");
		if(o.GetPreconditions() != null){
			PrintPredicates(o.GetPreconditions());
		}
		else
		{
			System.out.println("None");
		}
		
		System.out.println("\nThese predicates will be added to the state:");
		if(o.GetAddList() != null){
			PrintPredicates(o.GetAddList());
		}	
		else
		{
			System.out.println("None");
		}
		
		System.out.println("\nThese predicates will be deleted from the state:");
		if(o.GetDelList() != null){
			PrintPredicates(o.GetDelList());
		}
		else
		{
			System.out.println("None");
		}
		
		System.out.println("\nSubtasks:");
		if(o.GetSubTasks() != null){
			for(Operator sT : o.GetSubTasks()){
				OperatorPrintSwitch(sT);
			}
		}
		else
		{
			System.out.println("None");
		}
	}
}
